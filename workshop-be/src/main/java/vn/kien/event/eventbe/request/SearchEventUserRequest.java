package vn.kien.event.eventbe.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import vn.kien.event.eventbe.base.BasePageAndSortRequest;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * Le-Hong-Quan
 * Date: 05/03/2023
 * Time: 01:41
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchEventUserRequest extends BasePageAndSortRequest {
    @NotEmpty(message = "userId is required")
    private String userId;
    private String keyword;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fromStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date toStartDate;
}
