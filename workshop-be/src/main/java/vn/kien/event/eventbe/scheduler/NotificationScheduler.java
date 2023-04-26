package vn.kien.event.eventbe.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.kien.event.eventbe.converter.NotificationConverter;
import vn.kien.event.eventbe.entity.Event;
import vn.kien.event.eventbe.entity.Notification;
import vn.kien.event.eventbe.entity.Users;
import vn.kien.event.eventbe.entity.UsersEvent;
import vn.kien.event.eventbe.repository.IEventRepository;
import vn.kien.event.eventbe.repository.INotificationRepository;
import vn.kien.event.eventbe.services.MailService;
import vn.kien.event.eventbe.services.NotificationService;
import vn.kien.event.eventbe.utils.DateUtils;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Le-Hong-Quan
 * Date: 12/03/2023
 * Time: 23:48
 */
@Component
@RequiredArgsConstructor
public class NotificationScheduler {
    Logger logger = Logger.getLogger(this.getClass().getName());
    private final IEventRepository eventRepository;
    private final INotificationRepository notificationRepository;
    private final NotificationService notificationService;
    private final MailService mailService;

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
    public void run() {
        logger.info("--- start send notification");
        Date dateBeforeOneDateStartEvent = DateUtils.getDateAfterNumberTimes(new Date(), 1, DateUtils.DAILY);
        Date start = DateUtils.customFromDate(dateBeforeOneDateStartEvent);
        Date end = DateUtils.customToDate(dateBeforeOneDateStartEvent);
        List<Event> events = eventRepository.findAllByTimeStartIsBetween(start, end);
        for (Event event : events) {
            Set<UsersEvent> users = event.getUsers();
            for (UsersEvent user : users) {
//                Notification notification = new Notification();
//                notification.setUsers(user.getUsers());
//                notification.setRead(false);
//                notification.setDescription("Event " + event.getName() + " will start in " + event.getTimeStart());
//                notification.setCreatedAt(new Date());
//                notification.setEventId(event.getEventId());
//                notificationRepository.save(notification);
//                notificationService.sendNotification(user.getUsers().getUsersId(), NotificationConverter.convertNotificationResponse(notification));
                try {
                    mailService.sendNotifyEmailRemindUsersEvent(user.getUsers(), event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("send notification to user " + user.getUsers().getEmail());
            }
        }
        logger.info("--- end send notification");
    }
}
