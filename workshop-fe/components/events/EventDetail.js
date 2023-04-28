import React, { useState, useEffect } from "react";
import Link from "next/link";
import { withErrorBoundary } from "react-error-boundary";
const { TextArea } = Input;
import { Input, Table } from "antd";
import { Tab } from "@headlessui/react";

import {
  AiOutlineCalendar,
  AiOutlineEnvironment,
  AiOutlineTeam,
  AiOutlineDoubleRight,
} from "react-icons/ai";
import { useRouter } from "next/router";
import {
  feedbackService,
  fetchTickedService,
  recommendService,
} from "@/services/tickedService";
import { toast } from "react-toastify";
import Cookies from "js-cookie";
import ModalRegister from "../modal/ModalRegister";
import {
  fetchUserHistoryService,
  getRecommendService,
} from "@/services/userService";

const EventDetail = ({ detail, userRole }) => {
  const [isRegister, setIsRegister] = useState(false);

  const [data, setData] = useState([]);
  const [totalPage, setTotalPage] = useState(1);
  const [page, setPage] = useState(0);

  const [name, setName] = useState("");
  const [activeTab, setActiveTab] = useState(0);
  const [modal, setModal] = useState(false);
  const [nameErr, setNameErr] = useState("");
  const [description, setDescription] = useState("");
  const [commonets, setComments] = useState([]);
  const router = useRouter();
  const time = new Date().getTime();
  const [role, setRole] = useState(null);
  const dateObj = new Date(detail?.timeStart).getTime();
  // useEffect(() => {
  //   setRole(Cookies.get("role"));
  //   console.log(detail);
  //   if (detail?.eventId) {

  //   }
  // }, []);

  const handleFormatDay = (time) => {
    const inputDate = new Date(time);
    const formattedDate = inputDate.toLocaleString("en-US", {
      hour: "numeric",
      minute: "numeric",
      day: "numeric",
      month: "long",
      year: "numeric",
    });
    return formattedDate;
  };
  const currentDate = new Date(detail?.timeStart);
  const day = currentDate.getDate();
  const month = currentDate.toLocaleString("en-US", { month: "long" });

  const onCheck = async () => {
    const data = {
      pageNumber: 0,
      pageSize: 10,
      usersId: Cookies.get("userId"),
      eventId: detail?.eventId,
    };
    try {
      const res = await fetchUserHistoryService(data);
      console.log("check");
      switch (res.data.statusCode) {
        case "200":
          console.log("sadasdasd", res.data.data.content);
          if (res.data.data.content?.length == 0) {
            setIsRegister(false);
          } else {
            setIsRegister(true);
          }
          refresh();
          break;
        default:
          break;
      }
    } catch (error) {
      console.log("err" + error);
    }
  };
  const columns = [
    {
      title: "Feedbacks",
      dataIndex: "notifications",
      key: "notifications",
      className: "font-bold",
      render: (text, record) => {
        return (
          <div
            onClick={() => handleView(record.notificationId)}
            className="flex p-2 pr-8  transition duration-150 ease-in-out rounded-lg hover:bg-gray-100  focus:outline-none focus-visible:ring focus-visible:ring-orange-500 focus-visible:ring-opacity-50 relative"
          >
            <div className="ml-3 sm:ml-4 space-y-1">
              <p className="text-lg font-medium text-black ">{record.name}</p>
              <p className="text-sm text-gray-500 ">{text}</p>
            </div>
            {/* {!record.read && (
              <span className="absolute right-1 top-1/2 transform -translate-y-1/2 w-2 h-2 rounded-full bg-blue-500" />
            )} */}
          </div>
          //   <div
          //     className="cursor-pointer py-8"
          //     onClick={() => {
          //       router.push("workshops/" + record.id);
          //     }}
          //   >
          //     {text}
          //   </div>
        );
      },
    },
  ];
  const mapDataTable = (data) => {
    const list = data.map((item, index) => {
      return {
        key: index + 1,
        notifications: item.description,
        name: item.name,
        notificationId: item.notificationId,
      };
    });
    setData(list);
  };
  // const fetchData = async (data) => {
  //   try {
  //     const res = await getNotificationsService(userId, data);
  //     switch (res.data.statusCode) {
  //       case "200":
  //         mapDataTable(res.data.data.content);
  //         setTotalPage(res.data.data.totalElements);
  //         console.log(res.data.data.content);
  //         break;
  //       default:
  //         break;
  //     }
  //   } catch (error) {
  //     console.log("err" + error);
  //   }
  // };
  const fetchFeedback = async () => {
    const data = {
      pageNumber: page,
      pageSize: 10,
      sort: [
        {
          key: "createdAt",
          asc: false,
        },
      ],
      objectType: "FEED_BACK",
      eventId: detail?.eventId,
    };
    const res = await fetchTickedService(data);
    switch (res.data.statusCode) {
      case "200":
        console.log("aaaa");
        mapDataTable(res.data.data.content);
        setTotalPage(res.data.data.totalElements);
        break;
      default:
        break;
    }
  };

  const checkRoll = () => {
    if (role === "ROLE_ADMIN") {
      console.log("a");
      return false;
    } else {
      return true;
    }
  };
  const handleSubmit = async () => {
    let data = {
      name: name,
      description: description,
      eventId: detail?.eventId,
    };
    try {
      const res =
        activeTab == 1
          ? await recommendService(data)
          : await feedbackService(data);
      switch (res.data.statusCode) {
        case "200":
          fetchFeedback();
          activeTab == 1
            ? toast.success("recommend success")
            : toast.success("feedback success");
          setDescription("");
          setName("");
          break;

        default:
          break;
      }
    } catch (error) {
      console.log(error.response.data.message)
      toast.error(error.response.data.message)
    }
  };
  const validateName = (name) => {
    if (!name || name.trim() === "") {
      return "Name is required";
    }
    return "";
  };


  useEffect(() => {
    fetchFeedback();
  }, [activeTab]);
  useEffect(() => {
    if (detail?.eventId) {
      fetchFeedback();
      onCheck();
      console.log("");
    }
  }, []);
  useEffect(() => {
    if (detail?.eventId) {
      fetchFeedback();
    }
  }, [page]);

  return (
    <div className="container mx-auto my-40 px-2">
      <div className="flex items-center gap-4 mb-6">
        <div className="block rounded-t overflow-hidden bg-white text-center w-16 ">
          <div className="bg-red-600 text-white text-sm ">{month}</div>
          <div className=" border rounded-b">
            <span className="text-2xl font-bold">{day}</span>
          </div>
        </div>
        <div>
          <div
            className={`max-w-max px-2  ${
              detail?.status == "UPCOMING" ? "bg-[green]" : ""
            } ${detail?.status === "HAPPENED" ? "bg-[red]" : ""}  ${
              detail?.status === "HAPPENING" ? "bg-[orange]" : ""
            }    text-white rounded-md`}
          >
            {detail?.status}
          </div>
          <div className="font-medium textPrimary2 text-3xl">
            {detail?.name}
          </div>
        </div>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-7 md:gap-4 ">
        <div className="col-span-5 ">
          <img
            className="rounded-xl"
            src={detail?.fileDataResponses[0]?.link}
          />
          <div className="mt-12">
            <Tab.Group>
              <Tab.List className="flex border-b border-blue-200 gap-2">
                <Tab
                  className={`px-4 py-1.5 font-medium textPrimary2 ${
                    activeTab === 0
                      ? "border-b-2 border-orange-600 textLink"
                      : ""
                  }`}
                  selected={activeTab === 0}
                  onClick={() => setActiveTab(0)}
                >
                  Description
                </Tab>

                {detail?.status === "HAPPENED" && (
                  <Tab
                    className={`px-4 py-1.5 font-medium textPrimary2 ${
                      activeTab === 2
                        ? "border-b-2 border-orange-600 textLink"
                        : ""
                    }`}
                    selected={activeTab === 2}
                    onClick={() => setActiveTab(2)}
                  >
                    Feedback
                  </Tab>
                )}
              </Tab.List>
              <Tab.Panels>
                <Tab.Panel className={`py-12`}>
                  {detail?.description !== "" ? (
                    <div
                      dangerouslySetInnerHTML={{
                        __html: detail?.description,
                      }}
                    ></div>
                  ) : null}
                </Tab.Panel>

                <Tab.Panel>
                  {Cookies.get("role") === "ROLE_USER" && (
                    <>
                      {time > dateObj ? (
                        <>
                          {" "}
                          <div className="grid grid-cols-3 gap-6">
                            <div className="col-span-3 shadow rounded-lg bg-white p-6 grid grid-cols-2 gap-8">
                              <div>
                                <div className="text-xs font-semibold mb-2">
                                  Title<span className="text-red-500">*</span>
                                  <span className="text-red-500">
                                    {nameErr}
                                  </span>
                                </div>
                                <Input
                                  placeholder=""
                                  required
                                  value={name}
                                  onChange={(e) => setName(e.target.value)}
                                />
                              </div>
                              <div>
                                <div className="text-xs font-semibold mb-2">
                                  Description{" "}<span className="text-red-500">*</span>
                                </div>
                                <TextArea
                                  value={description}
                                  onChange={(e) =>
                                    setDescription(e.target.value)
                                  }
                                  rows={4}
                                />
                              </div>
                            </div>
                          </div>
                          <div className="flex justify-end">
                            <div
                              onClick={handleSubmit}
                              className="bgPrimary w-24 m-4 text-center  px-6 py-2 text-white rounded-lg cursor-pointer"
                            >
                              Send
                            </div>
                          </div>
                        </>
                      ) : (
                        <div className="my-12 mx-auto border border-red-600 text-center p-4">
                          This event has not yet taken place.
                        </div>
                      )}
                    </>
                  )}
                  {Cookies.get("role") !== "" && (
                    <Table
                      columns={columns}
                      dataSource={data}
                      pagination={{
                        defaultCurrent: 1,
                        total: totalPage,
                        pageSize: 10,
                        showSizeChanger: false,
                        onChange: (page, pageSize) => {
                          setPage(page - 1 < 0 ? 0 : page - 1);
                        },
                      }}
                      className="w-full"
                    ></Table>
                  )}

                  {
                    // <article className="p-6 mb-6 text-base bg-white border-t border-gray-200 dark:border-gray-700 dark:bg-gray-900">
                    //   <footer className="flex justify-between items-center mb-2">
                    //     <div className="flex items-center">
                    //       <p className="inline-flex items-center mr-3 text-sm text-gray-900 dark:text-white">
                    //         <img
                    //           className="mr-2 w-6 h-6 rounded-full"
                    //           src="https://flowbite.com/docs/images/people/profile-picture-3.jpg"
                    //           alt="Bonnie Green"
                    //         ></img>
                    //         Bonnie Green
                    //       </p>
                    //       <p className="text-sm text-gray-600 dark:text-gray-400">
                    //         <time
                    //           pubdate
                    //           datetime="2022-03-12"
                    //           title="March 12th, 2022"
                    //         >
                    //           Mar. 12, 2022
                    //         </time>
                    //       </p>
                    //     </div>
                    //   </footer>
                    //   <p className="text-gray-500 dark:text-gray-400">
                    //     The article covers the essentials, challenges, myths and
                    //     stages the UX designer should consider while creating
                    //     the design strategy.
                    //   </p>
                    //   <div className="flex items-center mt-4 space-x-4"></div>
                    // </article>
                  }
                </Tab.Panel>
              </Tab.Panels>
            </Tab.Group>
          </div>
        </div>
        <div className="md:col-span-2 border h-min  p-4 w-full rounded-xl flex flex-col md:gap-4">
          <div className="grid grid-cols-6">
            <div className="w-12">
              <AiOutlineCalendar className="text-gray-500 " size={"1.5em"} />
            </div>
            <div className="col-span-5">
              <div className="font-bold textPrimary2"> Date & time</div>
              <div className="textPrimary text-sm textPrimary2">
                Start: {handleFormatDay(detail?.timeStart)}
              </div>
              <div className="textPrimary text-sm textPrimary2">
                End: {handleFormatDay(detail?.timeEnd)}
              </div>
            </div>
          </div>
          <div className="grid grid-cols-6">
            <div className="w-12">
              <AiOutlineEnvironment className="text-gray-500" size={"1.5em"} />
            </div>
            <div className="col-span-5">
              <div className="font-bold textPrimary2"> Location</div>
              <div className="textPrimary text-sm"> {detail?.location}</div>
            </div>
          </div>
          {Cookies.get("role") === "ROLE_USER" && detail?.status == 'UPCOMING' && (
            <div>
              {detail?.eventId && isRegister ? (
                <div className="flex justify-center">
                  <button
                    disabled
                    className={`${
                      role === "ROLE_ADMIN" ? "hidden" : "flex"
                    } justify-center disabled:bg-gray-500 items-center bgPrimary text-white border px-4 py-2 rounded `}
                  >
                    Registered
                  </button>
                </div>
              ) : (
                <div>
                  <div className="flex justify-center">
                    <button
                      onClick={() => setModal(true)}
                      className="flex justify-center items-center bgPrimary text-white border px-4 py-2 rounded "
                    >
                      Register
                    </button>
                  </div>
                </div>
              )}
            </div>
          )}
        </div>
      </div>
      <ModalRegister
        show={modal}
        eventId={detail?.eventId}
        handleClose={() => {
          setModal(false);
          onCheck();
        }}
      />
    </div>
  );
};

function ErrorComponent({ error }) {
  return (
    <div className="p-4 text-red-500 border border-red-500">
      <span>{error.message}</span>
    </div>
  );
}

export default withErrorBoundary(EventDetail, {
  FallbackComponent: ErrorComponent,
});
