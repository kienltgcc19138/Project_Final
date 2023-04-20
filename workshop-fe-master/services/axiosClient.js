import axios from "axios";
import Cookies from "js-cookie";

import { URL } from "../utils/env";

const axiosClient = () => {
  const token = Cookies.get("accessToken");
  const axiosOptions = axios.create({
    baseURL: URL,
    headers: {
      "content-type": "application/json",
      "X-ACCESS-TOKEN":"Bearer "+ token,
    },
  });
  return axiosOptions;
};
export default axiosClient;
