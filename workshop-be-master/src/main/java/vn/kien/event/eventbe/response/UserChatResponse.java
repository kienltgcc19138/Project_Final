package vn.kien.event.eventbe.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Le-Hong-Quan
 * Date: 13/03/2023
 * Time: 21:31
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserChatResponse {
    private UsersResponse usersResponse;
    private Date lastCreatedMessage;
}
