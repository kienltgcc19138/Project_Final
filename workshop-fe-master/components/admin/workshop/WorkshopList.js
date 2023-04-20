import {
  deleteWorkshopService,
  fetchWorkshopService,
  setStatusService,
} from "@/services/adminService";
import React, { useEffect, useState } from "react";
import { Input } from "antd";
import {
  EditOutlined,
  DeleteOutlined,
  OrderedListOutlined,
  PlusCircleOutlined,
} from "@ant-design/icons";
import { useRouter } from "next/router";
import { Space, Table, Tag } from "antd";
import { formatDateTime } from "@/utils/dateUtils";
import ModalConfirm from "@/components/modal/ModalConfirm";
import { toast } from "react-toastify";
import { async } from "rxjs";
const { Search } = Input;
export default function WorkshopManager() {
  const [data, setData] = useState(null);
  const [eventId, setEventId] = useState(0);

  const [modal, setModal] = useState(false);
  const [activeId, setActiveId] = useState("");
  const [totalPage, setTotalPage] = useState(1);
  const router = useRouter();

  const handleSetStatus = async (id, status) => {
    const data = {
      status: status,
    };
    try {
      const res = await setStatusService(id, data);
    } catch (err) {
      console.log(err.response.data.errorCode);
      if ((err.response.code = 422)) {
        var str = err.response.data.errorCode;
        str = str.replace(/_/g, " ");
        toast.error(str);
      }
    }

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
  };
  const columns = [
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
              router.push("workshops/" + record.id);
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
      title: "Status",
      dataIndex: "status",
      key: "status",
      className: "text-left",
      render: (_, record) => (
        <Space size="middle">
          <div
            className={`w-4 h-4 rounded-full ${
              record.status == "UPCOMING" ? "bg-[green]" : ""
            } ${record.status === "HAPPENED" ? "bg-[red]" : ""}  ${
              record.status === "HAPPENING" ? "bg-[orange]" : ""
            }   border`}
          ></div>
        </Space>
      ),
    },

    {
      title: "Score",
      dataIndex: "score",
      key: "score",
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
      title: "Attendance",
      key: "attendance",
      render: (_, record) => (
        <Space size="middle">
          <a>
            <div
              onClick={() => {
                router.push("workshops/" + record.id + `?attendance=true`);
              }}
            >
              List
            </div>
          </a>
        </Space>
      ),
    },
    {
      title: "Action",
      key: "action",
      render: (_, record) => (
        <Space size="middle">
          <a>
            <EditOutlined
              className="cursor-pointer"
              onClick={() => {
                router.push("workshops/" + record.id);
              }}
            />
          </a>
          <a>
            <DeleteOutlined
              onClick={() => {
                setModal(true);
                setEventId(record.id);
              }}
            />
          </a>
          <a className="relative">
            <PlusCircleOutlined
              onClick={(e) => {
                e.stopPropagation();
                
                if (activeId === record.id) {
                  setActiveId("");
                } else {
                  setActiveId(record.id);
                }
              }}
            />

            {activeId == record.id && (
              <div className="absolute z-[999] top-8 right-0 border rounded-md w-32 bg-white">
                <div
                  onClick={() => {
                    handleSetStatus(record.id, "UPCOMING");
                    setActiveId("");
                  }}
                  className="border-b px-4 py-2"
                >
                  UPCOMING
                </div>
                <div
                  onClick={() => {
                    handleSetStatus(record.id, "HAPPENING");
                    setActiveId("");
                  }}
                  className="border-b px-4 py-2"
                >
                  HAPPENING
                </div>
                <div
                  onClick={() => {
                    handleSetStatus(record.id, "HAPPENED");
                    setActiveId("");
                  }}
                  className=" px-4 py-2"
                >
                  HAPPENED
                </div>
              </div>
            )}
          </a>
        </Space>
      ),
    },
  ];
  const mapDataTable = (data) => {
    const list = data.map((item, index) => {
      return {
        key: index + 1,
        id: item.eventId,
        name: item.name,
        topic: item.topic,
        status: item.status,
        score: item.score,
        start: formatDateTime(item.timeStart),
        end: formatDateTime(item.timeEnd),
      };
    });
    setData(list);
  };
  const fetchData = async (data) => {
    try {
      const res = await fetchWorkshopService(data);
      switch (res.data.statusCode) {
        case "200":
          mapDataTable(res.data.data.content);
          setTotalPage(res.data.data.totalElements);
          break;
        default:
          break;
      }
    } catch (error) {
      console.log("err" + error);
    }
  };
  useEffect(() => {
    fetchData({
      pageNumber: 0,
      pageSize: 10,
      keyword: "",
    });
  }, []);
  const handleDelete = async (id) => {
    try {
      const res = await deleteWorkshopService(id);

      switch (res.data.statusCode) {
        case "200":
          toast.success("Delete success.");
          setModal(false);
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
    } catch (error) {
      // if ((err.response.code = 422)) {
      //   var str = err.response.data.errorCode;
      //   str = str.replace(/_/g, " ");
      //   toast.error(str);
      // }
      if (error?.response?.status == 500) {
        if ((error.response.code = 422)) {
          // var str = error.response.data.errorCode;
          // str = str.replace(/_/g, " ");
          // toast.error(
          //   "It is not possible to delete an event that has already been registered."
          // );
        }
        toast.error(
          "It is not possible to delete an event that has already been registered."
        );
        setModal(false);
      }

      // toast.error(error.data.status)
    }
  };
  const onSearch = async (keyword) => {
    try {
      const res = await fetchWorkshopService({
        pageNumber: 0,
        pageSize: 10,
        keyword,
      });
      mapDataTable(res.data.data.content);
      setTotalPage(res.data.data.totalElements);
    } catch (error) {
      console.log("err" + error);
    }
  };

  return (
    <div onClick={() => setActiveId("")} className="h-[100vh]">
      <div className="flex justify-between items-center mb-8">
        <Search
          className="w-full md:w-[30%]  rounded-lg"
          placeholder="input search text"
          style={{
            backgroundColor: "#2D3791",
          }}
          allowClear
          enterButton="Search"
          size="large"
          onSearch={onSearch}
        />
        <div
          onClick={() => router.push("/admin/workshops/create")}
          className="bgPrimary px-6 py-2 text-white rounded-lg cursor-pointer"
        >
          Create Event
        </div>
      </div>
      <Table
        columns={columns}
        dataSource={data}
        pagination={{
          defaultCurrent: 1,
          total: totalPage,
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
      <ModalConfirm
        show={modal}
        title="Do you want to delete?"
        handleCancel={() => setModal(false)}
        handleConfirm={() => {
          handleDelete(eventId);
        }}
      />
    </div>
  );
}
