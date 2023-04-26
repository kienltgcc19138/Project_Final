package vn.kien.event.eventbe.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.kien.event.eventbe.model.wrapper.WrapResponse;
import vn.kien.event.eventbe.request.SignupRequest;
import vn.kien.event.eventbe.services.AccountService;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class Init {
    private final ExecutorService executorService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CompletableFuture<WrapResponse<Object>> helloApi() {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok("Api event-be"), executorService);
    }
}
