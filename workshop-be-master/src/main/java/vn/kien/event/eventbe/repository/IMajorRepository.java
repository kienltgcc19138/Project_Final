package vn.kien.event.eventbe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.kien.event.eventbe.entity.Major;

/**
 * Le-Hong-Quan
 * Date: 27/02/2023
 * Time: 20:31
 */
@Repository
public interface IMajorRepository extends JpaRepository<Major, Long>, JpaSpecificationExecutor {
    Page<Major> findAll(Specification specification, Pageable pageable);

    @EntityGraph("major-joined")
    Major findByMajorId(Long majorId);
}
