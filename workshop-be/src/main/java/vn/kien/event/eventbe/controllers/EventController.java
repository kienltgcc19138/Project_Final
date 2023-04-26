package vn.kien.event.eventbe.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vn.kien.event.eventbe.annotations.swagger.RequiredHeaderToken;
import vn.kien.event.eventbe.entity.Users;
import vn.kien.event.eventbe.model.wrapper.WrapResponse;
import vn.kien.event.eventbe.request.*;
import vn.kien.event.eventbe.services.EventService;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/event")
@Tag(name = "Event", description = "Event API")
@RequiredArgsConstructor
public class EventController {
    private final ExecutorService executorService;

    private final EventService eventService;

    @RequiredHeaderToken
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Object> createEvent(@Valid @RequestBody CreateEventRequest request,
                                                 @Parameter(hidden = true) @AuthenticationPrincipal Users users) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(eventService.createEvent(request, users.getUsersId())), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}/status", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Object> updateStatusEvent(@PathVariable("id") Long id,
                                                       @Valid @RequestBody UpdateStatusEventRequest request) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(eventService.updateStatusEvent(id, request)), executorService);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CompletableFuture<Object> getEvent(@PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(eventService.getEventById(id)), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Object> updateEvent(@Valid @RequestBody UpdateEventRequest request,
                                                 @PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(eventService.updateEvent(id, request)), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Object> deleteEvent(@PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(eventService.deleteEvent(id)), executorService);
    }

    @RequestMapping(value = "/search-event", method = RequestMethod.POST)
    public CompletableFuture<Object> searchEvent(@Valid @RequestBody SearchEventRequest request) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(eventService.searchEvent(request)), executorService);
    }


}
