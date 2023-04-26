package vn.kien.event.eventbe.services;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.kien.event.eventbe.base.BaseService;
import vn.kien.event.eventbe.base.BaseSort;
import vn.kien.event.eventbe.common.EnumConst;
import vn.kien.event.eventbe.converter.EventConverter;
import vn.kien.event.eventbe.entity.Event;
import vn.kien.event.eventbe.entity.FileData;
import vn.kien.event.eventbe.entity.Users;
import vn.kien.event.eventbe.exception.ErrorCode;
import vn.kien.event.eventbe.exception.ServiceException;
import vn.kien.event.eventbe.repository.IEventRepository;
import vn.kien.event.eventbe.repository.IFileDataRepository;
import vn.kien.event.eventbe.repository.IUsersRepository;
import vn.kien.event.eventbe.request.*;
import vn.kien.event.eventbe.response.EventResponse;
import vn.kien.event.eventbe.utils.MappingUtils;
import vn.kien.event.eventbe.utils.PageableUtils;
import vn.kien.event.eventbe.utils.StringUtils;


import javax.mail.MessagingException;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EventService extends BaseService {
    private final EntityManager entityManager;
    private final IEventRepository eventRepository;
    private final IUsersRepository userRepository;
    private final MailService mailService;
    private final IFileDataRepository fileDataRepository;


    @Transactional(rollbackFor = Exception.class)
    public EventResponse createEvent(CreateEventRequest request, String createdBy) {
        if (request.getTimeStart().before(new Date())) {
            throw new ServiceException(ErrorCode.TIME_START_MUST_AFTER_NOW);
        }
        if (request.getTimeEnd().before(request.getTimeStart())) {
            throw new ServiceException(ErrorCode.TIME_START_MUST_BEFORE_TIME_END);
        }
        Event event = MappingUtils.mapObject(request, Event.class);
        event.setCreatedBy(createdBy);
        event.setCreatedAt(new Date());
        event.setStatus(EnumConst.EventStatusEnum.UPCOMING.toString());
        FileData fileData = null;
        if (request.getFileId() != null) {
            fileData = fileDataRepository.findById(request.getFileId()).orElse(null);
            if (fileData == null) {
                throw new ServiceException(ErrorCode.FILE_NOT_FOUND);
            }
            event.setFiles(new LinkedHashSet<>(Collections.singletonList(fileData)));
        }
        Event eventCreate = eventRepository.save(event);
        //update file event
        if (fileData != null) {
            fileData.setEvent(eventCreate);
            fileDataRepository.save(fileData);
        }
        //send mail
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(new Runnable() {
            public void run() {
                List<Users> users = userRepository.findAllByRolesIs(EnumConst.UserRolesEnum.ROLE_USER.toString());
                if (users != null && !users.isEmpty()) {
                    for (Users user : users) {
                        try {
                            mailService.sendEmailEventCreateToUser(eventCreate, user);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        logger.info("Send mail event create to user: " + user.getEmail());
                    }
                }
            }
        });
        executor.shutdown();
        return EventConverter.convertToEventResponses(eventCreate);
    }

    @Transactional(rollbackFor = Exception.class)
    public EventResponse updateEvent(Long id, UpdateEventRequest request) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) {
            throw new ServiceException(ErrorCode.EVENT_NOT_FOUND);
        }
        if (request.getTimeEnd().before(request.getTimeStart())) {
            throw new ServiceException(ErrorCode.TIME_START_MUST_BEFORE_TIME_END);
        }
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setTopic(request.getTopic());
        event.setLocation(request.getLocation());
        event.setTimeStart(request.getTimeStart());
        event.setTimeEnd(request.getTimeEnd());
        event.setScore(request.getScore());
        FileData fileData = null;
        if (request.getFileId() != null) {
            fileData = fileDataRepository.findById(request.getFileId()).orElse(null);
            if (fileData == null) {
                throw new ServiceException(ErrorCode.FILE_NOT_FOUND);
            }
            fileData.setEvent(event);
            Set<FileData> fileDataRemoves = event.getFiles();
            if (fileDataRemoves != null && !fileDataRemoves.isEmpty()) {
                for (FileData fileDataRemove : fileDataRemoves) {
                    fileDataRemove.setEvent(null);
                    fileDataRepository.save(fileDataRemove);
                }
            }
            event.setFiles(new LinkedHashSet<>(Collections.singletonList(fileData)));
        }
        Event eventUpdate = eventRepository.save(event);
        //update file event
        if (fileData != null) {
            fileData.setEvent(eventUpdate);
            fileDataRepository.save(fileData);
        }
//        return getEventById(id);
        return EventConverter.convertToEventResponses(eventUpdate);
    }

    public EventResponse getEventById(Long id) {
        Event event = eventRepository.findByEventId(id);
        if (event == null) {
            throw new ServiceException(ErrorCode.EVENT_NOT_FOUND);
        }
        return EventConverter.convertToDetailEventResponses(event);
    }


    public Page<EventResponse> searchEvent(SearchEventRequest request) {
        if (request.getPageSize() == null || request.getPageSize() == 0) {
            request.setPageSize(10);
        }
        Pageable pageable = PageableUtils.convertPageableAndSort(request.getPageNumber(), request.getPageSize(), request.getSort());
        Specification<Event> specification = Specification.where(null);
        if (!StringUtils.isBlankOrNull(request.getKeyword())) {
            specification = specification.or((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + request.getKeyword() + "%"));
            specification = specification.or((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), "%" + request.getKeyword() + "%"));
            specification = specification.or((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("topic"), "%" + request.getKeyword() + "%"));
            specification = specification.or((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("location"), "%" + request.getKeyword() + "%"));
        }
        if (request.getFromStartDate() != null) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("timeStart"), request.getFromStartDate()));
        }
        if (request.getToStartDate() != null) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("timeStart"), request.getToStartDate()));
        }
        if (!StringUtils.isBlankOrNull(request.getUserId())) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join("users").get("users").get("usersId"), request.getUserId()));
        }
        if (!StringUtils.isBlankOrNull(request.getStatus())) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), request.getStatus()));
        }
        Page<Event> eventPage = eventRepository.findAll(specification, pageable);

        List<Event> eventList = eventPage.getContent();
        List<Long> eventIds = eventList.stream().map(Event::getEventId).collect(Collectors.toList());

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> queryWithIds = criteriaBuilder.createQuery(Event.class);
        Root<Event> root = queryWithIds.from(Event.class);
        queryWithIds.select(root).where(root.get("eventId").in(eventIds));
        if (request.getSort() != null && request.getSort().size() > 0) {
            List<Order> orders = new ArrayList<>();
            for (BaseSort sortRequest : request.getSort()) {
                if (sortRequest.getAsc()) {
                    orders.add(criteriaBuilder.asc(root.get(sortRequest.getKey())));
                } else {
                    orders.add(criteriaBuilder.desc(root.get(sortRequest.getKey())));
                }
            }
            queryWithIds.orderBy(orders);
        }


        EntityGraph<?> entityGraph = entityManager.getEntityGraph("event-joined");
        TypedQuery<Event> query = entityManager.createQuery(queryWithIds);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        eventPage = new PageImpl<>(query.getResultList(), pageable, eventPage.getTotalElements());
        Set<EventResponse> eventResponseList = EventConverter.convertToEventResponses(new LinkedHashSet<>(eventPage.getContent()));
        return new PageImpl<>(new ArrayList<>(eventResponseList), pageable, eventPage.getTotalElements());
    }

