package vn.kien.event.eventbe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import vn.kien.event.eventbe.entity.Ticket;

/**
 * Le-Hong-Quan
 * Date: 05/03/2023
 * Time: 10:33
 */
@Repository
public interface ITicketRepository extends JpaRepository<Ticket, String>, JpaSpecificationExecutor {
    Page<Ticket> findAll(Specification specification, Pageable pageable);
}
