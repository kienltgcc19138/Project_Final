import { useEffect, useState } from "react";
import { useRouter } from "next/router";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { sendCodeService } from "services/authService";
import Cookies from "js-cookie";
import { saveToLocalStorage } from "@/utils/utils";

export default function VerifyLogin() {
  const router = useRouter();
  const { code, token } = router.query;
  const handleVerifyEmail = async (code, token) => {
    const data = {
      code: code,
      token: token,
    };
    try {
      const res = await sendCodeService(data);
      switch (res.data.statusCode) {
        case "200":
          Cookies.set('name',res.data.data.users.fullName )
          Cookies.set('userId',res.data.data.users.usersId )
          Cookies.set('role',res.data.data.users.roles )
          Cookies.set("accessToken", res.data.data.accessToken);
          router.push("/");
          break;
        default:
          break;
      }
    } catch (error) {
      toast(error.response.data.errorCode)
      router.push("/login");
      toast(error)
    }
  };

  useEffect(() => {
    if (code) {
      handleVerifyEmail(code, token);
    }
  }, [code]);
  return <></>;
}
