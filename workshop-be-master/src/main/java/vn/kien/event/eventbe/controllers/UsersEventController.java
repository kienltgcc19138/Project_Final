package vn.kien.event.eventbe.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vn.kien.event.eventbe.annotations.swagger.RequiredHeaderToken;
import vn.kien.event.eventbe.converter.UsersEventConverter;
import vn.kien.event.eventbe.entity.Event;
import vn.kien.event.eventbe.entity.UsersEvent;
import vn.kien.event.eventbe.entity.UsersEventId;
import vn.kien.event.eventbe.model.wrapper.WrapResponse;
import vn.kien.event.eventbe.request.CreateAddScoreRequest;
import vn.kien.event.eventbe.request.RegisterEventRequest;
import vn.kien.event.eventbe.request.SearchUsersEventRequest;
import vn.kien.event.eventbe.response.UsersEventResponse;
import vn.kien.event.eventbe.services.UsersEventService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user-event")
@Tag(name = "User Event", description = "User Event API")
@RequiredArgsConstructor
public class UsersEventController {
    private final ExecutorService executorService;
    private final UsersEventService usersEventService;


    @RequiredHeaderToken
    @RequestMapping(value = "/add-score", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Object> createScore(@Valid @RequestBody List<CreateAddScoreRequest> request) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(usersEventService.addScoreUserEvent(request)), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}/score-by-usersId", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> getScoreUsers(@PathVariable("id") String id) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(usersEventService.getScoreUsers(id)), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> searchUsersEvent(@Valid @RequestBody SearchUsersEventRequest request) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(usersEventService.searchUsersEvent(request)), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}/register", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> registerEvent(@PathVariable("id") Long id,
                                                   @RequestBody RegisterEventRequest registerEventRequest) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(usersEventService.registerEvent(id, registerEventRequest)), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}/export-event", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Void> exportEvent(@PathVariable("id") Long id, HttpServletResponse response) {
        return CompletableFuture.supplyAsync(() -> {
            usersEventService.exportEvent(id, response);
            return null;
        }, executorService);
    }

}
