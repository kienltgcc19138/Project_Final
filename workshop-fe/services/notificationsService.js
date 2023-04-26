import { URL } from "../utils/env";
import axiosClient from "./axiosClient";

const getNotificationsService = async (usersId, data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/notification/${usersId}`,
    data: data,
  });
};
const putNotification = async (usersId, id) => {
    return (await axiosClient())({
      method: "PUT",
      url: URL + `/notification/${usersId}/${id}`,
    });
  };

export { getNotificationsService,putNotification };
