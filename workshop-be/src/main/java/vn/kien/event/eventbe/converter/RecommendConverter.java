package vn.kien.event.eventbe.converter;

import vn.kien.event.eventbe.entity.Recommend;
import vn.kien.event.eventbe.response.RecommendResponse;

import java.util.LinkedHashSet;

/**
 * Le-Hong-Quan
 * Date: 09/03/2023
 * Time: 23:36
 */
public class RecommendConverter {
    public static RecommendResponse convertToRecommendResponse(Recommend recommend) {
        if (recommend == null) {
            return null;
        }
        RecommendResponse recommendResponse = new RecommendResponse();
        recommendResponse.setRecommendId(recommend.getRecommendId());
        recommendResponse.setName(recommend.getName());
        recommendResponse.setDescription(recommend.getDescription());
        recommendResponse.setCreatedBy(recommend.getCreatedBy());
        recommendResponse.setUsersResponse(UsersConverter.convertToUsersResponseWithoutCourseMajorEvent(recommend.getUsers()));
        recommendResponse.setCreatedAt(recommend.getCreatedAt());
        return recommendResponse;
    }

    public static LinkedHashSet<RecommendResponse> convertToRecommendResponses(LinkedHashSet<Recommend> recommends) {
        LinkedHashSet<RecommendResponse> recommendResponses = new LinkedHashSet<>();
        for (Recommend recommend : recommends) {
            recommendResponses.add(convertToRecommendResponse(recommend));
        }
        return recommendResponses;
    }
}
