import BannerList from "@/components/banner/BannerList";
import { rank, dataContent } from "utils/data";
import ContentList from "@/components/content/ContentList";
import Talk from "@/components/talks/Talk";
import EventCom from "@/components/events/EventCom";
import Socials from "@/components/socials/Socials";
import LoginLayout from "@/layouts/LoginLayout";
import EventList from "@/components/events/EventList";
import Head from "next/head";
import { DatePicker, Space, Table } from "antd";
import { useEffect, useState } from "react";
import {
  getNotificationsService,
  putNotification,
} from "@/services/notificationsService";
import Cookies from "js-cookie";

export default function Home() {
  const [totalPage, setTotalPage] = useState(1);

  const [data, setData] = useState(null);
  const userId = Cookies.get("userId");

  const handleView = async (id) => {
    const res = await putNotification(userId, id);
    switch (res.data.statusCode) {
      case "200":
        fetchData({
          pageNumber: 0,
          pageSize: 10,
          keyword: "",
          sort: [
            {
              key: "createdAt",
              asc: false,
            },
          ],
        });
        break;

      default:
        break;
    }
  };
  const columns = [
    {
      title: "Notifications",
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
              <p className="text-sm font-medium text-gray-900 ">Admin</p>
              <p className="text-xs sm:text-sm text-gray-500 ">{text}</p>
            </div>
            {!record.read && (
              <span className="absolute right-1 top-1/2 transform -translate-y-1/2 w-2 h-2 rounded-full bg-blue-500" />
            )}
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
        read: item.read,
        notificationId: item.notificationId,
      };
    });
    setData(list);
  };
  const fetchData = async (data) => {
    try {
      const res = await getNotificationsService(userId, data);
      switch (res.data.statusCode) {
        case "200":
          mapDataTable(res.data.data.content);
          setTotalPage(res.data.data.totalElements);
          console.log(res.data.data.content);
          break;
        default:
          break;
      }
    } catch (error) {
      console.log("err" + error);
    }
  };
  useEffect(() => {
   if(userId){
    fetchData({
      pageNumber: 0,
      pageSize: 10,
      keyword: "",
      sort: [
        {
          key: "createdAt",
          asc: false,
        },
      ],
    });
   }
  }, []);
  return (
    <>
      <Head>
        <title> Notification</title>
      </Head>
      <LoginLayout>
        <div className="container mx-auto">
          {" "}
          <div className="mt-[200px]">
            <Table
              columns={columns}
              dataSource={data}
              pagination={{
                defaultCurrent: 1,
                total: totalPage,
                pageSize: 6,
                showSizeChanger: false,
                onChange: (page, pageSize) => {
                  fetchData({
                    pageNumber: page - 1 < 0 ? 0 : page - 1,
                    pageSize,
                  });
                },
              }}
              className="w-full"
            ></Table>
          </div>{" "}
        </div>
      </LoginLayout>
    </>
  );
}
