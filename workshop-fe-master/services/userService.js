import { URL } from "../utils/env";
import axiosClient from "./axiosClient";

///user
const regEvent = async (eventId, userId) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/user-event/${eventId}/register`,
    data: {
      usersId: userId,
    },
  });
};
const fetchUserHistoryService = async (data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/user-event/search`,
    data: data,
  });
};
const recommendService = async (data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/recommend`,
    data: data,
  });
};
const getRecommendService = async (data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/recommend`,
    data: data,
  });
};



const updateScoreEventService = async (data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/user-event/add-score`,
    data: data,
  });
};
const getScoreUserService = async (id) => {
  return (await axiosClient())({
    method: "GET",
    url: URL + `/user-event/${id}/score-by-usersId`,
  });
};

export {
  regEvent,
  recommendService,
  fetchUserHistoryService,
  updateScoreEventService,
  getScoreUserService,getRecommendService
};
