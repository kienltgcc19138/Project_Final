package vn.kien.event.eventbe.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Le-Hong-Quan
 * Date: 20/02/2023
 * Time: 21:22
 */
@Getter
@Setter
public class CreateUserRequest {
    @NotBlank(message = "Email is not empty")
    private String email;
    @NotBlank(message = "Password is not empty")
    private String password;
    @NotBlank(message = "FullName is not empty")
    private String fullName;
    @NotBlank(message = "Phone is not empty")
    private String phone;
    @NotBlank(message = "Gender is not empty")
    @Pattern(regexp = "^(MALE|FEMALE)$", message = "Gender is MALE or FEMALE")
    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    private Long majorId;
    private Long courseId;
}
