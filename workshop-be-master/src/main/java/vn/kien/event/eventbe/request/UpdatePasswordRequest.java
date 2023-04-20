package vn.kien.event.eventbe.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * Le-Hong-Quan
 * Date: 08/03/2023
 * Time: 14:38
 */
@Getter
@Setter
public class UpdatePasswordRequest {
    @NotEmpty(message = "Old password is not empty")
    private String oldPassword;
    @NotEmpty(message = "New password is not empty")
    private String newPassword;
    @NotEmpty(message = "Confirm password is not empty")
    private String confirmPassword;

}
