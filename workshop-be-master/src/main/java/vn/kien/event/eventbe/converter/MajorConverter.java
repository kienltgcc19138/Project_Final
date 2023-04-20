package vn.kien.event.eventbe.converter;

import vn.kien.event.eventbe.entity.Major;
import vn.kien.event.eventbe.response.MajorResponse;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Le-Hong-Quan
 * Date: 01/03/2023
 * Time: 23:23
 */
public class MajorConverter {
    public static MajorResponse convertToMajorResponse(Major major) {
        if (major == null) return null;
        MajorResponse majorResponse = new MajorResponse();
        majorResponse.setMajorId(major.getMajorId());
        majorResponse.setName(major.getName());
        majorResponse.setDescription(major.getDescription());
        majorResponse.setCreatedAt(major.getCreatedAt());
        majorResponse.setCreatedBy(major.getCreatedBy());
        return majorResponse;
    }

    public static Set<MajorResponse> convertToMajorResponses(Set<Major> majors) {
        if (majors == null) return new LinkedHashSet<>();
        Set<MajorResponse> majorResponses = new LinkedHashSet<>();
        for (Major major : majors) {
            majorResponses.add(convertToMajorResponse(major));
        }
        return majorResponses;
    }

    public static MajorResponse convertToMajorResponseWithUsers(Major major) {
        if (major == null) return null;
        MajorResponse majorResponse = new MajorResponse();
        majorResponse.setMajorId(major.getMajorId());
        majorResponse.setName(major.getName());
        majorResponse.setDescription(major.getDescription());
        majorResponse.setCreatedAt(major.getCreatedAt());
        majorResponse.setCreatedBy(major.getCreatedBy());
        majorResponse.setUsersResponses(UsersConverter.convertToUsersResponseWithoutCourseMajorEvents(new LinkedHashSet<>(major.getUsers())));
        return majorResponse;
    }
}
