package vn.kien.event.eventbe.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import vn.kien.event.eventbe.base.BasePageAndSortRequest;

import java.util.Date;

/**
 * Le-Hong-Quan
 * Date: 27/02/2023
 * Time: 23:46
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchEventRequest extends BasePageAndSortRequest {
    private String keyword;
    private String userId;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fromStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date toStartDate;
}
