package vn.kien.event.eventbe.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * Le-Hong-Quan
 * Date: 09/03/2023
 * Time: 23:31
 */
@Getter
@Setter
public class CreateRecommendRequest {
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Description is required")
    private String description;

}
