package vn.kien.event.eventbe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.kien.event.eventbe.entity.Event;
import vn.kien.event.eventbe.entity.UsersEvent;
import vn.kien.event.eventbe.entity.UsersEventId;

import javax.persistence.NamedEntityGraph;
import java.util.List;
import java.util.Optional;

/**
 * Le-Hong-Quan
 * Date: 05/03/2023
 * Time: 01:54
 */
@Repository
public interface IUsersEventRepository extends JpaRepository<UsersEvent, UsersEventId>, JpaSpecificationExecutor {
    @EntityGraph("users-event-joined")
    List<UsersEvent> findAllByEvent_EventIdAndIsJoin(Long eventId,Boolean isJoin);

//    @EntityGraph("users-event-joined")
    Page<UsersEvent> findAll(Specification specification, Pageable pageable);

    List<UsersEvent> findAllByUsers_UsersId(String userId);
}
