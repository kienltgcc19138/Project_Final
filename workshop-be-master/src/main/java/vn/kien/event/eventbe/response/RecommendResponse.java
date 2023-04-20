package vn.kien.event.eventbe.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Le-Hong-Quan
 * Date: 09/03/2023
 * Time: 23:35
 */

@Getter
@Setter
public class RecommendResponse {
    private Long recommendId;
    private String name;
    private String description;
    private String createdBy;
    private UsersResponse usersResponse;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
}
