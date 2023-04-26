package vn.kien.event.eventbe.converter;

import vn.kien.event.eventbe.entity.Course;
import vn.kien.event.eventbe.response.CourseResponse;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


public class CourseConverter {
    public static CourseResponse convertToCourseResponse(Course course) {
        if (course == null) return null;
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setCourseId(course.getCourseId());
        courseResponse.setName(course.getName());
        courseResponse.setDescription(course.getDescription());
        courseResponse.setCreatedAt(course.getCreatedAt());
        courseResponse.setCreatedBy(course.getCreatedBy());
        return courseResponse;
    }

    public static Set<CourseResponse> convertToCourseResponses(Set<Course> courses) {
        if (courses == null) return new LinkedHashSet<>();
        Set<CourseResponse> courseResponses = new LinkedHashSet<>();
        for (Course course : courses) {
            CourseResponse courseResponse = new CourseResponse();
            courseResponse.setCourseId(course.getCourseId());
            courseResponse.setName(course.getName());
            courseResponse.setDescription(course.getDescription());
            courseResponse.setCreatedAt(course.getCreatedAt());
            courseResponse.setCreatedBy(course.getCreatedBy());
            courseResponses.add(courseResponse);
        }
        return courseResponses;
    }

    public static CourseResponse convertToCourseResponseWithUsers(Course course) {
        if (course == null) return null;
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setCourseId(course.getCourseId());
        courseResponse.setName(course.getName());
        courseResponse.setDescription(course.getDescription());
        courseResponse.setCreatedAt(course.getCreatedAt());
        courseResponse.setCreatedBy(course.getCreatedBy());
        courseResponse.setUsersResponses(UsersConverter.convertToUsersResponseWithoutCourseMajorEvents(new LinkedHashSet<>(course.getUsers())));
        return courseResponse;
    }
}
