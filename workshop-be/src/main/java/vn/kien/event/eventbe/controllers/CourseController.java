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
import vn.kien.event.eventbe.services.CourseService;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/course")
@Tag(name = "Course", description = "Course API")
@RequiredArgsConstructor
public class CourseController {
    private final ExecutorService executorService;
    private final CourseService courseService;

    @RequiredHeaderToken
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Object> createCourse(@Valid @RequestBody CreateCourseRequest request,
                                                  @Parameter(hidden = true) @AuthenticationPrincipal Users users) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(courseService.createCourse(request, users.getUsersId())), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/search-course", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> searchCourse(@Valid @RequestBody SearchCourseRequest request) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(courseService.searchCourse(request)), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> getCourseDetail(@PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(courseService.getCourseDetail(id)), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Object> updateCourse(@Valid @RequestBody UpdateCourseRequest request,
                                                 @PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(courseService.updateCourse(id, request)), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Object> deleteMajor(@PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(courseService.deleteCourse(id)), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}/add-users", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Object> addUsersCourse(@Valid @RequestBody AddUsersCourseRequest request,
                                                    @PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(courseService.addUsersCourse(id, request)), executorService);
    }
}
