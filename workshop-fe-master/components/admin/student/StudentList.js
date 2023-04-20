import {
  adminUpdatePasswordUserService,
  fetchUserService,
  removeUserService,
} from "@/services/adminService";
import React, { useEffect, useState } from "react";
import { Table, Space, Modal, Form, Button } from "antd";
import { EditOutlined, DeleteOutlined, KeyOutlined } from "@ant-design/icons";
import { Input } from "antd";
import { useRouter } from "next/router";
import Cookies from "js-cookie";
import ModalConfirm from "@/components/modal/ModalConfirm";
import { toast } from "react-toastify";
import { async } from "rxjs";

const { Search } = Input;
export default function StudentList() {
  const router = useRouter();
  const [totalPage, setTotalPage] = useState(1);
  const [data, setData] = useState(null);
  const [userId, setUserId] = useState(0);

  const [modal, setModal] = useState(false);
  const [modalEditPassword, setModalEditPassword] = useState(false);
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
        return <a href={"student/" + record.id}>{text}</a>;
      },
    },
    {
      title: "Gender",
      dataIndex: "gender",
      key: "gender",
      className: "text-left",
    },
    {
      title: "Total Score",
      dataIndex: "totalScore",
      key: "totalScore",
      className: "text-left",
    },
    {
      title: "Major",
      dataIndex: "major",
      key: "major",
      className: "text-left",
    },
    {
      title: "Course",
      dataIndex: "course",
      key: "course",
      className: "text-left",
    },
    {
      title: "Phone",
      dataIndex: "phone",
      key: "phone",
      className: "text-left",
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
      className: "text-left",
    },
    {
      title: "Action",
      key: "action",
      render: (_, record) => (
        <Space size="middle">
          <a href={"student/" + record.id}>
            <EditOutlined />
          </a>
          <a>
            <DeleteOutlined
              onClick={() => {
                setModal(true);
                setUserId(record.id);
              }}
            />
          </a>
          <a>
            <KeyOutlined
              onClick={() => {
                setModalEditPassword(true);
                setUserId(record.id);
              }}
            />
          </a>
        </Space>
      ),
    },
  ];
  const handleDelete = async (id) => {
    try {
      const res = await removeUserService(id);

      switch (res.data.statusCode) {
        case "200":
          toast.success("Delete success.");
          setModal(false);
          fetchStudent({
            role: 'ROLE_USER',
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
      console.log(error);
      if (error?.response?.code == 422) {
        setModal(false);
        var str = error.response.data.errorCode;
        str = str.replace(/_/g, " ");
        // toast.error(str);
        toast.error(
          "You cannot delete the user because they have participated in events."
        );
      }
      if (error?.response?.status == 422) {
        setModal(false);
        var str = error.response.data.errorCode;
        str = str.replace(/_/g, " ");
        // toast.error(str);
        toast.error(
          "You cannot delete the user because they have participated in events."
        );
      }
      if (error?.response?.status == 500) {
        toast.error(
          "You cannot delete the user because they have participated in events."
        );
        setModal(false);
      }
      // toast.error(error.data.status)
    }
  };
  const mapDataTable = (data) => {
    const list = data.map((item, index) => {
      return {
        key: index + 1,
        id: item.usersId,
        name: item.fullName,
        gender: item.gender,
        totalScore: item.totalScore,
        major: item?.majorResponse?.name,
        course: item?.courseResponse?.name,
        phone: item.phone,
        email: item.email,
      };
    });
    setData(list);
  };
  const onSearch = async (keyword) => {
    try {
      const res = await fetchUserService({
        pageNumber: 0,
        pageSize: 10,
        role: "ROLE_USER",
        keyword,
      });
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
  const fetchStudent = async (data) => {
    try {
      const res = await fetchUserService(data);
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
      console.log(error?.response?.status);
      if (error?.response?.status === 403) {
        Cookies.remove("accessToken");
      }
    }
  };
  useEffect(() => {
    fetchStudent({
      pageNumber: 0,
      pageSize: 10,
      role: "ROLE_USER",
      keyword: "",
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
          onClick={() => router.push("/admin/student/create")}
          className="bgPrimary px-6 py-2 text-white rounded-lg cursor-pointer"
        >
          Create new student
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
          handleDelete(userId);
        }}
      />
      <Modal
        title="Change password"
        onCancel={() => {
          setModalEditPassword(false);
        }}
        visible={modalEditPassword}
        footer={null}
      >
        <Form

          name="basic"
          initialValues={{
            password : ""
          }}
          onFinish={async (data) => {
            const res = await adminUpdatePasswordUserService({
              userId: userId,
              newPassword: data.password,
            });
            switch (res.data.statusCode) {
              case "200":
                toast.success("Update password success.");
                setModalEditPassword(false);
                break;
              default:
                break;
            }
          }}
          onFinishFailed={() => {}}
        >
          <Form.Item
            label="Password"
            name="password"
            rules={[
              {
                required: true,
                message: "Please input your password!",
              },
            ]}
          >
            <Input.Password />
          </Form.Item>
          <Form.Item>
            <Button
              type="primary"
              style={{
                backgroundColor: "#2D3791",
              }}
              htmlType="submit"
            >
              Submit
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
}
