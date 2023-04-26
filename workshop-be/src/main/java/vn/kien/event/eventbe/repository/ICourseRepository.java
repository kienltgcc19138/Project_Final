package vn.kien.event.eventbe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.kien.event.eventbe.entity.Course;

import java.util.List;

/**
 * Le-Hong-Quan
 * Date: 27/02/2023
 * Time: 21:39
 */
@Repository
public interface ICourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor {
    Page<Course> findAll(Specification specification, Pageable pageable);

    @EntityGraph("course-joined")
    Course findByCourseId(Long courseId);

}
