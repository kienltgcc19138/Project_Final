package vn.kien.event.eventbe.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.kien.event.eventbe.entity.Users;

import javax.persistence.*;
import java.util.Date;

/**
 * Le-Hong-Quan
 * Date: 12/03/2023
 * Time: 23:38
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long notificationId;

    private UsersResponse usersResponse;
    private String description;
    private boolean isRead;
    private Long eventId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
}
