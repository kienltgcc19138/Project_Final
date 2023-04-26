package vn.kien.workshop;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import vn.kien.event.eventbe.EventBeApplication;
import vn.kien.event.eventbe.request.CreateUserRequest;
import vn.kien.event.eventbe.services.UsersService;

import java.util.Date;

/**
 * Le-Hong-Quan
 * Date: 28/02/2023
 * Time: 00:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = EventBeApplication.class)
//@AutoConfigureMockMvc
public class CreateUser {
    @Autowired
    private UsersService usersService;

    @Test
    public void createAdmin() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("kienle0660@gmail.com");
        createUserRequest.setFullName("Le Trung Kien Admin");
        createUserRequest.setGender("MALE");
        createUserRequest.setPhone("0123456789");
        createUserRequest.setPassword("123456");
        createUserRequest.setBirthday(new Date());
        usersService.createUser(createUserRequest, null, true);
    }

//    @Test
//    public void createUser() {
//        CreateUserRequest createUserRequest = new CreateUserRequest();
////        createUserRequest.setEmail("kienltgcc19138@fpt.edu.vn");
//        createUserRequest.setEmail("lhquan2@yopmail.com");
//        createUserRequest.setFullName("KLe Hong Quan User");
//        createUserRequest.setGender("MALE");
//        createUserRequest.setPhone("0123456789");
//        createUserRequest.setPassword("123456");
//        createUserRequest.setBirthday(new Date());
//        usersService.createUser(createUserRequest, null, false);
//    }
}
