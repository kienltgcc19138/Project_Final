import { URL } from "../utils/env";
import axiosClient from "./axiosClient";

const uploadFile = async (data) => {
  let params = {
    type: "image",
  };
  return (await axiosClient())({
    method: "POST",
    url: URL + `/file-data`,
    params,
    data:data
  });
};

export { uploadFile };
