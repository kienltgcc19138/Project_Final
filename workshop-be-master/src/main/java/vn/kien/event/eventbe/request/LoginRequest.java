package vn.kien.event.eventbe.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 13:15
 */
@Getter
@Setter
public class LoginRequest implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Email is not empty")
    private String email;

    @NotBlank(message = "Password is not empty")
    private String password;
    @NotBlank(message = "UrlContext is not empty")
    private String urlVerifyEmail;

}
