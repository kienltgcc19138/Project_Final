import axios from "axios";
import { URL } from "../utils/env";


const loginService = (data) => {
  return axios({
    method: "POST",
    url: URL + `/auth/login`,
    data: data,
  });
};

const sendCodeService = (data) => {
  return axios({
    method: "POST",
    url: URL + `/auth/verify-email-login`,
    data: data,
  });
};

export {
  loginService,
  sendCodeService,
};
