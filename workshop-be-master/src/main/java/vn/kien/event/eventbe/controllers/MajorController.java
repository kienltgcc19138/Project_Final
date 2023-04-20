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
import vn.kien.event.eventbe.request.AddUsersMajorRequest;
import vn.kien.event.eventbe.request.CreateMajorRequest;
import vn.kien.event.eventbe.request.SearchMajorRequest;
import vn.kien.event.eventbe.request.UpdateMajorRequest;
import vn.kien.event.eventbe.services.MajorService;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/major")
@Tag(name = "Major", description = "Major API")
@RequiredArgsConstructor
public class MajorController {
    private final ExecutorService executorService;
    private final MajorService majorService;

    @RequiredHeaderToken
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Object> createMajor(@Valid @RequestBody CreateMajorRequest createMajorRequest,
                                                 @Parameter(hidden = true) @AuthenticationPrincipal Users users) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(majorService.createMajor(createMajorRequest, users.getUsersId())), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> getMajorDetail(@PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(majorService.getMajorDetail(id)), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/search-major", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> searchMajor(@Valid @RequestBody SearchMajorRequest searchMajorRequest) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(majorService.searchMajor(searchMajorRequest)), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Object> updateMajor(@Valid @RequestBody UpdateMajorRequest updateMajorRequest,
                                                 @PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(majorService.updateMajor(id, updateMajorRequest)), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Object> deleteMajor(@PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(majorService.deleteMajor(id)), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}/add-usersIds", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Object> addStudentToMajor(@PathVariable("id") Long id,
                                                      @RequestBody AddUsersMajorRequest usersIds) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(majorService.addStudentToMajor(id, usersIds)), executorService);
    }
}

