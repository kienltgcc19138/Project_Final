import React, { useState, useEffect } from "react";
import Link from "next/link";
import { useRouter } from "next/router";
import { withErrorBoundary } from "react-error-boundary";
import { topMenu, navBarWorkshop, navBarUser } from "utils/data";
import SockJsClient from "react-stomp";
import { notification } from "antd";
import logo from "assets/images/logo.png";
import Cookies from "js-cookie";
import { getFromLocalStorage, removeFromLocalStorage } from "@/utils/utils";
import ModalRecommend from "../modal/ModalRecommend";
import { toast } from "react-toastify";
import InfiniteScroll from "react-infinite-scroll-component";
import { async } from "rxjs";
import {
  getNotificationsService,
  putNotification,
} from "@/services/notificationsService";
import useStomp from "@/hooks/Stomp";
const SOCKET_URL = process.env.NEXT_PUBLIC_SOCKET_URL;
const Header = () => {
  const [open, setOpen] = useState("");
  const [modal, setModal] = useState(false);
  const [openNoti, setOpenNoti] = useState(false);
  const [openAcc, setOpenAcc] = useState(false);
  const userId = Cookies.get("userId");
  const [fullName, setFullName] = useState("");
  const [role, setRole] = useState("");
  const [messages, setMessages] = useState([]);
  const handleIncomingMessage = (message) => {
    setMessages([...messages, message]);
  };
  const [stompClient, isConnected] = useStomp(
    SOCKET_URL,
    `/user/${userId}/notification`,
    handleIncomingMessage
  );

  useEffect(() => {
    if (stompClient && isConnected) {
      console.log(
        "Stomp connected and subscribed to /user/" + userId + "/message"
      );
    }
  }, [stompClient, isConnected]);
  const [openMobile, setOpenMobile] = useState(false);
  const router = useRouter();
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
    toast.success(msg.description);

    openNotification();
  };
  const getNoti = async () => {
    const data = {
      pageNumber: 0,
      pageSize: 3,
      sort: [
        {
          key: "createdAt",
          asc: true,
        },
      ],
    };

    try {
      const res = await getNotificationsService(userId, data);
      switch (res.data.statusCode) {
        case "200":
          setMessages(res.data.data.content);
          console.log("messages", res.data.data);
          break;
        default:
          break;
      }
    } catch (error) {
      // console.log(handleLogout);
      handleLogout();
    }
  };
  const hasUnreadNotification = (notifications) => {
    for (let i = 0; i < notifications.length; i++) {
      if (notifications[i].read === false) {
        return true;
      }
    }
    return false;
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
  useEffect(() => {
    setFullName(Cookies.get("name"));
    setRole(Cookies.get("role"));
    if (userId) {
      getNoti();
    }
  }, []);
  const handleView = async (id) => {
    const res = await putNotification(userId, id);
    switch (res.data.statusCode) {
      case "200":
        getNoti();

        // fetchData({
        //   pageNumber: 0,
        //   pageSize: 10,
        //   keyword: "",
        //   sort: [
        //     {
        //       key: "createdAt",
        //       asc: false,
        //     },
        //   ],
        // });
        break;

      default:
        break;
    }
  };
  const handleLogout = () => {
    Cookies.remove("accessToken");
    Cookies.remove("name");
    Cookies.remove("userId");
    Cookies.remove("role");
    setFullName("");
    setRole("");
    setOpenAcc(false);
    router.push("/");
  };
  const customHeaders = {
    "X-ACCESS-TOKEN": "Bearer " + Cookies.get("accessToken"),
  };
  return (
    <header className="fixed top-0 left-0 right-0 z-[9999]">
      {/* <SockJsClient
        url={SOCKET_URL}
        headers={customHeaders}
        topics={[`/user/${userId}/notification`]}
        onConnect={onConnected}
        onDisconnect={console.log("Disconnected!")}
        onMessage={(msg) => onMessageReceived(msg)}
        debug={false}
      /> */}
      <div className="h-[32px]  bgPrimary w-full flex  items-center ">
        <div className=" container mx-auto flex justify-end  text-sm ">
          <div className="text-white mr-12">
            Wellcome <span className="font-medium">{fullName}</span> !
          </div>
        </div>
      </div>
      <div className="w-full bg-white shadow">
        <div className="h-[102px] bg-white container mx-auto flex justify-between items-center">
          <nav className="px-2 bg-white border-gray-200    flex flex-wrap items-center  justify-between w-full">
            <div className="container flex flex-wrap items-center  justify-between mx-auto">
              <Link href="/" className="flex items-center">
                <img
                  src={logo.src}
                  alt=""
                  className="w-[185px] cursor-pointer"
                />
              </Link>
              <div
                onClick={() => setOpenMobile(!openMobile)}
                data-collapse-toggle="navbar-dropdown"
                className="inline-flex items-center p-2 ml-3 text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200   "
                aria-controls="navbar-dropdown"
                aria-expanded="false"
              >
                <span className="sr-only">Open main menu</span>
                <svg
                  className="w-6 h-6"
                  aria-hidden="true"
                  fill="currentColor"
                  viewBox="0 0 20 20"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    fillRule="evenodd"
                    d="M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 15a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z"
                    clipRule="evenodd"
                  ></path>
                </svg>
              </div>
              <div
                className={`${
                  openMobile ? "absolute" : "hidden"
                } w-full md:block md:w-auto flex justify-end bottom-[-50px] `}
                id="navbar-dropdown"
              >
                <ul className="flex flex-col p-2 mt-4 border border-gray-100 rounded-lg bg-gray-50 md:flex-row md:space-x-1 md:mt-0 md:font-medium md:border-0 md:bg-white   ">
                  {role === "ROLE_USER" && (
                    <li>
                      <button
                        onClick={() => setOpenNoti(!openNoti)}
                        className=" text-opacity-90 group self-center w-10 h-10 sm:w-12 sm:h-12 hover:bg-gray-100  rounded-full inline-flex items-center justify-center text-base font-medium hover:text-opacity-100 focus:outline-none focus-visible:ring-2 focus-visible:ring-white focus-visible:ring-opacity-75 relative"
                        type="button"
                        aria-expanded="false"
                        data-headlessui-state=""
                        id="headlessui-popover-button-:R1hsh:"
                      >
                        {hasUnreadNotification(messages) && (
                          <span className="w-2 h-2 bg-blue-500 absolute top-2 right-2 rounded-full"></span>
                        )}
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          fill="none"
                          viewBox="0 0 24 24"
                          strokeWidth="1.5"
                          stroke="currentColor"
                          aria-hidden="true"
                          className="h-6 w-6"
                        >
                          <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            d="M14.857 17.082a23.848 23.848 0 005.454-1.31A8.967 8.967 0 0118 9.75v-.7V9A6 6 0 006 9v.75a8.967 8.967 0 01-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 01-5.714 0m5.714 0a3 3 0 11-5.714 0"
                          ></path>
                        </svg>
                        {openNoti && (
                          <div
                            className="absolute z-[9999] w-screen max-w-xs sm:max-w-sm px-4 top-full -right-28  sm:right-0 sm:px-0 opacity-100 translate-y-0"
                            id="headlessui-popover-panel-:r0:"
                            tabIndex={-1}
                          >
                            <div className="overflow-hidden rounded-2xl shadow-lg ring-1 ring-black ring-opacity-5 text-left">
                              <div
                                className={`relative grid gap-8 bg-white ${
                                  messages.length > 3 ? "h-[400px]" : ""
                                }   p-7`}
                              >
                                <h3 className="text-xl font-semibold">
                                  Notifications
                                </h3>

                                {messages && messages.length != 0 ? (
                                  <>
                                    {messages.map((item, index) => {
                                      return (
                                        <div
                                          onClick={() =>
                                            handleView(item.notificationId)
                                          }
                                          className="flex p-2 pr-8 -m-3 transition duration-150 ease-in-out rounded-lg hover:bg-gray-100  focus:outline-none focus-visible:ring focus-visible:ring-orange-500 focus-visible:ring-opacity-50 relative"
                                        >
                                          <div className="ml-3 sm:ml-4 space-y-1">
                                            <p className="text-sm font-medium text-gray-900 ">
                                              Admin
                                            </p>
                                            <p className="text-xs sm:text-sm text-gray-500 ">
                                              {item.description}
                                            </p>
                                          </div>
                                          {!item.read && (
                                            <span className="absolute right-1 top-1/2 transform -translate-y-1/2 w-2 h-2 rounded-full bg-blue-500" />
                                          )}
                                        </div>
                                      );
                                    })}
                                    <Link
                                      href={"/notifications"}
                                      className="text-blue-600"
                                    >
                                      Read more
                                    </Link>
                                  </>
                                ) : (
                                  <div> Notification emty</div>
                                )}
                              </div>
                            </div>
                          </div>
                        )}
                      </button>
                    </li>
                  )}

                  <li className="flex items-center">
                    <div
                      onClick={() => {
                        if (!role) {
                          router.push("/login");
                        } else {
                          setOpenAcc(!openAcc);
                        }
                      }}
                      id="dropdownDefaultButton"
                      data-dropdown-toggle="dropdown"
                      className="flex relative items-center text-[#7a7a7a] px-3 font-medium mr-4 hover:textLink cursor-pointer"
                    >
                      {role
                        ? role == "ROLE_ADMIN"
                          ? "Admin"
                          : "Student"
                        : "Login"}
                      {role && (
                        <svg
                          className="w-5 h-5 ml-1"
                          aria-hidden="true"
                          fill="currentColor"
                          viewBox="0 0 20 20"
                          xmlns="http://www.w3.org/2000/svg"
                        >
                          <path
                            fillRule="evenodd"
                            d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
                            clipRule="evenodd"
                          ></path>
                        </svg>
                      )}
                    </div>
                    {navBarUser && (
                      <>
                        <div
                          id="dropdown"
                          className={`z-[9999] ${
                            openAcc ? "absolute top-28 " : "hidden"
                          } font-normal bg-white divide-y divide-gray-100 rounded-lg shadow w-44  `}
                        >
                          <div
                            className="fixed inset-0 "
                            // aria-hidden="true"
                            onClick={() => setOpenAcc(false)}
                          >
                            <div className="absolute right-[5%] top-28 font-normal bg-white divide-y divide-gray-100 rounded-lg shadow w-44  ">
                              <ul
                                className="py-2 text-sm text-gray-700 "
                                aria-labelledby="dropdownLargeButton"
                              >
                                {navBarUser.map((item, index) => {
                                  return (
                                    item.role == role && (
                                      <li key={index}>
                                        <Link
                                          href={item.href}
                                          onClick={() => setOpenAcc(false)}
                                          className="block px-4 py-2 hover:bg-gray-100 "
                                        >
                                          {item.title}
                                        </Link>
                                      </li>
                                    )
                                  );
                                })}
                              </ul>
                              {/* {role == "ROLE_USER" && (
                                <div className="py-1">
                                  <a
                                    href="#"
                                    onClick={() => {
                                      setModal(true);
                                      setOpenAcc(false);
                                    }}
                                    className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 "
                                  >
                                    Recommend
                                  </a>
                                </div>
                              )} */}

                              <div className="py-1">
                                <a
                                  href="#"
                                  onClick={() => handleLogout()}
                                  className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 "
                                >
                                  Sign out
                                </a>
                              </div>
                            </div>
                          </div>
                        </div>
                      </>
                    )}
                  </li>
                </ul>
              </div>
            </div>
          </nav>
        </div>
      </div>
      <ModalRecommend show={modal} handleClose={() => setModal(false)} />
    </header>
  );
};

export default Header;
