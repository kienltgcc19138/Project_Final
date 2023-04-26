import { URL } from "../utils/env";
import axiosClient from "./axiosClient";

const searchAllChatUser = async (usersId, data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/chat/search/${usersId}`,
    data: data,
  });
};

const userSendMessageInEvent = async (data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/chat/user-send`,
    data: data,
  });
};

const adminSendMessageInEvent = async (usersId, data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/chat/admin-send/${usersId}`,
    data: data,
  });
};

const searchAdminChat = async (data) => {
  
  return (await axiosClient())({
    method: "POST",
    url: URL + `/chat/search-user-chat-with-admin`,
    data: data,
  });
};
export {
  searchAllChatUser,
  userSendMessageInEvent,
  adminSendMessageInEvent,
  searchAdminChat,
};
