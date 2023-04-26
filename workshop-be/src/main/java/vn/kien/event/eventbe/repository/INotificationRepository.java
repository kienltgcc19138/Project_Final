package vn.kien.event.eventbe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.kien.event.eventbe.entity.Notification;
import vn.kien.event.eventbe.entity.Recommend;

/**
 * Le-Hong-Quan
 * Date: 12/03/2023
 * Time: 23:34
 */
@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor {
//    @EntityGraph("notification-joined")
    Page<Notification> findAll(Specification specification, Pageable pageable);
}
