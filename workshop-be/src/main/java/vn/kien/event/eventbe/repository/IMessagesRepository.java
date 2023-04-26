package vn.kien.event.eventbe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.kien.event.eventbe.entity.Messages;
import vn.kien.event.eventbe.entity.Recommend;

/**
 * Le-Hong-Quan
 * Date: 12/03/2023
 * Time: 13:15
 */
@Repository
public interface IMessagesRepository extends JpaRepository<Messages, String>, JpaSpecificationExecutor {
//    @EntityGraph("messages-joined")
    Page<Messages> findAll(Specification specification, Pageable pageable);
}
