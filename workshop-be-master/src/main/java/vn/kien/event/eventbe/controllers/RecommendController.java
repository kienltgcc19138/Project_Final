package vn.kien.event.eventbe.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vn.kien.event.eventbe.annotations.swagger.RequiredHeaderToken;
import vn.kien.event.eventbe.entity.Users;
import vn.kien.event.eventbe.request.CreateRecommendRequest;
import vn.kien.event.eventbe.request.SearchRecommendRequest;
import vn.kien.event.eventbe.request.UpdateRecommendRequest;
import vn.kien.event.eventbe.services.RecommendService;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/recommend")
@Tag(name = "Recommend", description = "Recommend API")
@RequiredArgsConstructor
public class RecommendController {
    private final RecommendService recommendService;

    @RequiredHeaderToken
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> createRecommend(@Valid @RequestBody CreateRecommendRequest createRecommendRequest,
                                                     @Parameter(hidden = true) @AuthenticationPrincipal Users users) {
        return CompletableFuture.supplyAsync(() -> recommendService.createRecommend(createRecommendRequest, users.getUsersId()));
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> getRecommend(@PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> recommendService.getRecommendDetail(id));
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> deleteRecommend(@PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> recommendService.deleteRecommend(id));
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> updateRecommend(@PathVariable("id") Long id, @Valid @RequestBody UpdateRecommendRequest updateRecommendRequest,
                                                     @Parameter(hidden = true) @AuthenticationPrincipal Users users) {
        return CompletableFuture.supplyAsync(() -> recommendService.updateRecommend(id, updateRecommendRequest, users.getUsersId()));
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> searchRecommend(@Valid @RequestBody SearchRecommendRequest searchRecommendRequest) {
        return CompletableFuture.supplyAsync(() -> recommendService.searchRecommend(searchRecommendRequest));
    }
}
