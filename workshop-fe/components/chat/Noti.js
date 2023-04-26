"use client";

import Cookies from "js-cookie";
import {
  notification,
} from "antd";
import { useRouter } from "next/router";
import { useState, useEffect, useRef } from "react";
import SockJsClient from "react-stomp";
import {
  searchAllChatUser,
  userSendMessageInEvent,
} from "@/services/chatService";
import ClientComponent from "@/layouts/Client";

const SOCKET_URL =  process.env.NEXT_PUBLIC_SOCKET_URL;

export default function Noti() {
  const [isOpen, setIsOpen] = useState(false);
  const [openChatBox, setOpenChatBox] = useState(false);
  const [messages, setMessages] = useState([]);
  const router = useRouter();
  const userId = Cookies.get("userId");
  const role = Cookies.get("role");
  const [message, setMessage] = useState([]);

  const messagesEndRef = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  const getChat = async () => {
    const res = await searchAllChatUser(userId, {
      pageNumber: 0,
      pageSize: 20,
      sort: [
        {
          key: "createdAt",
          asc: false,
        },
      ],
    });
    res.data.data.content.map((item) => {
      messages.unshift(item);
    });
  };
  useEffect(() => {
    scrollToBottom();
    console.log(messages);
  }, [messages]);
  useEffect(() => {
    getChat();
  }, []);

  const chatboxToogleHandler = () => {
    setIsOpen(!isOpen);
  };

  let onConnected = () => {
    console.log(userId);
    console.log(role);
    console.log("Connected!!");
  };
  const openNotification = (msg) => {
    notification.open({
      message: "Notification Title",
      description: msg,
      onClick: () => {
        console.log("Notification Clicked!");
      },
    });
  };

  let onMessageReceived = (msg) => {
    console.log("Message Received!! " + msg, msg);

    setMessages([...messages, msg]);
    openNotification();
  };

  const sendMessage = async (content) => {
    try {
      const data = {
        content: content,
      };

      const res = await userSendMessageInEvent(data);
      setMessage("");
    } catch (error) {
      console.log(error);
    }
  };
  const customHeaders = {
    "X-ACCESS-TOKEN": "Bearer " + Cookies.get("accessToken"),
  };
  return (
    <>
      <div
        className={`${
          role === "ROLE_ADMIN" ? "hidden" : "fixed"
        }  bottom-4 right-0 z-[99999999]  flex items-center justify-center p-12`}
      >
        <ClientComponent>
          {/* <SockJsClient
            url={SOCKET_URL}
            headers={customHeaders}
            topics={[`/user/${userId}/notification`]}
            onConnect={onConnected}
            onDisconnect={console.log("Disconnected!")}
            onMessage={(msg) => onMessageReceived(msg)}
            debug={false}
          /> */}
        </ClientComponent>
        <div className="w-full">
          {isOpen && (
            <div className="formbold-form-wrapper mx-auto  w-[500px] max-w-[550px] rounded-lg border border-[#e0e0e0] bg-white">
              <div className="flex items-center justify-between rounded-t-lg bg-[#6A64F1] py-4 px-9">
                <h3 className="text-xl font-bold text-white">
                  Let's chat? - Online
                </h3>
                <button onClick={chatboxToogleHandler} className="text-white">
                  <svg
                    width="17"
                    height="17"
                    viewBox="0 0 17 17"
                    className="fill-current"
                  >
                    <path
                      fillRule="evenodd"
                      clipRule="evenodd"
                      d="M0.474874 0.474874C1.10804 -0.158291 2.1346 -0.158291 2.76777 0.474874L16.5251 14.2322C17.1583 14.8654 17.1583 15.892 16.5251 16.5251C15.892 17.1583 14.8654 17.1583 14.2322 16.5251L0.474874 2.76777C-0.158291 2.1346 -0.158291 1.10804 0.474874 0.474874Z"
                    />
                    <path
                      fillRule="evenodd"
                      clipRule="evenodd"
                      d="M0.474874 16.5251C-0.158291 15.892 -0.158291 14.8654 0.474874 14.2322L14.2322 0.474874C14.8654 -0.158292 15.892 -0.158291 16.5251 0.474874C17.1583 1.10804 17.1583 2.1346 16.5251 2.76777L2.76777 16.5251C2.1346 17.1583 1.10804 17.1583 0.474874 16.5251Z"
                    />
                  </svg>
                </button>
              </div>
              <div className="flex flex-col flex-auto flex-shrink-0  bg-gray-100 h-full py-4 pl-4">
                <div className="flex flex-col ">
                  <div className=" grid-cols-12 gap-y-2 h-[550px] overflow-y-scroll pr-4 ">
                    {messages?.map((message, index) => {
                      return (
                        <div
                          className={` ${
                            message.createdBy === userId
                              ? "col-start-6 col-end-13 "
                              : "col-start-1 col-end-8"
                          } p-3 rounded-lg`}
                        >
                          <div
                            className={`flex flex-row items-center ${
                              message.createdBy === userId
                                ? "justify-start flex-row-reverse"
                                : ""
                            }`}
                          >
                            <div className="flex items-center justify-center h-10 w-10 rounded-full bg-indigo-500 flex-shrink-0">
                              {message.createdBy === userId ? "ST" : "AD"}
                            </div>
                            <div
                              className={`relative ml-3 text-sm ${
                                message.createdBy === userId
                                  ? "bg-indigo-500  mr-3"
                                  : "bg-white"
                              }  py-2 px-4 shadow rounded-xl`}
                            >
                              <div> {message?.description}</div>
                            </div>
                          </div>
                        </div>
                      );
                    })}
                    <div ref={messagesEndRef} />
                  </div>
                </div>
              </div>
              <div className="flex px-4 flex-row items-center h-16 rounded-xl bg-white w-full ">
                <div>
                  <button className="flex items-center justify-center text-gray-400 hover:text-gray-600">
                    <svg
                      className="w-5 h-5"
                      fill="none"
                      stroke="currentColor"
                      viewBox="0 0 24 24"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth="2"
                        d="M15.172 7l-6.586 6.586a2 2 0 102.828 2.828l6.414-6.586a4 4 0 00-5.656-5.656l-6.415 6.585a6 6 0 108.486 8.486L20.5 13"
                      ></path>
                    </svg>
                  </button>
                </div>
                <div className="flex-grow ml-4">
                  <div className="relative w-full">
                    <input
                      onChange={(e) => setMessage(e.target.value)}
                      value={message}
                      onKeyDown={(e) => {
                        if (e.key === "Enter") {
                          sendMessage(e.target.value);
                        }
                      }}
                      type="text"
                      className="flex w-full border rounded-xl focus:outline-none focus:border-indigo-300 pl-4 h-10"
                    />
                    <button className="absolute flex items-center justify-center h-full w-12 right-0 top-0 text-gray-400 hover:text-gray-600">
                      <svg
                        className="w-6 h-6"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                        xmlns="http://www.w3.org/2000/svg"
                      >
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth="2"
                          d="M14.828 14.828a4 4 0 01-5.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                        ></path>
                      </svg>
                    </button>
                  </div>
                </div>
                <div className="ml-4">
                  <button className="flex items-center justify-center bg-indigo-500 hover:bg-indigo-600 rounded-xl text-white px-4 py-1 flex-shrink-0">
                    <span>Send</span>
                    <span className="ml-2">
                      <svg
                        className="w-4 h-4 transform rotate-45 -mt-px"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                        xmlns="http://www.w3.org/2000/svg"
                      >
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth="2"
                          d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8"
                        ></path>
                      </svg>
                    </span>
                  </button>
                </div>
              </div>
            </div>
          )}
          <div className="mx-auto mt-4 flex max-w-[550px] items-center justify-end space-x-5">
            <button
              className="flex h-[70px] w-[70px] items-center justify-center rounded-full bg-[#6A64F1] text-white"
              onClick={chatboxToogleHandler}
            >
              <span className="cross-icon hidden">
                <svg
                  width="17"
                  height="17"
                  viewBox="0 0 17 17"
                  fill="none"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    fillRule="evenodd"
                    clipRule="evenodd"
                    d="M0.474874 0.474874C1.10804 -0.158291 2.1346 -0.158291 2.76777 0.474874L16.5251 14.2322C17.1583 14.8654 17.1583 15.892 16.5251 16.5251C15.892 17.1583 14.8654 17.1583 14.2322 16.5251L0.474874 2.76777C-0.158291 2.1346 -0.158291 1.10804 0.474874 0.474874Z"
                    fill="white"
                  />
                  <path
                    fillRule="evenodd"
                    clipRule="evenodd"
                    d="M0.474874 16.5251C-0.158291 15.892 -0.158291 14.8654 0.474874 14.2322L14.2322 0.474874C14.8654 -0.158292 15.892 -0.158291 16.5251 0.474874C17.1583 1.10804 17.1583 2.1346 16.5251 2.76777L2.76777 16.5251C2.1346 17.1583 1.10804 17.1583 0.474874 16.5251Z"
                    fill="white"
                  />
                </svg>
              </span>
              <span className="chat-icon">
                <svg
                  width="28"
                  height="28"
                  viewBox="0 0 28 28"
                  fill="none"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    d="M19.8333 14.0002V3.50016C19.8333 3.19074 19.7103 2.894 19.4915 2.6752C19.2728 2.45641 18.976 2.3335 18.6666 2.3335H3.49992C3.1905 2.3335 2.89375 2.45641 2.67496 2.6752C2.45617 2.894 2.33325 3.19074 2.33325 3.50016V19.8335L6.99992 15.1668H18.6666C18.976 15.1668 19.2728 15.0439 19.4915 14.8251C19.7103 14.6063 19.8333 14.3096 19.8333 14.0002ZM24.4999 7.00016H22.1666V17.5002H6.99992V19.8335C6.99992 20.1429 7.12284 20.4397 7.34163 20.6585C7.56042 20.8772 7.85717 21.0002 8.16659 21.0002H20.9999L25.6666 25.6668V8.16683C25.6666 7.85741 25.5437 7.56066 25.3249 7.34187C25.1061 7.12308 24.8093 7.00016 24.4999 7.00016Z"
                    fill="white"
                  />
                </svg>
              </span>
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
