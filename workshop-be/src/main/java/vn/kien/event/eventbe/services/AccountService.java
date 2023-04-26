package vn.kien.event.eventbe.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.kien.event.eventbe.base.BaseService;
import vn.kien.event.eventbe.common.EnumConst;
import vn.kien.event.eventbe.entity.Users;
import vn.kien.event.eventbe.entity.UsersPreference;
import vn.kien.event.eventbe.exception.ErrorCode;
import vn.kien.event.eventbe.exception.ServiceException;
import vn.kien.event.eventbe.jwt.JwtUtils;
import vn.kien.event.eventbe.repository.IUsersRepository;
import vn.kien.event.eventbe.repository.IUsersPreferenceRepository;
import vn.kien.event.eventbe.request.LoginRequest;
import vn.kien.event.eventbe.request.VerifyLoginEmailRequest;
import vn.kien.event.eventbe.response.JwtResponse;
import vn.kien.event.eventbe.response.LoginResponse;

import javax.mail.MessagingException;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class AccountService extends BaseService {

    @Value("${URL_VERIFY_LOGIN}")
    private String urlVerifyLogin;
    private final IUsersRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;
    private final IUsersPreferenceRepository usersPreferenceRepository;


    public boolean checkExistenceByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new ServiceException(ErrorCode.INVALID_ACCOUNT);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Users users = (Users) authentication.getPrincipal();
        validateAccount(users);

        String jwt = jwtUtils.generateJwtToken(authentication);

        //generate a refresh token for refresh access token
//        String refreshToken = jwtUtils.generateJwtRefreshToken(account.getUsername());
//        setRefreshToken(account.getUsername(), refreshToken);

        try {
            // Create code verify email
            UsersPreference usersPreference = new UsersPreference();
            usersPreference.setUsersId(users.getUsersId());
            usersPreference.setType(EnumConst.UserPreferenceTypeEnum.VERIFY_LOGIN.toString());
            int randomCode = (int) Math.floor(Math.random() * (999999 - 100000 + 1) + 100000);
            usersPreference.setCode(randomCode + "");
            usersPreference.setCreatedAt(new Date());
            usersPreferenceRepository.save(usersPreference);
            String url = loginRequest.getUrlVerifyEmail() + "?code=" + usersPreference.getCode() + "&token=" + jwt;
            mailService.sendEmailLogin(users, usersPreference.getCode(), url);
        } catch (MessagingException e) {
            throw new ServiceException(ErrorCode.MAIL_SENDING_ERROR);
        }
        return new LoginResponse("Your code has been sent to your email! Please check your email to verify login!");
    }

    public JwtResponse verifyLoginEmail(VerifyLoginEmailRequest verifyLoginEmailRequest) {
        String token = verifyLoginEmailRequest.getToken();
        String code = verifyLoginEmailRequest.getCode();
        String email = jwtUtils.getUserNameFromJwtToken(token);
        Users users = userRepository.findByEmail(email).orElse(null);
        validateAccount(users);
        UsersPreference usersPreference = usersPreferenceRepository.findByUsersIdAndCodeAndType(users.getUsersId(), code, EnumConst.UserPreferenceTypeEnum.VERIFY_LOGIN.toString());
        if (usersPreference == null) {
            throw new ServiceException(ErrorCode.INVALID_CODE);
        }
        usersPreferenceRepository.delete(usersPreference);

        String jwt = jwtUtils.generateJwtTokenWithEmail(users.getEmail());
        return new JwtResponse(jwt, null, users);
    }

    private void validateAccount(Users users) {
        if (users == null) {
            throw new ServiceException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        Users users1 = userRepository.findByEmail(users.getEmail()).orElse(null);
        assert users1 != null;
//        if (users1.getStatus().equals(EnumConst.UserStatusEnum.DISABLE.toString())) {
//            throw new ServiceException(ErrorCode.ACCOUNT_NOT_ACTIVE);
//        }
    }

//    public VerifyEmailResponse verifyEmail(String userId, String code) {
//        UsersPreference usersPreference = usersPreferenceRepository.findByUsersId(userId);
//        if (usersPreference == null) {
//            throw new ServiceException(ErrorCode.ACCOUNT_NOT_FOUND);
//        }
//        Users users = userRepository.findById(userId).orElse(null);
//        if (users == null) {
//            throw new ServiceException(ErrorCode.ACCOUNT_NOT_FOUND);
//        }
//        if (!usersPreference.getCode().equals(code)) {
//            throw new ServiceException(ErrorCode.INVALID_CODE_ACTIVE_ACCOUNT);
//        }
////        users.setStatus(EnumConst.UserStatusEnum.ENABLE.toString());
//        userRepository.save(users);
//
//        return new VerifyEmailResponse("Verify email successfully", null);
//    }
}

