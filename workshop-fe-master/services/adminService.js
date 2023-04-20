import { URL } from "../utils/env";
import axiosClient from "./axiosClient";

///user
const fetchUserService = async (data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/users/search-user`,
    data: data,
  });
};
const addUserServie = async (data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/users`,
    data: data,
  });
};
const updateUserServie = async (data) => {
  return (await axiosClient())({
    method: "PUT",
    url: URL + `/users`,
    data: data,
  });
};
const updatePassword = async (id,data) => {
  return (await axiosClient())({
    method: "PUT",
    url: URL + `/users/${id}/update-password`,
    data: data,
  });
};
const getDetailUserService = async (id) => {
  return (await axiosClient())({
    method: "GET",
    url: URL + `/users/${id}`,
  });
};
const removeUserService = async (id) => {
  return (await axiosClient())({
    method: "DELETE",
    url: URL + `/users/${id}`,
  });
};

const adminUpdatePasswordUserService = async (data) => {
  return (await axiosClient())({
    method: "PUT",
    url: URL + `/users/admin/update-password-user`,
    data: data,
  });
};


////event
const fetchWorkshopService = async (data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/event/search-event`,
    data: data,
  });
};
const setStatusService = async (id,data) => {
  return (await axiosClient())({
    method: "PUT",
    url: URL + `/event/${id}/status`,
    data:data
  });
};

const addWorkshopServie = async (data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/event`,
    data: data,
  });
};
const editWorkshopServie = async (data, id) => {
  return (await axiosClient())({
    method: "PUT",
    url: URL + `/event/${id}`,
    data: data,
  });
};
const deleteWorkshopService = async (id) => {
  return (await axiosClient())({
    method: "DELETE",
    url: URL + `/event/${id}`,
  });
};
const getDetailWorkshopService = async (id) => {
  return (await axiosClient())({
    method: "GET",
    url: URL + `/event/${id}`,
  });
};
const exportEvent = async (id) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/user-event/${id}/export-event`,
    responseType: "blob",

  });
};

////major
const fetchMajorService = async (data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/major/search-major`,
    data: data,
  });
};
const addMajorServie = async (data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/major`,
    data: data,
  });
};
const editMajorServie = async (data, id) => {
  return (await axiosClient())({
    method: "PUT",
    url: URL + `/major/${id}`,
    data: data,
  });
};
const getDetailMajorService = async (id) => {
  return (await axiosClient())({
    method: "GET",
    url: URL + `/major/${id}`,
  });
};
const deleteMajorService = async (id) => {
  return (await axiosClient())({
    method: "DELETE",
    url: URL + `/major/${id}`,
  });
};

////course
const fetchCourseService = async (data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/course/search-course`,
    data: data,
  });
};
const editCourseServie = async (data, id) => {
  return (await axiosClient())({
    method: "PUT",
    url: URL + `/course/${id}`,
    data: data,
  });
};
const addCourseService = async (data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/course`,
    data: data,
  });
};
const getDetailCourseService = async (id) => {
  return (await axiosClient())({
    method: "GET",
    url: URL + `/course/${id}`,
  });
};
const deleteCourseService = async (id) => {
  return (await axiosClient())({
    method: "DELETE",
    url: URL + `/course/${id}`,
  });
};

///recommend
const fetchRecommendService = async (data) => {
  return (await axiosClient())({
    method: "POST",
    url: URL + `/recommend/search`,
    data: data,
  });
};
export {
  fetchUserService,
  removeUserService,
  addUserServie,
  getDetailUserService,
  updatePassword,
  updateUserServie,
  fetchWorkshopService,
  setStatusService,
  getDetailWorkshopService,
  exportEvent,
  addWorkshopServie,
  editWorkshopServie,
  deleteWorkshopService,
  fetchMajorService,
  addMajorServie,
  editMajorServie,
  deleteMajorService,
  getDetailMajorService,
  editCourseServie,
  fetchCourseService,
  getDetailCourseService,
  addCourseService,
  deleteCourseService,
  fetchRecommendService,
  adminUpdatePasswordUserService
};
