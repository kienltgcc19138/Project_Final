import { URL } from "../utils/env";
import axiosClient from "./axiosClient";

///user
const recommendService = async (data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/ticket/recommend`,
    data:data
  });
};
const questionService = async (data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/ticket/question`,
    data:data
  });
};

const feedbackService = async (data) => {
    return (await axiosClient())({
      method: "POST",
      url: URL + `/ticket/feed-back`,
      data:data
    });
  };
const fetchTickedService = async (data) => {
    return (await axiosClient())({
      method: "POST",
      url: URL + `/ticket/search`,
      data: data,
    });
  };

export { recommendService,fetchTickedService,feedbackService,questionService };
