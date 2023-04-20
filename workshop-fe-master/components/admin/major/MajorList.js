import { fetchMajorService, deleteMajorService } from "@/services/adminService";
import React, { useEffect, useState } from "react";
import { Space, Table, Tag } from "antd";

import { Input } from "antd";
import { useRouter } from "next/router";
import { EditOutlined, DeleteOutlined } from "@ant-design/icons";
import ModalConfirm from "@/components/modal/ModalConfirm";
import { toast } from "react-toastify";
const { Search } = Input;

// const getRandomuserParams = (params) => ({
//   pageSize: params.pagination?.pageSize,
//   pageNumber: params.pagination?.current,
//   ...params,
// });
export default function MajorList() {
  const [totalPage, setTotalPage] = useState(1);
  const [majorId, setMajorId] = useState(0);
  const [modal, setModal] = useState(false);

  const [tableParams, setTableParams] = useState({
    pagination: {
      current: 0,
      pageSize: 10,
    },
  });
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const router = useRouter();
  const columns = [
    {
      title: "ID",
      dataIndex: "id",
      key: "id",
      className: "text-left",
    },
    {
      title: "Name",
      dataIndex: "name",
      key: "name",
      className: "font-bold",
      render: (text, record) => {
        return <a href={"major/" + record.id}>{text}</a>;
      },
    },
    // {
    //   title: "Description",
    //   dataIndex: "description",
    //   key: "description",
    //   className: "text-left",
    // },
    {
      title: "Action",
      key: "action",
      render: (_, record) => (
        <Space size="middle">
          <a href={"major/" + record.id}>
            <EditOutlined />
          </a>
          <a>
            <DeleteOutlined
              onClick={() => {
                setModal(true);
                setMajorId(record.id);
              }}
            />
          </a>
        </Space>
      ),
    },
  ];
  const mapDataTable = (data) => {
    const list = data.map((item, index) => {
      return {
        key: index + 1,
        id: item.majorId,
        name: item.name,
        description: item.description,
      };
    });
    setData(list);
  };
  const onSearch = async (keyword) => {
    setLoading(true);
    try {
      const res = await fetchMajorService({
        pageNumber: 0,
        pageSize: 10,
        keyword,
      });
      mapDataTable(res.data.data.content);
      setLoading(false);
    } catch (error) {
      console.log("err" + error);
    }
  };
  const handleDelete = async (id) => {
    try {
      const res = await deleteMajorService(id);

      switch (res.data.statusCode) {
        case "200":
          toast.success("Delete success.");
          setModal(false);
          fetchData({
            pageNumber: 0,
            pageSize: 10,
            keyword: "",
          });
          break;

        default:
          break;
      }
    } catch (error) {
      if (error.response.status == 500) {
        toast.error(
          "It is not possible to delete an major that has already been registered."
        );
        setModal(false);
      }
      console.log(error.response.status);
      // toast.error(error.data.status)
    }
  };
  // const handleDelete = async (id) => {
  //   const res = await deleteMajorService(id);
  //   switch (res.data.statusCode) {
  //     case "200":
  //       fetchData({
  //         pageNumber: 0,
  //         pageSize: 10,
  //         keyword: "",
  //       });
  //       break;

  //     default:
  //       break;
  //   }
  // };
  const fetchData = async (data) => {
    setLoading(true);
    try {
      const res = await fetchMajorService(data);
      switch (res.data.statusCode) {
        case "200":
          mapDataTable(res.data.data.content);
          setTotalPage(res.data.data.totalElements);

          setLoading(false);
          break;
        default:
          break;
      }
    } catch (error) {
      console.log("err" + error);
    }
  };
  useEffect(() => {
    fetchData({ pageNumber: 0, pageSize: 10, keyword: "" });
  }, []);

  return (
    <div>
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
          onClick={() => router.push("/admin/major/create")}
          className="bgPrimary px-6 py-2 text-white rounded-lg cursor-pointer"
        >
          Create Major
        </div>
      </div>
      <Table
        columns={columns}
        dataSource={data}
        className="w-full"
        pagination={{
          defaultCurrent: 1,
          total: totalPage,
          pageSize: 10,
          showSizeChanger: false,
          onChange: (page, pageSize) => {
            fetchStudent({
              pageNumber: page - 1 < 0 ? 0 : page - 1,
              pageSize,
            });
          },
        }}
      ></Table>
      <ModalConfirm
        show={modal}
        title="Do you want to delete?"
        handleCancel={() => setModal(false)}
        handleConfirm={() => {
          handleDelete(majorId);
        }}
      />
    </div>
  );
}
