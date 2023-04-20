package vn.kien.event.eventbe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.kien.event.eventbe.entity.Event;

import java.util.Date;
import java.util.List;

/**
 * Le-Hong-Quan
 * Date: 27/02/2023
 * Time: 23:31
 */
@Repository
public interface IEventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor {
//    @EntityGraph("event-joined")
    Page<Event> findAll(Specification specification, Pageable pageable);

    @EntityGraph(value = "event-joined", type = EntityGraph.EntityGraphType.LOAD)
    Event findByEventId(Long eventId);

    @EntityGraph("event-joined")
    List<Event> findAllByTimeStartIsBetween(Date start, Date end);

    List<Event> findAllByStatus(String status);
}
