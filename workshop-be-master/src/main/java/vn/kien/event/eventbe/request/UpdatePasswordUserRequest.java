package vn.kien.event.eventbe.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Le-Hong-Quan
 * Date: 16/04/2023
 * Time: 10:28
 */
@Getter
@Setter
public class UpdatePasswordUserRequest {
    private String userId;
    private String newPassword;
}
