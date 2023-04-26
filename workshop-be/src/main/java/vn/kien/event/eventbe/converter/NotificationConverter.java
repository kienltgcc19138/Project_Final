package vn.kien.event.eventbe.converter;

import vn.kien.event.eventbe.entity.Notification;
import vn.kien.event.eventbe.response.NotificationResponse;

import java.util.LinkedHashSet;

/**
 * Le-Hong-Quan
 * Date: 12/03/2023
 * Time: 23:40
 */
public class NotificationConverter {
    public static NotificationResponse convertNotificationResponse(Notification notification) {
        if (notification == null) {
            return null;
        }
        NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setUsersResponse(UsersConverter.convertToUsersResponseWithoutCourseMajorEvent(notification.getUsers()));
        notificationResponse.setNotificationId(notification.getNotificationId());
        notificationResponse.setDescription(notification.getDescription());
        notificationResponse.setCreatedAt(notification.getCreatedAt());
        notificationResponse.setEventId(notification.getEventId());
        notificationResponse.setRead(notification.isRead());
        return notificationResponse;
    }

    public static LinkedHashSet<NotificationResponse> convertNotificationResponses(LinkedHashSet<Notification> notifications) {
        LinkedHashSet<NotificationResponse> notificationResponses = new LinkedHashSet<>();
        for (Notification notification : notifications) {
            notificationResponses.add(convertNotificationResponse(notification));
        }
        return notificationResponses;
    }
}
