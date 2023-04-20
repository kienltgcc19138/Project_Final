package vn.kien.event.eventbe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.kien.event.eventbe.entity.Recommend;
import vn.kien.event.eventbe.entity.Ticket;

/**
 * Le-Hong-Quan
 * Date: 09/03/2023
 * Time: 23:29
 */
@Repository
public interface IRecommendRepository extends JpaRepository<Recommend, Long>, JpaSpecificationExecutor {
//    @EntityGraph("recommend-users-joined")
    Page<Recommend> findAll(Specification specification, Pageable pageable);
}
