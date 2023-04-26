package vn.kien.event.eventbe.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.kien.event.eventbe.base.BaseService;
import vn.kien.event.eventbe.base.BaseSort;
import vn.kien.event.eventbe.common.EnumConst;
import vn.kien.event.eventbe.converter.UsersConverter;
import vn.kien.event.eventbe.entity.*;
import vn.kien.event.eventbe.exception.ErrorCode;
import vn.kien.event.eventbe.exception.ServiceException;
import vn.kien.event.eventbe.repository.*;
import vn.kien.event.eventbe.request.*;
import vn.kien.event.eventbe.response.UsersResponse;
import vn.kien.event.eventbe.utils.PageableUtils;
import vn.kien.event.eventbe.utils.StringUtils;

import javax.mail.MessagingException;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UsersService extends BaseService {
    private final IUsersRepository userRepository;
    private final IMajorRepository majorRepository;
    private final ICourseRepository courseRepository;
    private final IUsersEventRepository usersEventRepository;
    private final EntityManager entityManager;
    private final PasswordEncoder encoder;

    private final MailService mailService;

    @Transactional(rollbackFor = Exception.class)
    public UsersResponse createUser(CreateUserRequest createUserRequest, String createdBy, boolean isAdmin) {
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new ServiceException(ErrorCode.ACCOUNT_EXISTED);
        }
        // validate major
        Major major = null;
        if (createUserRequest.getMajorId() != null) {
            major = majorRepository.findByMajorId(createUserRequest.getMajorId());
            if (major == null) {
                throw new ServiceException(ErrorCode.MAJOR_NOT_FOUND);
            }
        }
        Course course = null;
        if (createUserRequest.getCourseId() != null) {
            course = courseRepository.findByCourseId(createUserRequest.getCourseId());
            if (course == null) {
                throw new ServiceException(ErrorCode.COURSE_NOT_FOUND);
            }
        }
        Users user = new Users();
        user.setUsersId("GCC19" + sequenceValueItemService.getSequence(Users.class));
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(encoder.encode(createUserRequest.getPassword()));
        user.setFullName(createUserRequest.getFullName());
        user.setPhone(createUserRequest.getPhone());
        user.setRoles(isAdmin ? EnumConst.UserRolesEnum.ROLE_ADMIN.toString() : EnumConst.UserRolesEnum.ROLE_USER.toString());
        user.setBirthday(createUserRequest.getBirthday());
        user.setGender(createUserRequest.getGender());

        user.setMajor(major);
        user.setCreatedAt(new Date());
        if (course != null) {
            user.setCourse(course);
        }

        user.setCreatedBy(createdBy);
        Users users = userRepository.save(user);
        try {
            mailService.sendEmailCreateUser(user.getEmail(), createUserRequest.getPassword());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return UsersConverter.convertToUsersResponse(users);
    }

    @Transactional(rollbackFor = Exception.class)
    public UsersResponse updateUser(UpdateUserRequest updateUserRequest) {
        if (StringUtils.isBlankOrNull(updateUserRequest.getId())) {
            throw new ServiceException(ErrorCode.ID_IS_NOT_EMPTY);
        }
        Users users = userRepository.findById(updateUserRequest.getId()).orElse(null);
        if (users == null) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }
        if (!StringUtils.isBlankOrNull(updateUserRequest.getEmail()) && !users.getEmail().equals(updateUserRequest.getEmail())) {
            if (userRepository.existsByEmail(updateUserRequest.getEmail())) {
                throw new ServiceException(ErrorCode.ACCOUNT_EXISTED);
            }
            users.setEmail(updateUserRequest.getEmail());
        }
        if (!StringUtils.isBlankOrNull(updateUserRequest.getFullName())) {
            users.setFullName(updateUserRequest.getFullName());
        }
        if (!StringUtils.isBlankOrNull(updateUserRequest.getPhone())) {
            users.setPhone(updateUserRequest.getPhone());
        }
        if (!StringUtils.isBlankOrNull(updateUserRequest.getGender())) {
            users.setGender(updateUserRequest.getGender());
        }
        if (updateUserRequest.getBirthday() != null) {
            users.setBirthday(updateUserRequest.getBirthday());
        }
        if (updateUserRequest.getMajorId() != null) {
            Major major = majorRepository.findByMajorId(updateUserRequest.getMajorId());
            if (major == null) {
                throw new ServiceException(ErrorCode.MAJOR_NOT_FOUND);
            }
            users.setMajor(major);
        }
        if (updateUserRequest.getCourseId() != null) {
            Course course = courseRepository.findById(updateUserRequest.getCourseId()).orElse(null);
            if (course == null) {
                throw new ServiceException(ErrorCode.COURSE_NOT_FOUND);
            }
            users.setCourse(course);
        }
        userRepository.save(users);
        return UsersConverter.convertToUsersResponse(users);
    }


    public Page<UsersResponse> getAllUsers(SearchUserRequest request) {
        if (request.getPageSize() == null || request.getPageSize() == 0) {
            request.setPageSize(10);
        }
        Pageable pageable = PageableUtils.convertPageableAndSort(request.getPageNumber(), request.getPageSize(), request.getSort());
        Specification<Users> specification = Specification.where(null);
        if (!StringUtils.isBlankOrNull(request.getKeyword())) {
            specification = specification.or((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("fullName"), "%" + request.getKeyword() + "%"));
            specification = specification.or((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), "%" + request.getKeyword() + "%"));
            specification = specification.or((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("phone"), "%" + request.getKeyword() + "%"));
            specification = specification.or((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("usersId"), "%" + request.getKeyword() + "%"));
        }

        if (!StringUtils.isBlankOrNull(request.getRole())) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("roles"), request.getRole()));
        }
        Page<Users> result = userRepository.findAll(specification, pageable);
        List<Users> users = result.getContent();
        List<String> userIds = users.stream().map(Users::getUsersId).collect(Collectors.toList());

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery = criteriaBuilder.createQuery(Users.class);
        Root<Users> root = criteriaQuery.from(Users.class);
        criteriaQuery.select(root).where(root.get("usersId").in(userIds));
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
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("users-joined");
        TypedQuery<Users> query = entityManager.createQuery(criteriaQuery);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        result = new PageImpl<>(query.getResultList(), pageable, result.getTotalElements());

        return new PageImpl<>(new ArrayList<>(UsersConverter.convertToUsersResponse(new LinkedHashSet<>(result.getContent()))), pageable, result.getTotalElements());
    }

    public UsersResponse getUserById(String id) {
//        Optional<Users> users = userRepository.findById(id);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery = criteriaBuilder.createQuery(Users.class);
        Root<Users> root = criteriaQuery.from(Users.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("usersId"), id));
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("users-joined");
        TypedQuery<Users> query = entityManager.createQuery(criteriaQuery);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        List<Users> userList = query.getResultList();
        if (userList == null || userList.size() == 0) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }
        Optional<Users> users = Optional.ofNullable(userList.get(0));

        return UsersConverter.convertToUsersResponse(users.orElse(null));
    }

    public UsersResponse updatePassword(String id, UpdatePasswordRequest request) {
        if (StringUtils.isBlankOrNull(id)) {
            throw new ServiceException(ErrorCode.ID_IS_NOT_EMPTY);
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new ServiceException(ErrorCode.PASSWORD_OLD_NOT_MATCH_PASSWORD_CONFIRM);
        }
        Users users = userRepository.findById(id).orElse(null);
        if (users == null) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }
        if (!encoder.matches(request.getOldPassword(), users.getPassword())) {
            throw new ServiceException(ErrorCode.OLD_PASSWORD_INCORRECT);
        }
        users.setPassword(encoder.encode(request.getNewPassword()));
        userRepository.save(users);
        return UsersConverter.convertToUsersResponseOnlyUser(users);
    }

    public String deleteUser(String usersId) {
        Users users = userRepository.findById(usersId).orElse(null);
        if (users == null) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }
        //check user in event
        List<UsersEvent> usersEvents = usersEventRepository.findAllByUsers_UsersId(usersId);
        if (usersEvents != null && usersEvents.size() > 0) {
            throw new ServiceException(ErrorCode.USER_IN_EVENT);
        }
        userRepository.delete(users);
        return "Delete user successfully";
    }

    public UsersResponse updatePasswordUser(UpdatePasswordUserRequest request) {
        Users users = userRepository.findById(request.getUserId()).orElse(null);
        if (users == null) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }
        users.setPassword(encoder.encode(request.getNewPassword()));
        userRepository.save(users);
        return UsersConverter.convertToUsersResponseOnlyUser(users);
    }
}
