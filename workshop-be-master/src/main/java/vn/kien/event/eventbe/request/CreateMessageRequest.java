package vn.kien.event.eventbe.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * Le-Hong-Quan
 * Date: 12/03/2023
 * Time: 12:08
 */
@Getter
@Setter
public class CreateMessageRequest {
    @NotEmpty(message = "Content is required")
    private String content;
}
