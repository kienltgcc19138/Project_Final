package vn.kien.event.eventbe.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

/**
 * Le-Hong-Quan
 * Date: 20/02/2023
 * Time: 22:53
 */
@Getter
@Setter
public class UpdateUserRequest {
    @NotBlank(message = "Id is not empty")
    private String id;
    private String email;

//    private String password;

    private String fullName;

    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    private Long majorId;
    private Long courseId;

    @NotBlank(message = "Gender is not empty")
    @Pattern(regexp = "^(MALE|FEMALE)$", message = "Gender is MALE or FEMALE")
    private String gender;
}
