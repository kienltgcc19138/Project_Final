package vn.kien.event.eventbe.services;

import lombok.RequiredArgsConstructor;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;
import vn.kien.event.eventbe.entity.Event;
import vn.kien.event.eventbe.entity.FileData;
import vn.kien.event.eventbe.entity.Users;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;

import org.thymeleaf.context.Context;


@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendEmailLogin(Users usersRegister, String code, String urlVerifyEmail) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable("fullName", usersRegister.getFullName());
        context.setVariable("url", urlVerifyEmail);
        context.setVariable("code", code);
        String html = templateEngine.process("login-mail", context);
        helper.setFrom("msonlineservicesgreenwich@gmail.com");
        helper.setSubject("Confirm Login");
        helper.setTo(usersRegister.getEmail());
        helper.setText(html, true);

        emailSender.send(message);
    }

    public void sendEmailEventCreateToUser(Event eventCreate, Users users) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable("fullName", users.getFullName());
        context.setVariable("eventName", eventCreate.getName());
        context.setVariable("topic", eventCreate.getName());
        context.setVariable("dateStart", new SimpleDateFormat("yyyy-MM-dd").format(eventCreate.getTimeStart()));
        context.setVariable("timeStart", new SimpleDateFormat("HH:mm").format(eventCreate.getTimeStart()));
        context.setVariable("location", eventCreate.getLocation());
        String image = "";
        if (eventCreate.getFiles() != null) {
            for (FileData fileData : eventCreate.getFiles()) {
                image += fileData.getLink();
                break;
            }
        }
        context.setVariable("image", image);
        String html = templateEngine.process("event-create", context);
        helper.setFrom("msonlineservicesgreenwich@gmail.com");
        helper.setSubject("New Event Information");
        helper.setTo(users.getEmail());
        helper.setText(html, true);

        emailSender.send(message);
    }


    public void sendMailRegisterEvent(Users users, Event event) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable("fullName", users.getFullName());
        context.setVariable("eventName", event.getName());
        context.setVariable("topic", event.getName());
        context.setVariable("dateStart", new SimpleDateFormat("yyyy-MM-dd").format(event.getTimeStart()));
        context.setVariable("timeStart", new SimpleDateFormat("HH:mm").format(event.getTimeStart()));
        context.setVariable("location", event.getLocation());
        String html = templateEngine.process("event-register-successfully", context);
        helper.setFrom("msonlineservicesgreenwich@gmail.com");
        helper.setSubject("Register Event Successfully");
        helper.setTo(users.getEmail());
        helper.setText(html, true);

        emailSender.send(message);
    }

    public void sendNotifyEmailRemindUsersEvent(Users users, Event event) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable("fullName", users.getFullName());
        context.setVariable("eventName", event.getName());
        context.setVariable("topic", event.getName());
        context.setVariable("dateStart", new SimpleDateFormat("yyyy-MM-dd").format(event.getTimeStart()));
        context.setVariable("timeStart", new SimpleDateFormat("HH:mm").format(event.getTimeStart()));
        context.setVariable("location", event.getLocation());
        String html = templateEngine.process("announcement-of-the-event", context);
        helper.setFrom("msonlineservicesgreenwich@gmail.com");
        helper.setSubject("Announcement of the event");
        helper.setTo(users.getEmail());
        helper.setText(html, true);
        emailSender.send(message);
    }

    public void sendEmailCreateUser(String email,String password) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable("email", email);
        context.setVariable("password", password);
        String html = templateEngine.process("create-user", context);
        helper.setFrom("msonlineservicesgreenwich@gmail.com");
        helper.setSubject("Confirm Login");
        helper.setTo(email);
        helper.setText(html, true);

        emailSender.send(message);
    }
}
