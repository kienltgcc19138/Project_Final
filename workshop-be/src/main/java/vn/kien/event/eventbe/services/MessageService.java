package vn.kien.event.eventbe.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import vn.kien.event.eventbe.base.BaseSort;
import vn.kien.event.eventbe.common.EnumConst;
import vn.kien.event.eventbe.converter.MessagesConverter;
import vn.kien.event.eventbe.converter.UsersConverter;
import vn.kien.event.eventbe.entity.Event;
import vn.kien.event.eventbe.entity.Messages;
import vn.kien.event.eventbe.entity.Users;
import vn.kien.event.eventbe.exception.ErrorCode;
import vn.kien.event.eventbe.exception.ServiceException;
import vn.kien.event.eventbe.repository.IEventRepository;
import vn.kien.event.eventbe.repository.IMessagesRepository;
import vn.kien.event.eventbe.repository.IUsersRepository;
import vn.kien.event.eventbe.request.CreateMessageRequest;
import vn.kien.event.eventbe.request.SearchMessageRequest;
import vn.kien.event.eventbe.response.MessagesResponseEvent;
import vn.kien.event.eventbe.response.UsersChatAdminResponse;
import vn.kien.event.eventbe.response.UsersResponse;
import vn.kien.event.eventbe.utils.PageableUtils;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MessageService {
    private final EntityManager entityManager;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final IMessagesRepository messagesRepository;
    private final IUsersRepository usersRepository;
    private final IEventRepository eventRepository;


    public Page<MessagesResponseEvent> searchMessages(String usersIds, SearchMessageRequest request) {
        if (request.getPageSize() == null || request.getPageSize() == 0) {
            request.setPageSize(10);
        }
        Pageable pageable = PageableUtils.convertPageableAndSort(request.getPageNumber(), request.getPageSize(), request.getSort());
        Specification<Messages> specification = Specification.where(null);
        specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("users").get("usersId"), usersIds));
        Page<Messages> messagesPage = messagesRepository.findAll(specification, pageable);

        List<Messages> messages = messagesPage.getContent();
        List<String> messagesIds = messages.stream().map(Messages::getId).collect(Collectors.toList());

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Messages> criteriaQuery = criteriaBuilder.createQuery(Messages.class);
        Root<Messages> root = criteriaQuery.from(Messages.class);
        criteriaQuery.select(root).where(criteriaBuilder.and(criteriaBuilder.in(root.get("id")).value(messagesIds)));
        if (request.getSort() != null && request.getSort().size() > 0) {
            List<Order> orders = new ArrayList<>();
            for (BaseSort sortRequest : request.getSort()) {
                if (sortRequest.getAsc()) {
                    orders.add(criteriaBuilder.asc(root.get(sortRequest.getKey())));
                } else {
                    orders.add(criteriaBuilder.desc(root.get(sortRequest.getKey())));
                }
            }
            criteriaQuery.orderBy(orders);
        }
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("messages-joined");
        TypedQuery<Messages> query = entityManager.createQuery(criteriaQuery);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        messagesPage = new PageImpl<>(query.getResultList(), pageable, messagesPage.getTotalElements());

        return new PageImpl<>(new ArrayList<>(MessagesConverter.convertToMessagesResponseEvents(new LinkedHashSet<>(messagesPage.getContent()))), pageable, messagesPage.getTotalElements());
    }

    @Transactional(rollbackFor = Exception.class)
    public MessagesResponseEvent sendUserMessage(String usersId, CreateMessageRequest createMessageRequest) {
//        Users users = usersRepository.findById(usersId).orElseThrow(() -> new RuntimeException(ErrorCode.USER_NOT_FOUND));
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery = criteriaBuilder.createQuery(Users.class);
        Root<Users> root = criteriaQuery.from(Users.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("usersId"), usersId));
        TypedQuery<Users> query = entityManager.createQuery(criteriaQuery);
        Users users = query.getSingleResult();
        if (users.getRoles().contains(EnumConst.UserRolesEnum.ROLE_ADMIN.toString())) {
            throw new ServiceException(ErrorCode.ADMIN_NOT_SEND_MESSAGE_EVENT);
        }


        Messages messages = new Messages();
        messages.setUsers(users);
        messages.setCreatedAt(new Date());
        messages.setCreatedBy(usersId);
        messages.setType(EnumConst.MessageType.SEND.toString());
        messages.setDescription(createMessageRequest.getContent());
        messagesRepository.save(messages);
        simpMessagingTemplate.convertAndSendToUser(usersId, "/message", MessagesConverter.convertToMessagesResponseEvent(messages));
        String content = createMessageRequest.getContent();


        boolean isAutomatic = false;
        List<Messages> messagesReceives = new ArrayList<>();
        if (content.contains(EnumConst.DEFAULT_MESSAGE.EVENT_LIST_HAPPENING.getDescription())) {
            isAutomatic = true;
            List<Event> events = eventRepository.findAllByStatus(EnumConst.EventStatusEnum.HAPPENING.toString());
            int i = 1;
            for (Event event : events) {
                Messages messagesAuto = new Messages();
                messagesAuto.setUsers(users);
                messagesAuto.setCreatedAt(new Date());
                messagesAuto.setCreatedBy("DEFAULT_MESSAGE");
                messagesAuto.setType(EnumConst.MessageType.RECEIVE.toString());
                messagesAuto.setDescription(i + "." + EnumConst.DEFAULT_MESSAGE.EVENT_INFORMATION.getDescription() + ": " + event.getName() + " #" + event.getEventId());
                messagesRepository.save(messagesAuto);
                i++;
                messagesReceives.add(messagesAuto);
            }
            if (!CollectionUtils.isEmpty(messagesReceives)) {
                simpMessagingTemplate.convertAndSendToUser(usersId, "/message", MessagesConverter.convertToMessagesResponseEvents(new LinkedHashSet<>(messagesReceives)));
            } else {
                Messages messagesAuto = new Messages();
                messagesAuto.setUsers(users);
                messagesAuto.setCreatedAt(new Date());
                messagesAuto.setCreatedBy("DEFAULT_MESSAGE");
                messagesAuto.setType(EnumConst.MessageType.RECEIVE.toString());
                messagesAuto.setDescription("There are no events going on");
                messagesRepository.save(messagesAuto);
                simpMessagingTemplate.convertAndSendToUser(usersId, "/message", MessagesConverter.convertToMessagesResponseEvent(messagesAuto));
            }
        } else if (content.contains(EnumConst.DEFAULT_MESSAGE.EVENT_TIME.getDescription()) && content.contains("#")) {
            isAutomatic = true;
            String eventId = content.substring(content.indexOf("#") + 1);
            Event event = eventRepository.findById(Long.valueOf(eventId)).orElseThrow(() -> new RuntimeException(ErrorCode.EVENT_NOT_FOUND));
            Messages messagesAuto = new Messages();
            messagesAuto.setUsers(users);
            messagesAuto.setCreatedAt(new Date());
            messagesAuto.setCreatedBy("DEFAULT_MESSAGE");
            messagesAuto.setType(EnumConst.MessageType.RECEIVE.toString());
            messagesAuto.setDescription(EnumConst.DEFAULT_MESSAGE.EVENT_TIME.getDescription() + ": " + event.getTimeStart() + " -> " + event.getTimeEnd());
            messagesRepository.save(messagesAuto);
            messagesReceives.add(messagesAuto);
            simpMessagingTemplate.convertAndSendToUser(usersId, "/message", MessagesConverter.convertToMessagesResponseEvent(messagesReceives.get(0)));
        } else if (content.contains(EnumConst.DEFAULT_MESSAGE.EVENT_LOCATION.getDescription()) && content.contains("#")) {
            isAutomatic = true;
            String eventId = content.substring(content.indexOf("#") + 1);
            Event event = eventRepository.findById(Long.valueOf(eventId)).orElseThrow(() -> new RuntimeException(ErrorCode.EVENT_NOT_FOUND));
            Messages messagesAuto = new Messages();
            messagesAuto.setUsers(users);
            messagesAuto.setCreatedAt(new Date());
            messagesAuto.setCreatedBy("DEFAULT_MESSAGE");
            messagesAuto.setType(EnumConst.MessageType.RECEIVE.toString());
            messagesAuto.setDescription(EnumConst.DEFAULT_MESSAGE.EVENT_LOCATION.getDescription() +
                    ": " + event.getLocation());
            messagesRepository.save(messagesAuto);
            messagesReceives.add(messagesAuto);
            simpMessagingTemplate.convertAndSendToUser(usersId, "/message", MessagesConverter.convertToMessagesResponseEvent(messagesReceives.get(0)));
        } else if (content.contains(EnumConst.DEFAULT_MESSAGE.EVENT_INFORMATION.getDescription()) && content.contains("#")) {
            isAutomatic = true;
            String eventId = content.substring(content.indexOf("#") + 1);
            Event event = eventRepository.findByEventId(Long.valueOf(eventId));
            Messages messagesAuto1 = new Messages();
            messagesAuto1.setUsers(users);
            messagesAuto1.setCreatedAt(new Date());
            messagesAuto1.setCreatedBy("DEFAULT_MESSAGE");
            messagesAuto1.setType(EnumConst.MessageType.RECEIVE.toString());
            messagesAuto1.setDescription("1. " + EnumConst.DEFAULT_MESSAGE.EVENT_TIME.getDescription() + " " + event.getName() + " #" + event.getEventId());
            messagesRepository.save(messagesAuto1);
            messagesReceives.add(messagesAuto1);

            Messages messagesAuto2 = new Messages();
            messagesAuto2.setUsers(users);
            messagesAuto2.setCreatedAt(new Date());
            messagesAuto2.setCreatedBy("DEFAULT_MESSAGE");
            messagesAuto2.setType(EnumConst.MessageType.RECEIVE.toString());
            messagesAuto2.setDescription("2. " + EnumConst.DEFAULT_MESSAGE.EVENT_LOCATION.getDescription() + ": " + event.getName() + " #" + event.getEventId());
            messagesRepository.save(messagesAuto1);
            messagesReceives.add(messagesAuto2);
            simpMessagingTemplate.convertAndSendToUser(usersId, "/message", MessagesConverter.convertToMessagesResponseEvents(new LinkedHashSet<>(messagesReceives)));
        }

        // get list user admin
        List<Users> usersAdmins = usersRepository.findAllByRolesIs(EnumConst.UserRolesEnum.ROLE_ADMIN.toString());
        for (Users usersAdmin : usersAdmins) {
            simpMessagingTemplate.convertAndSendToUser(usersAdmin.getUsersId(), "/message", MessagesConverter.convertToMessagesResponseEvent(messages));
            if (isAutomatic) {
                if (content.contains(EnumConst.DEFAULT_MESSAGE.EVENT_LIST_HAPPENING.getDescription())) {
                    simpMessagingTemplate.convertAndSendToUser(usersAdmin.getUsersId(), "/message", MessagesConverter.convertToMessagesResponseEvents(new LinkedHashSet<>(messagesReceives)));
                } else {
                    simpMessagingTemplate.convertAndSendToUser(usersAdmin.getUsersId(), "/message", MessagesConverter.convertToMessagesResponseEvent(messagesReceives.get(0)));
                }
            }
        }
        return MessagesConverter.convertToMessagesResponseEvent(messages);
    }

    @Transactional(rollbackFor = Exception.class)
    public MessagesResponseEvent sendAdminMessage(String usersId, CreateMessageRequest createMessageRequest, String createdBy) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery = criteriaBuilder.createQuery(Users.class);
        Root<Users> root = criteriaQuery.from(Users.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("usersId"), usersId));
        TypedQuery<Users> query = entityManager.createQuery(criteriaQuery);
        List<Users> users = query.getResultList();
        if (users == null || users.isEmpty()) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }

        Messages messages = new Messages();
        messages.setUsers(users.get(0));
        messages.setCreatedAt(new Date());
        messages.setCreatedBy(createdBy);
        messages.setType(EnumConst.MessageType.RECEIVE.toString());
        messages.setDescription(createMessageRequest.getContent());
        messagesRepository.save(messages);
        simpMessagingTemplate.convertAndSendToUser(usersId, "/message", MessagesConverter.convertToMessagesResponseEvent(messages));
        List<Users> usersAdmins = usersRepository.findAllByRolesIs(EnumConst.UserRolesEnum.ROLE_ADMIN.toString());
        for (Users usersAdmin : usersAdmins) {
            simpMessagingTemplate.convertAndSendToUser(usersAdmin.getUsersId(), "/message", MessagesConverter.convertToMessagesResponseEvent(messages));
        }
        return MessagesConverter.convertToMessagesResponseEvent(messages);
    }

    public Page<UsersChatAdminResponse> searchUserChatWithAdmin(SearchMessageRequest searchMessageRequest) {
        if (searchMessageRequest.getPageSize() == null || searchMessageRequest.getPageSize() == 0) {
            searchMessageRequest.setPageSize(10);
        }
        Pageable pageable = PageableUtils.convertPageableAndSort(searchMessageRequest.getPageNumber(), searchMessageRequest.getPageSize(), searchMessageRequest.getSort());
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
        Root<Messages> root = criteriaQuery.from(Messages.class);
        List<Predicate> predicates = new ArrayList<>();
//        predicates.add(criteriaBuilder.equal(root.get("type"), EnumConst.MessageType.SEND.toString()));
//        predicates.add(criteriaBuilder.equal(root.get("users").get("roles"), EnumConst.UserRolesEnum.ROLE_USER.toString()));
        criteriaQuery.multiselect(root.get("users").get("usersId"), criteriaBuilder.max(root.get("createdAt")));
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        criteriaQuery.groupBy(root.get("users").get("usersId"));
        criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.max(root.get("createdAt"))));
        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<Tuple> tuples = typedQuery.getResultList();
        List<UsersChatAdminResponse> usersChatAdminResponses = new ArrayList<>();
        for (Tuple tuple : tuples) {
            UsersChatAdminResponse usersChatAdminResponse = new UsersChatAdminResponse();
            String usersId = tuple.get(0, String.class);
            TypedQuery<Users> usersTypedQuery = entityManager.createQuery("select u from Users u where u.usersId = :usersId", Users.class);
            usersTypedQuery.setParameter("usersId", usersId);
            Users users = usersTypedQuery.getSingleResult();

            usersChatAdminResponse.setUsersResponse(UsersConverter.convertToUsersResponseWithoutCourseMajorEvent(users));
            usersChatAdminResponse.setLastCreatedMessage(tuple.get(1, Date.class));
            usersChatAdminResponses.add(usersChatAdminResponse);
        }
        Long count = entityManager.createQuery("select count(distinct m.users.usersId) from Messages m ", Long.class)
                .getSingleResult();
        return new PageImpl<>(usersChatAdminResponses, pageable, count);
    }
}
