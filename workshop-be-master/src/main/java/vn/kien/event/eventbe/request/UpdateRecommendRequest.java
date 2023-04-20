package vn.kien.event.eventbe.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Le-Hong-Quan
 * Date: 09/03/2023
 * Time: 23:31
 */
@Getter
@Setter
public class UpdateRecommendRequest {
    private String name;
    private String description;

}
