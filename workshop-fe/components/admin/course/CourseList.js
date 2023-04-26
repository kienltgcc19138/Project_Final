import {
  fetchCourseService,
  deleteCourseService,
} from "@/services/adminService";
import React, { useEffect, useState } from "react";
import { Input } from "antd";
import { EditOutlined, DeleteOutlined } from "@ant-design/icons";
import { useRouter } from "next/router";
import { Space, Table, Tag } from "antd";
import { formatDateTime } from "@/utils/dateUtils";
import ModalConfirm from "@/components/modal/ModalConfirm";
import { toast } from "react-toastify";
const { Search } = Input;
export default function CourseList() {
const [totalPage, setTotalPage] = useState(1);
  const [courseId, setCourseId] = useState(0);
  const [modal, setModal] = useState(false);

  const [data, setData] = useState(null);
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
      className: "font-bold w-[80%]",
      render: (text, record) => {
        return (
          <div
            className="cursor-pointer"
            onClick={() => {
              router.push("course/" + record.id);
            }}
          >
            {text}
          </div>
        );
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
          <EditOutlined
            className="cursor-pointer"
            onClick={() => {
              router.push("course/" + record.id);
            }}
          />
           <DeleteOutlined
              onClick={() => {
                setModal(true);
                setCourseId(record.id);
              }}
            />
        
        </Space>
      ),
    },
  ];
  const handleDelete = async (id) => {
    try {
      const res = await deleteCourseService(id);

      switch (res.data.statusCode) {
        case "200":
          toast .success("Delete success.");
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
          "It is not possible to delete an course that has already been registered."
        );
        setModal(false);
      }
      console.log(error.response.status);
      // toast.error(error.data.status)
    }
  };
  // const handleDelete = async (id) => {
  //   const res = await deleteCourseService(id);

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
  const mapDataTable = (data) => {
    const list = data.map((item, index) => {
      return {
        key: index + 1,
        id: item.courseId,
        name: item.name,
        description: item.description,
      };
    });
    setData(list);
  };
  const onSearch = async (keyword) => {
    try {
      const res = await fetchCourseService({
        pageNumber: 0,
        pageSize: 10,
        keyword,
      });
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
  const fetchData = async (data) => {
    try {
      const res = await fetchCourseService(data);
      switch (res.data.statusCode) {
        case "200":
          mapDataTable(res.data.data.content);
          setTotalPage(res.data.data.totalElements);

          // setTableParams({
          //   ...tableParams,
          //   pagination: {
          //     ...tableParams.pagination,
          //     total: res.data.data.totalElements,
          //   },
          // });
          // setLoading(false);
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

  return (
    <div >
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
          onClick={() => router.push("/admin/course/create")}
          className="bgPrimary px-6 py-2 text-white rounded-lg cursor-pointer"
        >
         Create Course
        </div>
      </div>
      <Table
        columns={columns}
        dataSource={data}
        className="w-full"
        pagination={{
          defaultCurrent: 1,
          total: 10,
          pageSize: 10,
          showSizeChanger: false,
          onChange: (page, pageSize) => {
            console.log(page, pageSize);
            fetchData({
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
          handleDelete(courseId);
        }}
      />
    </div>
  );
}
