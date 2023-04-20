import ChangePass from "@/components/account/ChangePass";
import Profile from "@/components/account/Profile";
import LoginLayout from "@/layouts/LoginLayout";
import {
  fetchUserHistoryService,
  getScoreUserService,
} from "@/services/userService";
import { EditOutlined, DeleteOutlined } from "@ant-design/icons";

import Cookies from "js-cookie";
import { useRouter } from "next/router";
import { Fragment, useEffect, useRef, useState } from "react";
// import { ChevronDownIcon } from '@heroicons/react/20/solid'
import { Space, Table, Tag } from "antd";
import { formatDateTime } from "@/utils/dateUtils";

export default function History() {
  const [data, setData] = useState([]);
  const [score, setScore] = useState(0);
  const router = useRouter();
  if (!Cookies.get("accessToken") && process.browser) {
    router.push("/login");
  }
  const columns = [
    // {
    //   title: "ID",
    //   dataIndex: "id",
    //   key: "id",
    //   className: "text-left",
    // },
    {
      title: "Name",
      dataIndex: "name",
      key: "name",
      className: "font-bold",
      render: (text, record) => {
        return (
          <div
            className="cursor-pointer"
            onClick={() => {
              router.push("/workshop/" + record.id);
            }}
          >
            {text}
          </div>
        );
      },
    },
    {
      title: "Topic",
      dataIndex: "topic",
      key: "topic",
      className: "text-left",
    },

    {
      title: "Time start",
      dataIndex: "start",
      key: "start",
      className: "text-left",
    },
    {
      title: "Time end",
      dataIndex: "end",
      key: "end",
      className: "text-left",
    },
    {
      title: "Score",
      dataIndex: "score",
      key: "score",
      className: "text-left",
    },
  ];

  const fetchData2 = async () => {
    try {
      const res = await getScoreUserService(Cookies.get("userId"));
      switch (res.data.statusCode) {
        case "200":
          setScore(res.data.data);
          break;
        default:
          break;
      }
    } catch (error) {
      console.log("err" + error);
    }
  };
  const fetchData = async () => {
    const data = {
      pageNumber: 0,
      pageSize: 10,
      usersId: Cookies.get("userId"),
      eventId: null,
    };
    try {
      const res = await fetchUserHistoryService(data);
      switch (res.data.statusCode) {
        case "200":
          mapDataTable(res.data.data.content);
          break;
        default:
          break;
      }
    } catch (error) {
      console.log("err" + error);
    }
  };
  const mapDataTable = (data) => {
    const list = data.map((item, index) => {
      return {
        key: index + 1,
        id: item.eventResponse.eventId,
        name: item.eventResponse.name,
        topic: item.eventResponse.topic,
        start: formatDateTime(item.eventResponse.timeStart),
        end: formatDateTime(item.eventResponse.timeEnd),
        // score: (item.score ? item.score : 0) + "/" + item.eventResponse.score,
        score: item.score ? item.score : 0,
      };
    });
    setData(list);
  };
  useEffect(() => {
    fetchData();
    fetchData2();
  }, []);

  return (
    <LoginLayout>
      <div className="container mx-auto my-40">
        <div className="flex justify-between mb-12">
          <div className="mx-2 ">
            <div className="text-4xl textPrimary2 font-bold ">
              Account Score
            </div>

            <div className="bgPrimary2 h-1 w-20 my-4"></div>
          </div>
          <div className="text-4xl textPrimary2 font-bold ">
            Score Total: {score}
          </div>
        </div>
        <div>
          <Table
            columns={columns}
            dataSource={data}
            pagination={{
              defaultCurrent: 1,
              total: 10,
              pageSize: 10,
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
        </div>
      </div>
    </LoginLayout>
  );
}
