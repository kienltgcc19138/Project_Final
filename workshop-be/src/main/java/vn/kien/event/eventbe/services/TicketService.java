package vn.kien.event.eventbe.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.kien.event.eventbe.common.EnumConst;
import vn.kien.event.eventbe.converter.TicketConverter;
import vn.kien.event.eventbe.entity.Event;
import vn.kien.event.eventbe.entity.Ticket;
import vn.kien.event.eventbe.repository.IEventRepository;
import vn.kien.event.eventbe.repository.ITicketRepository;
import vn.kien.event.eventbe.request.CreateTicketRequest;
import vn.kien.event.eventbe.request.SearchTicketRequest;
import vn.kien.event.eventbe.response.TicketResponse;
import vn.kien.event.eventbe.utils.PageableUtils;
import vn.kien.event.eventbe.utils.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;


@Service
@RequiredArgsConstructor
public class TicketService {
    private final ITicketRepository ticketRepository;
    private final IEventRepository eventRepository;


    @Transactional(rollbackFor = Exception.class)
    public TicketResponse createFeedBack(CreateTicketRequest request, String createdBy) {
        Ticket ticket = new Ticket();
        ticket.setName(request.getName());
        ticket.setObjectType(EnumConst.TicketTypeEnum.FEED_BACK.toString());
        ticket.setDescription(request.getDescription());
        ticket.setCreatedBy(createdBy);
        ticket.setCreatedAt(new Date());
        Event event = eventRepository.findById(request.getEventId()).orElseThrow(() -> new RuntimeException("Event not found"));
        ticket.setEvent(event);
        Ticket ticketCreate = ticketRepository.save(ticket);
        return TicketConverter.convertTicketResponse(ticketCreate);
    }


    public TicketResponse createQuestion(CreateTicketRequest request, String usersId) {
        Ticket ticket = new Ticket();
        ticket.setName(request.getName());
        ticket.setObjectType(EnumConst.TicketTypeEnum.QUESTION.toString());
        ticket.setDescription(request.getDescription());
        ticket.setCreatedBy(usersId);
        ticket.setCreatedAt(new Date());
        Event event = eventRepository.findById(request.getEventId()).orElseThrow(() -> new RuntimeException("Event not found"));
        ticket.setEvent(event);
        Ticket ticketCreate = ticketRepository.save(ticket);
        return TicketConverter.convertTicketResponse(ticketCreate);
    }


    public TicketResponse getTicketDetail(String id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
        return TicketConverter.convertTicketResponse(ticket);
    }

    public Page<TicketResponse> searchTicket(SearchTicketRequest request) {
        if (request.getPageSize() == null || request.getPageSize() == 0) {
            request.setPageSize(10);
        }
        Pageable pageable = PageableUtils.convertPageableAndSort(request.getPageNumber(), request.getPageSize(), request.getSort());
        Specification<Ticket> specification = Specification.where(null);
        if (!StringUtils.isBlankOrNull(request.getObjectType())) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("objectType"), request.getObjectType()));
        }
        if (!StringUtils.isBlankOrNull(request.getKeyword())) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + request.getKeyword() + "%"));
        }
        if (request.getEventId() != null) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("event").get("eventId"), request.getEventId()));
        }
        if (!StringUtils.isBlankOrNull(request.getUsersId())) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("createdBy"), request.getUsersId()));
        }
        Page<Ticket> ticketPage = ticketRepository.findAll(specification, pageable);
        return new PageImpl<>(new ArrayList<>(TicketConverter.convertTicketResponses(new LinkedHashSet<>(ticketPage.getContent()))), pageable, ticketPage.getTotalElements());
    }


}
