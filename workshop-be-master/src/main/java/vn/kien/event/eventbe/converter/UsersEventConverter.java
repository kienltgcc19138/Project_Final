package vn.kien.event.eventbe.converter;

import vn.kien.event.eventbe.entity.Event;
import vn.kien.event.eventbe.entity.Users;
import vn.kien.event.eventbe.entity.UsersEvent;
import vn.kien.event.eventbe.response.UsersEventDetailResponse;
import vn.kien.event.eventbe.response.UsersEventResponse;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Le-Hong-Quan
 * Date: 01/03/2023
 * Time: 23:25
 */
public class UsersEventConverter {
    public static UsersEventResponse convertToUserEventResponse(UsersEvent userEvent) {
        if (userEvent == null) return null;
        UsersEventResponse userEventResponse = new UsersEventResponse();
        userEventResponse.setUsersId(userEvent.getUsers().getUsersId());
        userEventResponse.setEventId(userEvent.getEvent().getEventId());
        return userEventResponse;
    }

    public static Set<UsersEventResponse> convertToUserEventResponse(Set<UsersEvent> userEvents) {
        if (userEvents == null) return new LinkedHashSet<>();
        Set<UsersEventResponse> userEventResponses = new LinkedHashSet<>();
        for (UsersEvent userEvent : userEvents) {
            UsersEventResponse userEventResponse = new UsersEventResponse();
            userEventResponse.setUsersId(userEvent.getUsers().getUsersId());
            userEventResponse.setEventId(userEvent.getEvent().getEventId());
            userEventResponses.add(userEventResponse);
        }
        return userEventResponses;
    }

    public static Set<UsersEventResponse> convertToUserEventResponseWithUsers(Set<UsersEvent> userEvents) {
        if (userEvents == null) return new LinkedHashSet<>();
        Set<UsersEventResponse> userEventResponses = new LinkedHashSet<>();
        for (UsersEvent userEvent : userEvents) {
            UsersEventResponse userEventResponse = new UsersEventResponse();
            userEventResponse.setUsersId(userEvent.getUsers().getUsersId());
            userEventResponse.setUsersResponse(UsersConverter.convertToUsersResponseOnlyEvent(userEvent.getUsers()));
            userEventResponse.setEventId(userEvent.getEvent().getEventId());
            userEventResponse.setScore(userEvent.getScore());
            userEventResponses.add(userEventResponse);
        }
        return userEventResponses;
    }

    public static UsersEventDetailResponse convertToUsersDetailResponse(UsersEvent usersEvent) {
        if (usersEvent == null) return null;
        UsersEventDetailResponse usersEventDetailResponse = new UsersEventDetailResponse();
        usersEventDetailResponse.setUsersResponse(UsersConverter.convertToUsersResponseOnlyEvent(usersEvent.getUsers()));
        usersEventDetailResponse.setEventResponse(EventConverter.convertToEventResponseWithoutFile(usersEvent.getEvent()));
        usersEventDetailResponse.setScore(usersEvent.getScore());
        return usersEventDetailResponse;
    }

    public static Set<UsersEventDetailResponse> convertToUsersDetailResponses(Set<UsersEvent> usersEvent) {
        if (usersEvent == null) return new LinkedHashSet<>();
        Set<UsersEventDetailResponse> usersEventDetailResponses = new LinkedHashSet<>();
        for (UsersEvent userEvent : usersEvent) {
            usersEventDetailResponses.add(convertToUsersDetailResponse(userEvent));
        }
        return usersEventDetailResponses;
    }
}
