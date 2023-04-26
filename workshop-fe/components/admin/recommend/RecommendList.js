import {
  fetchRecommendService,
  fetchUserService,
} from "@/services/adminService";
import React, { useEffect, useState } from "react";
import { Table, Space } from "antd";
import { EditOutlined, DeleteOutlined } from "@ant-design/icons";
import { Input } from "antd";
import { useRouter } from "next/router";
import Cookies from "js-cookie";
import ModalRecommend from "@/components/modal/ModalRecommend";

const { Search } = Input;
export default function RecommendList() {
  const router = useRouter();
  const [totalPage, setTotalPage] = useState(1);
  const [data, setData] = useState(null);

  const [modal, setModal] = useState(false);

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
    },
    {
      title: "Description",
      dataIndex: "description",
      key: "description",
      className: "text-left",
    },
    // {
    //   title: "Action",
    //   key: "action",
    //   render: (_, record) => (
    //     <Space size="middle">
    //       <a href={"student/" + record.id}>
    //         <EditOutlined />
    //       </a>
    //       {/* <a>
    //         <DeleteOutlined />
    //       </a> */}
    //     </Space>
    //   ),
    // },
  ];
  const mapDataTable = (data) => {
    const list = data.map((item, index) => {
      return {
        key: index + 1,
        id: item.recommendId,
        name: item.name,
        description: item.description,
      };
    });
    setData(list);
  };
  const onSearch = async (keyword) => {
    try {
      let createdBy = "";
      if (Cookies.get("role") == "ROLE_USER") {
        createdBy = Cookies.get("userId");
      }
      let data = {
        pageNumber: 0,
        pageSize: 10,
        keyword,
        createdBy: createdBy,
        sort: [
          {
            key: "createdAt",
            asc: false,
          },
        ],
      };
      console.log("data",data);
      const res = await fetchRecommendService(data);
      switch (res.status) {
        case 200:
          mapDataTable(res.data.content);
          setTotalPage(res.totalElements);
          break;
        default:
          break;
      }
    } catch (error) {
      console.log("err" + error);
    }
  };
  const fetchStudent = async (data) => {
    try {
      const res = await fetchRecommendService(data);
      switch (res.status) {
        case 200:
          mapDataTable(res.data.content);
          setTotalPage(res.totalElements);
          break;
        default:
          break;
      }
    } catch (error) {
      console.log("err" + error);
      console.log(error?.response?.status);
      if (error?.response?.status === 403) {
        Cookies.remove("accessToken");
      }
    }
  };
  useEffect(() => {
    let createdBy = "";
    if (Cookies.get("role") == "ROLE_USER") {
      createdBy = Cookies.get("userId");
    }
    fetchStudent({
      pageNumber: 0,
      pageSize: 10,
      keyword: "",
      createdBy: createdBy,
      sort: [
        {
          key: "createdAt",
          asc: false,
        },
      ],
    });
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
          onClick={() => setModal(true)}
          className={` ${
            Cookies.get("role") == "ROLE_USER" ? "block" : "hidden"
          } bgPrimary px-6 py-2 text-white rounded-lg cursor-pointer`}
        >
          Create recommend
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
      <ModalRecommend
        show={modal}
        handleClose={() => setModal(false)}
        onSearch={() => onSearch("")}
      />
    </div>
  );
}
