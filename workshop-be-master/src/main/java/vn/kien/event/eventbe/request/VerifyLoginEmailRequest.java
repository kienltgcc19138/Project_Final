package vn.kien.event.eventbe.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Le-Hong-Quan
 * Date: 20/02/2023
 * Time: 20:21
 */
@Getter
@Setter
public class VerifyLoginEmailRequest {
    private static final long serialVersionUID = 1L;
    @NotBlank(message = "Code is not empty")
    private String code;
    @NotBlank(message = "Token is not empty")
    private String token;
}
