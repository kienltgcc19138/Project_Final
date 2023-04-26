package vn.kien.event.eventbe.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * Le-Hong-Quan
 * Date: 01/03/2023
 * Time: 00:24
 */
@Getter
@Setter
public class RegisterEventRequest {
    @NotEmpty(message = "usersId is required")
    private String usersId;
}
