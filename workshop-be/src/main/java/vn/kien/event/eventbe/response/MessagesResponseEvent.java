package vn.kien.event.eventbe.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Le-Hong-Quan
 * Date: 12/03/2023
 * Time: 18:41
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessagesResponseEvent {
    private String usersId;
    private UsersResponse usersResponse;
    private String description;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    private String eventId;
}
