package vn.kien.event.eventbe.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

/**
 * Le-Hong-Quan
 * Date: 27/02/2023
 * Time: 23:34
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {
    private Long eventId;
    private String name;
    private String topic;
    private String description;
    private String location;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeStart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeEnd;
    //    private String fromDate;
//    private String toDate;
    private Float score;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    private Set<UsersEventResponse> userEventResponses;
    private Set<FileDataResponse> fileDataResponses;
}
