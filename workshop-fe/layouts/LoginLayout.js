// components/layout.js

import FooterAuth from "@/components/common/FooterAuth";
import Head from "next/head";
import Header from "../components/common/HeaderAuth";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { Button, ConfigProvider } from "antd";
import ChatUser from "@/components/chat/ChatUser";
import { useEffect, useState } from "react";
import Cookies from "js-cookie";
import Noti from "@/components/chat/Noti";
export default function LoginLayout({ children }) {
  const [role, setRole] = useState(null);
  useEffect(() => {
    let role1 = Cookies.get("role");
    setRole(role1);
  }, []);

  return (
    <>
      <ConfigProvider
        theme={{
          token: {  colorPrimary: '#2D3791', colorLink: '#2D3791', colorLinkHover: '#2D3791'},

        }}
      >
        <ToastContainer />
        {/* <Widget handleNewUserMessage={handleNewUserMessage} /> */}
        <Header />
        <div className="mt-20 w-full"></div>
        <main className="relative">
          {children}
          {role === "ROLE_USER" && <ChatUser className="" />}
        </main>
        <FooterAuth />
      </ConfigProvider>
    </>
  );
}