//    public Page<EventResponse> searchEventByUser(String userId, SearchEventUserRequest request) {
//        if (request.getPageSize() == null || request.getPageSize() == 0) {
//            request.setPageSize(10);
//        }
//        Pageable pageable = PageableUtils.convertPageableAndSort(request.getPageNumber(), request.getPageSize(), request.getSort());
//        Specification<Event> specification = Specification.where(null);
//        if (!StringUtils.isBlankOrNull(request.getKeyword())) {
//            specification = specification.or((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + request.getKeyword() + "%"));
//            specification = specification.or((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), "%" + request.getKeyword() + "%"));
//            specification = specification.or((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("topic"), "%" + request.getKeyword() + "%"));
//            specification = specification.or((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("location"), "%" + request.getKeyword() + "%"));
//        }
//        if (request.getFromStartDate() != null) {
//            specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("timeStart"), request.getFromStartDate()));
//        }
//        if (request.getToStartDate() != null) {
//            specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("timeStart"), request.getToStartDate()));
//        }
//        specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join("users").get("users").get("usersId"), userId));
//        Page<Event> eventPage = eventRepository.findAll(specification, pageable);
//
//        Set<EventResponse> eventResponseList = EventConverter.convertToEventResponses(new LinkedHashSet<>(eventPage.getContent()));
//        return new PageImpl<>(new ArrayList<>(eventResponseList), pageable, eventPage.getTotalElements());
//    }


    @Transactional(rollbackFor = Exception.class)
    public String deleteEvent(Long id) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) {
            throw new ServiceException(ErrorCode.EVENT_NOT_FOUND);
        }
        eventRepository.delete(event);
        return "Delete success";
    }

    public String updateStatusEvent(Long id, UpdateStatusEventRequest request) {
        try {
            EnumConst.EventStatusEnum.valueOf(request.getStatus());
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.STATUS_MUST_IN, EnumConst.EventStatusEnum.UPCOMING.name(), EnumConst.EventStatusEnum.HAPPENING.name(), EnumConst.EventStatusEnum.HAPPENED.name());
        }

        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) {
            throw new ServiceException(ErrorCode.EVENT_NOT_FOUND);
        }
        if (StringUtils.isBlankOrNull(request.getStatus())) {
            throw new ServiceException(ErrorCode.STATUS_NOT_FOUND);
        }
        Date now = new Date();
        if (request.getStatus().equals(EnumConst.EventStatusEnum.UPCOMING.toString())) {
            if (now.after(event.getTimeStart())) {
                throw new ServiceException(ErrorCode.TIME_EVENT_NOT_VALID_TO_UPDATE_STATUS_UPCOMING.toString());
            }
        }
        if (request.getStatus().equals(EnumConst.EventStatusEnum.HAPPENING.toString())) {
            if (now.before(event.getTimeStart()) || now.after(event.getTimeEnd())) {
                throw new ServiceException(ErrorCode.TIME_EVENT_NOT_VALID_TO_UPDATE_STATUS_HAPPENING.toString());
            }
        }
        if (request.getStatus().equals(EnumConst.EventStatusEnum.HAPPENED.toString())) {
            if (now.before(event.getTimeEnd())) {
                throw new ServiceException(ErrorCode.TIME_EVENT_NOT_VALID_TO_UPDATE_STATUS_HAPPENED.toString());
            }
        }
        event.setStatus(request.getStatus());
        eventRepository.save(event);
        return "Update status success";
    }
}
