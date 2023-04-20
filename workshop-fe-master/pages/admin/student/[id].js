import CustomLayout from "@/layouts/LayoutAnt";
import dayjs from "dayjs";
const dateFormat = "YYYY-MM-DD";

import {
  addUserServie,
  fetchCourseService,
  fetchMajorService,
  getDetailUserService,
  updateUserServie,
} from "@/services/adminService";
import { Input, Radio, Select } from "antd";
import { useRouter } from "next/router";
import { ArrowLeftOutlined } from "@ant-design/icons";
import { useEffect, useState } from "react";
import { DatePicker, Space } from "antd";
import { UploadOutlined } from "@ant-design/icons";
import { Button, Upload } from "antd";
import { uploadFile } from "@/services/uploadService";
import dynamic from "next/dynamic";
const ReactQuill = dynamic(() => import("react-quill"), { ssr: false });
import "react-quill/dist/quill.snow.css";

const { RangePicker } = DatePicker;
import locale from "antd/lib/locale/vi_VN";
import { toast } from "react-toastify";

export default function CreateUser() {
  const [course, setCourse] = useState(null);
  const [major, setMajor] = useState(null);
  const [form, setForm] = useState({
    id: "",
    // password: "",
    email: "",
    fullName: "",
    birthday: "",

    phone: "",
    gender: "",
    majorId: null,
    courseId: null,
    score: "",
  });

  const router = useRouter();
  const userId = router.query?.id;
  const fetchData = async () => {
    try {
      const res = await getDetailUserService(userId);
      switch (res.data.statusCode) {
        case "200":
          let user = res.data.data;

          setForm({
            ...form,
            id: user?.usersId,
            password: user.password,
            email: user.email,
            fullName: user.fullName,
            birthday: user.birthday,
            phone: user.phone,
            gender: user?.gender,
            majorId: user?.majorResponse?.majorId,
            courseId: user?.courseResponse?.courseId,
            score: user.totalScore,
          });

          break;
        default:
          break;
      }
    } catch (error) {
      console.log("err" + error);
    }
  };

  const [errors, setErrors] = useState({
    id: "",
    email: "",
    // password: "",
    fullName: "",
    phone: "",
    birthday: "",
    gender: "",
    majorId: "",
    courseId: "",
  });

  const {
    id,
    email,
    // password,
    fullName,
    birthday,
    phone,
    gender,
    majorId,
    courseId,
  } = form;
  const {
    id: idErr,
    email: emailErr,
    birthday: birthdayErr,
    // password: passwordErr,
    fullName: fullNameErr,
    phone: phoneErr,
    gender: genderErr,
  } = errors;

  const validateForm = () => {
    let isValid = true;
    const errors = {};

    if (!form.id.trim()) {
      errors.id = "id is required";
      isValid = false;
    }
    if (!form.birthday.trim()) {
      errors.birthday = "birthday is required";
      isValid = false;
    }

    if (!form.email.trim()) {
      errors.email = "email is required";
      isValid = false;
    } else if (!/\S+@\S+\.\S+/.test(form.email)) {
      errors.email = "email is invalid";
      isValid = false;
    }
    if (!form.fullName.trim()) {
      errors.fullName = "fullName is required";
      isValid = false;
    } else if (!/^[\p{L}\p{M}\s]+$/u.test(form.fullName.trim())) {
      errors.fullName = "fullName must only contain letters";
      isValid = false;
    }
    if (!form.phone.trim()) {
      errors.phone = "phone is required";
      isValid = false;
    } else if (form.phone.length > 10) {
      errors.phone = "phone must be a 10-digit number";
      isValid = false;
    }

    if (!form.gender.trim()) {
      errors.gender = "gender is required";
      isValid = false;
    }

    setErrors(errors);

    return isValid;
  };
  const handleSubmit = async () => {
    const isValid = validateForm();

    if (!isValid) {
      return;
    }

    const data = {
      id: form.id,
      fullName: form.fullName,
      phone: form.phone,
      email: form.email,
      birthday: form.birthday + " 00:00:00",
      gender: form.gender,
      majorId: form.majorId,
      courseId: form.courseId,
      score: form.score,
    };
    try {
      const res = await updateUserServie(data);
      switch (res.data.statusCode) {
        case "200":
          toast.success("Update success");
          router.push("/admin/student/" + res.data.data.usersId);
          break;
        default:
          break;
      }
    } catch (error) {
      console.log("err" + error);
      toast.error(error?.response.data.message[0]);
    }
  };
  const fetchDataCourse = async (data) => {
    try {
      const res = await fetchCourseService(data);
      switch (res.data.statusCode) {
        case "200":
          let result = res.data.data.content?.map((item) => ({
            label: item.name,
            value: item.courseId,
          }));
          setCourse(result);
          break;
        default:
          break;
      }
    } catch (error) {
      console.log("err" + error);
    }
  };
  const fetchDataMajor = async (data) => {
    try {
      const res = await fetchMajorService(data);
      switch (res.data.statusCode) {
        case "200":
          let result = res.data.data.content?.map((item) => ({
            label: item.name,
            value: item.majorId,
          }));
          setMajor(result);
          break;
        default:
          break;
      }
    } catch (error) {
      console.log("err" + error);
    }
  };

  useEffect(() => {
    const data = {
      pageNumber: 0,
      pageSize: 100,
    };

    fetchDataMajor(data);
    fetchDataCourse(data);
  }, []);
  useEffect(() => {
    if (userId) {
      fetchData();
    }
  }, [userId]);

  return (
    <>
      {form && (
        <CustomLayout>
          <div
            className="text-xl cursor-pointer "
            onClick={() => router.push("/admin/student")}
          >
            <ArrowLeftOutlined />
          </div>
          <div className="flex justify-between items-center my-8">
            <div className="text-2xl font-medium">
              <span className="font-bold">Update User:</span> {form?.id}
            </div>
            <div
              onClick={handleSubmit}
              className="bgPrimary px-6 py-2 text-white rounded-lg cursor-pointer"
            >
              Save
            </div>
          </div>
          <div className="grid grid-cols-4 gap-6">
            <div className="col-span-4 shadow rounded-lg bg-white p-6 grid grid-cols-2 gap-8">
              <div>
                <div className="text-xs font-semibold mb-2">
                  ID student <span className="text-red-500">*</span>
                  {idErr && <span className="text-red-500">{idErr}</span>}
                </div>
                <Input
                  disabled
                  placeholder=""
                  required
                  value={id}
                  onChange={(e) =>
                    setForm({
                      ...form,
                      id: e.target.value,
                    })
                  }
                />
              </div>
              <div>
                <div className="text-xs font-semibold mb-2">
                  Email <span className="text-red-500">*</span>
                  {emailErr && <span className="text-red-500">{emailErr}</span>}
                </div>
                <Input
                  disabled
                  placeholder=""
                  required
                  value={email}
                  onChange={(e) =>
                    setForm({
                      ...form,
                      email: e.target.value,
                    })
                  }
                />
              </div>
              <div>
                <div className="text-xs font-semibold mb-2">
                  Birthday <span className="text-red-500">*</span>
                  {birthdayErr && (
                    <span className="text-red-500">{birthdayErr}</span>
                  )}
                </div>
                {form && (
                  <DatePicker
                    defaultValue={dayjs(form?.birthday, dateFormat)}
                    value={
                      form?.birthday ? dayjs(form?.birthday, dateFormat) : ""
                    }
                    format={dateFormat}
                    onChange={(date, dateString) => {
                      setForm({
                        ...form,
                        birthday: dateString,
                      });
                      console.log(form);
                    }}
                  />
                )}
              </div>

              <div>
                <div className="text-xs font-semibold mb-2">
                  Full Name <span className="text-red-500">*</span>
                  {fullNameErr && (
                    <span className="text-red-500">{fullNameErr}</span>
                  )}
                </div>
                <Input
                  placeholder=""
                  required
                  value={fullName}
                  onChange={(e) => {
                    if (!/^[\p{L}\p{M}\s]+$/u.test(e.target.value.trim())) {
                      errors.fullName = "fullName must only contain letters";
                    } else {
                      errors.fullName = "";
                    }
                    setForm({
                      ...form,
                      fullName: e.target.value,
                    });
                  }}
                />
              </div>

              <div>
                <div className="text-xs font-semibold mb-2">
                  Phone <span className="text-red-500">*</span>
                  {phoneErr && <span className="text-red-500">{phoneErr}</span>}
                </div>
                <Input
                  type="number"
                  maxLength={10}
                  min={0}
                  max={999999999}
                  placeholder=""
                  required
                  value={phone}
                  onChange={(e) => {
                    if (e.target.value.length > 10) {
                      errors.phone = "phone must be a 10-digit number";
                    } else {
                      errors.phone = "";
                    }
                    setForm({
                      ...form,
                      phone: e.target.value,
                    });
                  }}
                />
              </div>
              <div>
                <div className="text-xs font-semibold mb-2">
                  Gender <span className="text-red-500">*</span>
                  {genderErr && (
                    <span className="text-red-500">{genderErr}</span>
                  )}
                </div>
                {form?.gender && (
                  <Radio.Group
                    defaultValue={form.gender}
                    onChange={(e) => {
                      setForm({
                        ...form,
                        gender: e.target.value,
                      });
                    }}
                  >
                    <Radio value={"MALE"}>MALE</Radio>
                    <Radio value={"FEMALE"}>FEMALE</Radio>
                  </Radio.Group>
                )}
              </div>
              <div>
                <div className="text-xs font-semibold mb-2">
                  Major <span className="text-red-500">*</span>
                </div>

                <Select
                  value={form?.majorId}
                  style={{ width: 320 }}
                  onChange={(value) => {
                    setForm({
                      ...form,
                      majorId: value,
                    });
                  }}
                  options={major}
                />
              </div>

              <div>
                <div className="text-xs font-semibold mb-2">
                  Course <span className="text-red-500">*</span>
                </div>

                <Select
                  value={form?.courseId}
                  style={{ width: 320 }}
                  onChange={(value) => {
                    setForm({
                      ...form,
                      courseId: value,
                    });
                  }}
                  options={course}
                />
              </div>
              {/* <div>
              <div className="text-xs font-semibold mb-2">
                Course <span className="text-red-500">*</span>
              </div>
            
              {form.courseId && (
                <Select
                  mode="multiple"
                  defaultValue={form?.courseId}
                  style={{ width: 320 }}
                  onChange={(value) => {
                    setForm({
                      ...form,
                      courseId: value,
                    });
                  }}
                  options={course}
                />
              )}
            </div> */}
              <div>
                <div className="text-xs font-semibold mb-2">
                  Score <span className="text-red-500">*</span>
                </div>
                {/* <Select
                mode="multiple"
                allowClear
                style={{ width: "100%" }}
                placeholder="Please select"
                defaultValue={["a10", "c12"]}
                onChange={handleChange}
                options={options}
              /> */}
                <Input
                  disabled
                  placeholder=""
                  required
                  value={form.score}
                  onChange={(e) =>
                    setForm({
                      ...form,
                      score: e.target.value,
                    })
                  }
                />
              </div>
            </div>
            {/* <div className="shadow rounded-lg bg-white p-3 h-40">
              <div className="w-full font-semibold border-b p-2">
                Information
              </div>
              <div className="flex justify-between px-3 py-2">
                <div className="text-sm font-semibold  ">Created</div>
                <div className="text-sm">{create?.time}</div>
              </div>
              <div className="flex justify-between px-3 py-2">
                <div className="text-sm font-semibold  ">By</div>
                <div className="text-sm">{create?.name}</div>
              </div>
            </div> */}
          </div>
        </CustomLayout>
      )}
    </>
  );
}
