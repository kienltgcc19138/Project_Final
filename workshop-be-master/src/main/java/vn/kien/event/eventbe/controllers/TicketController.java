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
import vn.kien.event.eventbe.request.CreateTicketRequest;
import vn.kien.event.eventbe.request.SearchTicketRequest;
import vn.kien.event.eventbe.services.TicketService;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/ticket")
@Tag(name = "Ticket", description = "Ticket API")
@RequiredArgsConstructor
public class TicketController {
    private final ExecutorService executorService;
    private final TicketService ticketService;

    @RequiredHeaderToken
    @RequestMapping(value = "/feed-back", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> createFeedBack(@Valid @RequestBody CreateTicketRequest request,
                                                    @Parameter(hidden = true) @AuthenticationPrincipal Users users) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(ticketService.createFeedBack(request, users.getUsersId())), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/question", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> createQuestion(@Valid @RequestBody CreateTicketRequest request,
                                                     @Parameter(hidden = true) @AuthenticationPrincipal Users users) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(ticketService.createQuestion(request, users.getUsersId())), executorService);
    }
    @RequiredHeaderToken
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Object> getTicketDetail(@PathVariable("id") String id) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(ticketService.getTicketDetail(id)), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> searchTicket(@Valid @RequestBody SearchTicketRequest request) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(ticketService.searchTicket(request)), executorService);
    }

}
