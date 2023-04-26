package vn.kien.event.eventbe.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.kien.event.eventbe.base.BaseSort;
import vn.kien.event.eventbe.converter.RecommendConverter;
import vn.kien.event.eventbe.entity.Event;
import vn.kien.event.eventbe.entity.Notification;
import vn.kien.event.eventbe.entity.Recommend;
import vn.kien.event.eventbe.entity.Users;
import vn.kien.event.eventbe.exception.ErrorCode;
import vn.kien.event.eventbe.exception.ServiceException;
import vn.kien.event.eventbe.repository.IRecommendRepository;
import vn.kien.event.eventbe.repository.IUsersRepository;
import vn.kien.event.eventbe.request.CreateRecommendRequest;
import vn.kien.event.eventbe.request.SearchRecommendRequest;
import vn.kien.event.eventbe.request.UpdateRecommendRequest;
import vn.kien.event.eventbe.response.RecommendResponse;
import vn.kien.event.eventbe.utils.PageableUtils;
import vn.kien.event.eventbe.utils.StringUtils;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RecommendService {
    private final EntityManager entityManager;
    private final IUsersRepository usersRepository;

    private final IRecommendRepository recommendRepository;

    @Transactional(rollbackFor = Exception.class)
    public RecommendResponse createRecommend(CreateRecommendRequest request, String usersId) {
        Users users = usersRepository.findById(usersId).orElse(null);
        if (users == null) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }
        Recommend recommend = new Recommend();
        recommend.setName(request.getName());
        recommend.setDescription(request.getDescription());
        recommend.setCreatedBy(usersId);
        recommend.setCreatedAt(new Date());
        recommend.setUsers(users);
        recommendRepository.save(recommend);
        return RecommendConverter.convertToRecommendResponse(recommend);
    }

    @Transactional(rollbackFor = Exception.class)
    public RecommendResponse updateRecommend(Long id, UpdateRecommendRequest request, String usersId) {
        Users users = usersRepository.findById(usersId).orElse(null);
        if (users == null) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }
        Recommend recommend = recommendRepository.findById(id).orElse(null);
        if (recommend == null) {
            throw new ServiceException(ErrorCode.RECOMMEND_NOT_FOUND);
        }
        if (!recommend.getUsers().getUsersId().equals(usersId)) {
            throw new ServiceException(ErrorCode.RECOMMEND_NOT_ALLOW_USER_UPDATE);
        }
        if (!StringUtils.isBlankOrNull(request.getName())) {
            recommend.setName(request.getName());
        }
        if (!StringUtils.isBlankOrNull(request.getDescription())) {
            recommend.setDescription(request.getDescription());
        }
        recommendRepository.save(recommend);
        return RecommendConverter.convertToRecommendResponse(recommend);
    }

    public RecommendResponse getRecommendDetail(Long id) {
        Recommend recommend = recommendRepository.findById(id).orElse(null);
        if (recommend == null) {
            throw new ServiceException(ErrorCode.RECOMMEND_NOT_FOUND);
        }
        return RecommendConverter.convertToRecommendResponse(recommend);
    }

    @Transactional(rollbackFor = Exception.class)
    public String deleteRecommend(Long id) {
        Recommend recommend = recommendRepository.findById(id).orElse(null);
        if (recommend == null) {
            throw new ServiceException(ErrorCode.RECOMMEND_NOT_FOUND);
        }
        recommendRepository.delete(recommend);
        return "Delete success";
    }

    public Page<RecommendResponse> searchRecommend(SearchRecommendRequest request) {
        if (request.getPageSize() == null || request.getPageSize() == 0) {
            request.setPageSize(10);
        }
        Pageable pageable = PageableUtils.convertPageableAndSort(request.getPageNumber(), request.getPageSize(), request.getSort());
        Specification<Event> specification = Specification.where(null);
        if (!StringUtils.isBlankOrNull(request.getKeyword())) {
            specification = specification.or((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + request.getKeyword() + "%"));
            specification = specification.or((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), "%" + request.getKeyword() + "%"));
        }
        if (!StringUtils.isBlankOrNull(request.getCreatedBy())) {
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("createdBy"), request.getCreatedBy()));
        }
        Page<Recommend> recommends = recommendRepository.findAll(specification, pageable);
        List<Recommend> recommendList = recommends.getContent();
        List<Long> recommendIds = recommendList.stream().map(Recommend::getRecommendId).collect(Collectors.toList());

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recommend> criteriaQuery = criteriaBuilder.createQuery(Recommend.class);
        Root<Recommend> root = criteriaQuery.from(Recommend.class);
        criteriaQuery.select(root).where(root.get("recommendId").in(recommendIds));
        if (request.getSort() != null && request.getSort().size() > 0) {
            List<Order> orders = new ArrayList<>();
            for (BaseSort sortRequest : request.getSort()) {
                if (sortRequest.getAsc()) {
                    orders.add(criteriaBuilder.asc(root.get(sortRequest.getKey())));
                } else {
                    orders.add(criteriaBuilder.desc(root.get(sortRequest.getKey())));
                }
            }
            criteriaQuery.orderBy(orders);
        }
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("recommend-users-joined");
        TypedQuery<Recommend> query = entityManager.createQuery(criteriaQuery);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        recommends = new PageImpl<>(query.getResultList(), pageable, recommends.getTotalElements());

        return PageableExecutionUtils.getPage(new ArrayList<>(RecommendConverter.convertToRecommendResponses(new LinkedHashSet<>(recommends.getContent()))), pageable, recommends::getTotalElements);
    }
}
