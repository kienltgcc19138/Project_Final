package vn.kien.event.eventbe.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.kien.event.eventbe.base.BaseService;
import vn.kien.event.eventbe.converter.MajorConverter;
import vn.kien.event.eventbe.entity.Major;
import vn.kien.event.eventbe.entity.Users;
import vn.kien.event.eventbe.exception.ErrorCode;
import vn.kien.event.eventbe.exception.ServiceException;
import vn.kien.event.eventbe.repository.IMajorRepository;
import vn.kien.event.eventbe.repository.IUsersRepository;
import vn.kien.event.eventbe.request.AddUsersMajorRequest;
import vn.kien.event.eventbe.request.CreateMajorRequest;
import vn.kien.event.eventbe.request.SearchMajorRequest;
import vn.kien.event.eventbe.request.UpdateMajorRequest;
import vn.kien.event.eventbe.response.MajorResponse;
import vn.kien.event.eventbe.utils.PageableUtils;
import vn.kien.event.eventbe.utils.StringUtils;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.*;


@Service
@RequiredArgsConstructor
public class MajorService extends BaseService {
    private final IMajorRepository majorRepository;
    private final IUsersRepository usersRepository;

    private final EntityManager entityManager;

    @Transactional(rollbackOn = Exception.class)
    public MajorResponse createMajor(CreateMajorRequest createMajorRequest, String createdBy) {
        Major major = new Major();
        major.setName(createMajorRequest.getName());
        major.setDescription(createMajorRequest.getDescription());
        major.setCreatedAt(new Date());
        major.setCreatedBy(createdBy);
        Major majorCreate = majorRepository.save(major);
        return MajorConverter.convertToMajorResponse(majorCreate);
    }

    public MajorResponse getMajorDetail(Long id) {
        Major major = majorRepository.findByMajorId(id);
        if (major == null) {
            throw new ServiceException(ErrorCode.MAJOR_NOT_FOUND);
        }
        return MajorConverter.convertToMajorResponseWithUsers(major);
    }

    public Page<MajorResponse> searchMajor(SearchMajorRequest request) {
        if (request.getPageSize() == null || request.getPageSize() == 0) {
            request.setPageSize(10);
        }
        Pageable pageable = PageableUtils.convertPageableAndSort(request.getPageNumber(), request.getPageSize(), request.getSort());
        Specification<Major> specification = Specification.where(null);
        if (!StringUtils.isBlankOrNull(request.getKeyword())) {
            specification = specification.or((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + request.getKeyword() + "%"));
        }
        Page<Major> majorPage = majorRepository.findAll(specification, pageable);
        return new PageImpl<>(new ArrayList<>(MajorConverter.convertToMajorResponses(new LinkedHashSet<>(majorPage.getContent()))), pageable, majorPage.getTotalElements());
    }

    @Transactional(rollbackOn = Exception.class)
    public MajorResponse updateMajor(Long id, UpdateMajorRequest request) {
        Major major = majorRepository.findByMajorId(id);
        if (major == null) {
            throw new ServiceException(ErrorCode.MAJOR_NOT_FOUND);
        }
        major.setName(request.getName());
        major.setDescription(request.getDescription());
        Major majorUpdate = majorRepository.save(major);
        return MajorConverter.convertToMajorResponse(majorUpdate);
    }

    @Transactional(rollbackOn = Exception.class)
    public String deleteMajor(Long id) {
        Major major = majorRepository.findByMajorId(id);
        if (major == null) {
            throw new ServiceException(ErrorCode.MAJOR_NOT_FOUND);
        }
        majorRepository.delete(major);
        return "Delete success";
    }

    @Transactional(rollbackOn = Exception.class)
    public MajorResponse addStudentToMajor(Long id, AddUsersMajorRequest requests) {
        List<String> usersIds = requests.getUsersIds();
        Major major = majorRepository.findByMajorId(id);
        if (major == null) {
            throw new ServiceException(ErrorCode.MAJOR_NOT_FOUND);
        }
        EntityGraph<Major> entityGraph = entityManager.createEntityGraph(Major.class);
        entityGraph.addAttributeNodes("users");
        TypedQuery<Users> query = entityManager.createQuery("SELECT u FROM Users u WHERE u.usersId = :usersIds", Users.class);
        List<Users> users = query
                .setHint("javax.persistence.loadgraph", entityGraph)
                .setParameter("usersIds", usersIds)
                .getResultList();
        if (users == null) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }
        for (Users usersUpdate : users) {
            usersUpdate.setMajor(major);
            usersRepository.save(usersUpdate);
        }
        major.setUsers(new LinkedHashSet<>(users));
        return getMajorDetail(id);
    }
}
