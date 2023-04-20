package vn.kien.event.eventbe.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Le-Hong-Quan
 * Date: 27/02/2023
 * Time: 23:41
 */
@Getter
@Setter
public class UpdateEventRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Topic is required")
    private String topic;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Location is required")
    private String location;
    @NotNull(message = "Time start is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeStart;
    @NotNull(message = "Time end is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeEnd;
    @NotNull(message = "Score is required")
    private Float score;

    private Long fileId;

}
