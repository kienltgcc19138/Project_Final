package vn.kien.event.eventbe.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.kien.event.eventbe.base.BaseService;
import vn.kien.event.eventbe.converter.CourseConverter;
import vn.kien.event.eventbe.entity.Course;
import vn.kien.event.eventbe.entity.Users;
import vn.kien.event.eventbe.exception.ErrorCode;
import vn.kien.event.eventbe.exception.ServiceException;
import vn.kien.event.eventbe.repository.ICourseRepository;
import vn.kien.event.eventbe.request.*;
import vn.kien.event.eventbe.response.CourseResponse;
import vn.kien.event.eventbe.utils.PageableUtils;
import vn.kien.event.eventbe.utils.StringUtils;

import javax.persistence.EntityManager;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CourseService extends BaseService {
    private final ICourseRepository courseRepository;
    private final EntityManager entityManager;

    @Transactional(rollbackFor = Exception.class)
    public CourseResponse createCourse(CreateCourseRequest request, String createdBy) {
        Course course = new Course();
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setCreatedAt(new Date());
        course.setCreatedBy(createdBy);
        Course courseCreate = courseRepository.save(course);
        return CourseConverter.convertToCourseResponse(courseCreate);
    }

    public CourseResponse getCourseDetail(Long id) {
        Course course = courseRepository.findByCourseId(id);
        if (course == null) {
            throw new ServiceException(ErrorCode.COURSE_NOT_FOUND);
        }
        return CourseConverter.convertToCourseResponseWithUsers(course);
    }

    public Page<CourseResponse> searchCourse(SearchCourseRequest request) {
        if (request.getPageSize() == null || request.getPageSize() == 0) {
            request.setPageSize(10);
        }
        Pageable pageable = PageableUtils.convertPageableAndSort(request.getPageNumber(), request.getPageSize(), request.getSort());
        Specification<Users> specification = Specification.where(null);
        if (!StringUtils.isBlankOrNull(request.getKeyword())) {
            specification = specification.or((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + request.getKeyword() + "%"));
        }
        Page<Course> coursePage = courseRepository.findAll(specification, pageable);
        return new PageImpl<>(new ArrayList<>(CourseConverter.convertToCourseResponses(new LinkedHashSet<>(coursePage.getContent()))), pageable, coursePage.getTotalElements());
    }

    @Transactional(rollbackFor = Exception.class)
    public CourseResponse updateCourse(Long id, UpdateCourseRequest request) {
        Course course = courseRepository.findByCourseId(id);
        if (course == null) {
            throw new ServiceException(ErrorCode.COURSE_NOT_FOUND);
        }
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        Course courseUpdate = courseRepository.save(course);
        return CourseConverter.convertToCourseResponse(courseUpdate);
    }

    @Transactional(rollbackFor = Exception.class)
    public String deleteCourse(Long id) {
        Course course = courseRepository.findByCourseId(id);
        if (course == null) {
            throw new ServiceException(ErrorCode.COURSE_NOT_FOUND);
        }
        courseRepository.delete(course);
        return "Delete success";
    }

    @Transactional(rollbackFor = Exception.class)
    public CourseResponse addUsersCourse(Long id, AddUsersCourseRequest request) {
        Course course = courseRepository.findByCourseId(id);
        if (course == null) {
            throw new ServiceException(ErrorCode.COURSE_NOT_FOUND);
        }
        List<String> usersIds = request.getUsersIds();
        List<Users> users = entityManager.createQuery("select u from Users u where u.usersId in :usersIds", Users.class)
                .setParameter("usersIds", usersIds)
                .getResultList();
        if (users == null) {
            throw new ServiceException(ErrorCode.COURSE_NOT_FOUND);
        }
        course.getUsers().addAll(users);
        courseRepository.save(course);

        return getCourseDetail(id);
    }
}
