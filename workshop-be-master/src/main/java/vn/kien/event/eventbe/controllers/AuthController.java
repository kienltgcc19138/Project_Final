package vn.kien.event.eventbe.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.kien.event.eventbe.model.wrapper.WrapResponse;
import vn.kien.event.eventbe.request.LoginRequest;
import vn.kien.event.eventbe.request.SignupRequest;
import vn.kien.event.eventbe.request.VerifyLoginEmailRequest;
import vn.kien.event.eventbe.services.AccountService;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Auth API")
@RequiredArgsConstructor
public class AuthController  {
    private final ExecutorService executorService;
    private final AccountService accountService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CompletableFuture<WrapResponse<Object>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(accountService.login(loginRequest)), executorService);
    }

    @RequestMapping(value = "/verify-email-login", method = RequestMethod.POST)
    public CompletableFuture<WrapResponse<Object>> verifyEmailLogin(@Valid @RequestBody VerifyLoginEmailRequest verifyLoginEmailRequest) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(accountService.verifyLoginEmail(verifyLoginEmailRequest)), executorService);
    }

}
