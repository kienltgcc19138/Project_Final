package vn.kien.event.eventbe.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Le-Hong-Quan
 * Date: 27/02/2023
 * Time: 21:44
 */
@Getter
@Setter
public class CreateCourseRequest {
    @NotBlank(message = "Name is not empty")
    private String name;
    private String description;

}
