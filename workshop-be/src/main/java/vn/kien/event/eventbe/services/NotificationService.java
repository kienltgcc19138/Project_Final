package vn.kien.event.eventbe.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vn.kien.event.eventbe.base.BaseSort;
import vn.kien.event.eventbe.converter.NotificationConverter;
import vn.kien.event.eventbe.entity.Event;
import vn.kien.event.eventbe.entity.Messages;
import vn.kien.event.eventbe.entity.Notification;
import vn.kien.event.eventbe.exception.ErrorCode;
import vn.kien.event.eventbe.exception.ServiceException;
import vn.kien.event.eventbe.repository.INotificationRepository;
import vn.kien.event.eventbe.request.SearchNotificationRequest;
import vn.kien.event.eventbe.response.NotificationResponse;
import vn.kien.event.eventbe.utils.PageableUtils;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class NotificationService {
    private final INotificationRepository notificationRepository;
    private final EntityManager entityManager;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public Page<NotificationResponse> searchNotification(String userId, SearchNotificationRequest request) {
        if (request.getPageSize() == null || request.getPageSize() == 0) {
            request.setPageSize(10);
        }
        Pageable pageable = PageableUtils.convertPageableAndSort(request.getPageNumber(), request.getPageSize(), request.getSort());
        Specification<Event> specification = Specification.where(null);
        specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("users").get("usersId"), userId));
        Page<Notification> notificationPage = notificationRepository.findAll(specification, pageable);

        List<Notification> notifications = notificationPage.getContent();
        List<Long> notificationIds = notifications.stream().map(Notification::getNotificationId).collect(Collectors.toList());

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);
        Root<Notification> root = criteriaQuery.from(Notification.class);
        criteriaQuery.select(root).where(root.get("notificationId").in(notificationIds));
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
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("notification-joined");
        TypedQuery<Notification> query = entityManager.createQuery(criteriaQuery);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        notificationPage = new PageImpl<>(query.getResultList(), pageable, notificationPage.getTotalElements());

        return new PageImpl<>(new ArrayList<>(NotificationConverter.convertNotificationResponses(new LinkedHashSet<>(notificationPage.getContent()))), pageable, notificationPage.getTotalElements());
    }

    public String readNotification(String userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow( () -> new ServiceException(ErrorCode.NOTIFICATION_NOT_FOUND));
        if (notification != null) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
        return "success";
    }
    public NotificationResponse createNotification(Notification notification) {
        Notification notification1 = notificationRepository.save(notification);
        return NotificationConverter.convertNotificationResponse(notification1);
    }

    public void sendNotification(String userId, NotificationResponse notificationResponse) {
        simpMessagingTemplate.convertAndSendToUser(userId, "/notification", notificationResponse);
    }
}
