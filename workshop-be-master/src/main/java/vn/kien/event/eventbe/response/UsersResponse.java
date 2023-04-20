package vn.kien.event.eventbe.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.kien.event.eventbe.entity.Users;

import java.util.Date;
import java.util.Set;

/**
 * Le-Hong-Quan
 * Date: 20/02/2023
 * Time: 23:45
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersResponse {
    private String usersId;
    private String email;
    private String fullName;
    private String phone;
    private String roles;
    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    private CourseResponse courseResponse;
    private MajorResponse majorResponse;
    private Set<UsersEventResponse> userEventResponses;

    private Float totalScore;

    public UsersResponse(Users users) {
        this.usersId = users.getUsersId();
        this.email = users.getEmail();
        this.fullName = users.getFullName();
        this.phone = users.getPhone();
        this.roles = users.getRoles();
        this.gender = users.getGender();
        this.birthday = users.getBirthday();
    }
}
