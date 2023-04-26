package vn.kien.event.eventbe.converter;

import vn.kien.event.eventbe.entity.Users;
import vn.kien.event.eventbe.entity.UsersEvent;
import vn.kien.event.eventbe.response.UsersResponse;

import java.util.*;

/**
 * Le-Hong-Quan
 * Date: 01/03/2023
 * Time: 23:08
 */
public class UsersConverter {
    public static UsersResponse convertToUsersResponseOnlyUser(Users users){
        if (users == null) return null;
        UsersResponse usersResponse = new UsersResponse();
        usersResponse.setUsersId(users.getUsersId());
        usersResponse.setEmail(users.getEmail());
        usersResponse.setFullName(users.getFullName());
        usersResponse.setPhone(users.getPhone());
        usersResponse.setRoles(users.getRoles());
        usersResponse.setGender(users.getGender());
        usersResponse.setBirthday(users.getBirthday());
        return usersResponse;
    }
    public static UsersResponse convertToUsersResponse(Users user) {
        if (user == null) return null;
        UsersResponse usersResponse = new UsersResponse();
        usersResponse.setUsersId(user.getUsersId());
        usersResponse.setEmail(user.getEmail());
        usersResponse.setFullName(user.getFullName());
        usersResponse.setPhone(user.getPhone());
        usersResponse.setRoles(user.getRoles());
        usersResponse.setGender(user.getGender());
        usersResponse.setBirthday(user.getBirthday());
        usersResponse.setCourseResponse(CourseConverter.convertToCourseResponse(user.getCourse()));
        usersResponse.setMajorResponse(MajorConverter.convertToMajorResponse(user.getMajor()));
        usersResponse.setUserEventResponses(UsersEventConverter.convertToUserEventResponse(user.getEvents()));
        Set<UsersEvent> usersEvents = user.getEvents();
        Float totalScore = 0f;
        if (usersEvents != null) {
            for (UsersEvent usersEvent : usersEvents) {
                totalScore += usersEvent.getScore() != null ? usersEvent.getScore() : 0;
            }
        }
        usersResponse.setTotalScore(totalScore);
        return usersResponse;
    }

    public static UsersResponse convertToUsersResponseOnlyEvent(Users user) {
        if (user == null) return null;
        UsersResponse usersResponse = new UsersResponse();
        usersResponse.setUsersId(user.getUsersId());
        usersResponse.setEmail(user.getEmail());
        usersResponse.setFullName(user.getFullName());
        usersResponse.setPhone(user.getPhone());
        usersResponse.setRoles(user.getRoles());
        usersResponse.setGender(user.getGender());
        usersResponse.setBirthday(user.getBirthday());
        return usersResponse;
    }

    public static Set<UsersResponse> convertToUsersResponse(Set<Users> users) {
        if (users == null) return new LinkedHashSet<>();
        Set<UsersResponse> usersResponses = new LinkedHashSet<>();
        for (Users user : users) {
            UsersResponse usersResponse = new UsersResponse();
            usersResponse.setUsersId(user.getUsersId());
            usersResponse.setEmail(user.getEmail());
            usersResponse.setFullName(user.getFullName());
            usersResponse.setPhone(user.getPhone());
            usersResponse.setRoles(user.getRoles());
            usersResponse.setGender(user.getGender());
            usersResponse.setBirthday(user.getBirthday());
            usersResponse.setCourseResponse(CourseConverter.convertToCourseResponse(user.getCourse()));
            usersResponse.setMajorResponse(MajorConverter.convertToMajorResponse(user.getMajor()));
            usersResponse.setUserEventResponses(UsersEventConverter.convertToUserEventResponse(user.getEvents()));
            Set<UsersEvent> usersEvents = user.getEvents();
            Float totalScore = 0f;
            if (usersEvents != null) {
                for (UsersEvent usersEvent : usersEvents) {
                    totalScore += usersEvent.getScore() != null ? usersEvent.getScore() : 0;
                }
            }
            usersResponse.setTotalScore(totalScore);
            usersResponses.add(usersResponse);
        }
        return usersResponses;
    }

    public static List<UsersResponse> convertToUsersResponseList(List<Users> users) {
        if (users == null) return new ArrayList<>();
        List<UsersResponse> usersResponses = new ArrayList<>();
        for (Users user : users) {
            UsersResponse usersResponse = new UsersResponse();
            usersResponse.setUsersId(user.getUsersId());
            usersResponse.setEmail(user.getEmail());
            usersResponse.setFullName(user.getFullName());
            usersResponse.setPhone(user.getPhone());
            usersResponse.setRoles(user.getRoles());
            usersResponse.setGender(user.getGender());
            usersResponse.setBirthday(user.getBirthday());
            usersResponse.setCourseResponse(CourseConverter.convertToCourseResponse(user.getCourse()));
            usersResponse.setMajorResponse(MajorConverter.convertToMajorResponse(user.getMajor()));
            usersResponse.setUserEventResponses(UsersEventConverter.convertToUserEventResponse(user.getEvents()));
            usersResponses.add(usersResponse);
        }
        return usersResponses;
    }

    public static LinkedHashSet<UsersResponse> convertToUsersResponseWithoutCourseMajorEvents(LinkedHashSet<Users> users) {
        if (users == null) return new LinkedHashSet<>();
        LinkedHashSet<UsersResponse> usersResponses = new LinkedHashSet<>();
        for (Users user : users) {
            UsersResponse usersResponse = convertToUsersResponseWithoutCourseMajorEvent(user);
            usersResponses.add(usersResponse);
        }
        return usersResponses;
    }

    public static UsersResponse convertToUsersResponseWithoutCourseMajorEvent(Users user) {
        if (user == null) return null;
        UsersResponse usersResponse = new UsersResponse();
        usersResponse.setUsersId(user.getUsersId());
        usersResponse.setEmail(user.getEmail());
        usersResponse.setFullName(user.getFullName());
        usersResponse.setPhone(user.getPhone());
        usersResponse.setRoles(user.getRoles());
        usersResponse.setGender(user.getGender());
        usersResponse.setBirthday(user.getBirthday());
        return usersResponse;
    }
}
