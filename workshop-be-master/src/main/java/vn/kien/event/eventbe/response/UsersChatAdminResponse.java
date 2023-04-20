package vn.kien.event.eventbe.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Le-Hong-Quan
 * Date: 13/03/2023
 * Time: 20:58
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersChatAdminResponse {
    private UsersResponse usersResponse;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastCreatedMessage;
}
