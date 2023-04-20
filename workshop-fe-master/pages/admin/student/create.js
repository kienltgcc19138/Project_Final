import CustomLayout from "@/layouts/LayoutAnt";
import {
  addUserServie,
  fetchCourseService,
  fetchMajorService,
} from "@/services/adminService";
import { Radio } from "antd";
import { Input, Select } from "antd";
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
import { async, from } from "rxjs";
const dateFormat = "YYYY-MM-DD";

export default function CreateUser() {
  const [idFile, setIdFile] = useState(null);
  const [course, setCourse] = useState(null);
  const [major, setMajor] = useState(null);
  const [form, setForm] = useState({
    id: "auto",
    password: "12345678",
    email: "",
    fullName: "",
    birthday: "",
    phone: "",
    gender: "MALE",
    majorId: "",
    courseId: "",
  });

  const [errors, setErrors] = useState({
    id: "",
    email: "",
    password: "",
    fullName: "",
    birthday: "",
    phone: "",
    gender: "",
    majorId: "",
    courseId: "",
  });

  const { id, email, password, fullName, phone, gender, majorId, courseId } =
    form;
  const {
    id: idErr,
    email: emailErr,
    password: passwordErr,
    birthday: birthdayErr,
    fullName: fullNameErr,
    phone: phoneErr,
    gender: genderErr,
  } = errors;

  const router = useRouter();

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

    // if (!form.password?.trim()) {
    //   errors.password = "password is required";
    //   isValid = false;
    // }
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
      password: form.password,
      fullName: form.fullName,
      birthday: form.birthday + " 00:00:00",
      phone: form.phone,
      email: form.email,
      gender: form.gender,
      majorId: form.majorId,
      courseId: form.courseId,
    };
    try {
      const res = await addUserServie(data);
      switch (res.data.statusCode) {
        case "200":
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
  // const fetchDataCourse = async (data) => {
  //   try {
  //     const res = await fetchCourseService(data);
  //     switch (res.data.statusCode) {
  //       case "200":
  //         let result = res.data.data.content?.map((item) => ({
  //           label: item.name,
  //           value: item.courseId,
  //         }));
  //         setCourse(result);
  //         setForm({
  //           ...form,
  //           courseId: result[0].value,
  //         });
  //         break;
  //       default:
  //         break;
  //     }
  //   } catch (error) {
  //     console.log("err" + error);
  //   }
  // };
  // const fetchDataMajor = async (data) => {
  //   try {
  //     const res = await fetchMajorService(data);
  //     switch (res.data.statusCode) {
  //       case "200":
  //         let result = res.data.data.content?.map((item) => ({
  //           label: item.name,
  //           value: item.majorId,
  //         }));
  //         setMajor(result);
  //         setForm({
  //           ...form,
  //           majorId: result[0].value,
  //         });
  //         break;
  //       default:
  //         break;
  //     }
  //   } catch (error) {
  //     console.log("err" + error);
  //   }
  // };

  const fetchData = async (data) => {
    try {
      const res = await fetchMajorService(data);
      let resultMajor ;
      let resultCourse;
      switch (res.data.statusCode) {
        case "200":
          resultMajor = res.data.data.content?.map((item) => ({
            label: item.name,
            value: item.majorId,
          }));
          setMajor(resultMajor);
          break;
        default:
          break;
      }

      const res1 = await fetchCourseService(data);
      switch (res1.data.statusCode) {
        case "200":
          resultCourse = res1.data.data.content?.map((item) => ({
            label: item.name,
            value: item.courseId,
          }));
          setCourse(resultCourse);
          break;
        default:
          break;
      }

      setForm({
        ...form,
        majorId: resultMajor[0].value,
        courseId: resultCourse[0].value,
      });
    } catch (error) {
      console.log("err" + error);
    }
  };

  useEffect(() => {
    const data = {
      pageNumber: 0,
      pageSize: 100,
    };
    fetchData(data);
  }, []);

  return (
    <>
      <CustomLayout>
        <div className="text-xl cursor-pointer " onClick={() => router.back()}>
          <ArrowLeftOutlined />
        </div>
        <div className="flex justify-between items-center mb-12">
          <div className="text-2xl font-medium">Create new student</div>
          <div
            onClick={handleSubmit}
            className="bgPrimary px-6 py-2 text-white rounded-lg cursor-pointer"
          >
            Save
          </div>
        </div>
        <div className="grid grid-cols-4 gap-6">
          <div className="col-span-4 shadow rounded-lg bg-white p-6 grid grid-cols-2 gap-8">
            {/* <div>
              <div className="text-xs font-semibold mb-2">
                Id <span className="text-red-500">*</span>
                {idErr && <span className="text-red-500">{idErr}</span>}
              </div>
              <Input
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
            </div> */}
            <div>
              <div className="text-xs font-semibold mb-2">
                Email <span className="text-red-500">*</span>
                {emailErr && <span className="text-red-500">{emailErr}</span>}
              </div>
              <Input
                placeholder=""
                required
                type="email"
                value={email}
                onChange={(e) => {
                  setForm({
                    ...form,
                    email: e.target.value,
                  });
                }}
              />
            </div>
            {/* <div>
              <div className="text-xs font-semibold mb-2">
                password <span className="text-red-500">*</span>
                {passwordErr && (
                  <span className="text-red-500">{passwordErr}</span>
                )}
              </div>
              <Input
                placeholder=""
                required
                value={password}
                onChange={(e) =>
                  setForm({
                    ...form,
                    password: e.target.value,
                  })
                }
              />
            </div> */}
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
                Birthday <span className="text-red-500">*</span>
                {birthdayErr && (
                  <span className="text-red-500">{birthdayErr}</span>
                )}
              </div>
              <DatePicker
                // defaultValue={dayjs(birthday, dateFormat)}
                format={dateFormat}
                // value={birthday}
                onChange={(date, dateString) =>
                  setForm({
                    ...form,
                    birthday: dateString,
                  })
                }
              />
            </div>
            {/* {birthday && (
              <DatePickercker
                defaultValue={dayjs(birthday, dateFormat)}
                format={dateFormat}
                onChange={onChange}
              />
            )} */}
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
                {genderErr && <span className="text-red-500">{genderErr}</span>}
              </div>
              <Radio.Group
                defaultValue={"MALE"}
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
              {/* <Select
                defaultValue={() => {
                  setForm({
                    ...form,
                    gender: "MALE",
                  });
                  return "MALE";
                }}
                style={{ width: 120 }}
                onChange={(value) => {
                  setForm({
                    ...form,
                    gender: value,
                  });
                }}
                options={[
                  { value: "MALE", label: "male" },
                  { value: "FEMALE", label: "female" },
                ]}
              /> */}
            </div>
            <div>
              <div className="text-xs font-semibold mb-2">
                Major <span className="text-red-500">*</span>
                {/* {genderErr && <span className="text-red-500">{genderErr}</span>} */}
              </div>
              {major && (
                <Select
                  defaultValue={() => {
                    // setForm({
                    //   ...form,
                    //   majorId: major[0]?.value,
                    // });
                    console.log(major[0]?.value);
                    return major[0]?.value;
                  }}
                  style={{ width: 320 }}
                  onChange={(value) => {
                    setForm({
                      ...form,
                      majorId: value,
                    });
                  }}
                  options={major}
                />
              )}
            </div>
            <div>
              <div className="text-xs font-semibold mb-2">
                Course <span className="text-red-500">*</span>
                {/* {genderErr && <span className="text-red-500">{genderErr}</span>} */}
              </div>
              {course && (
                <Select
                  defaultValue={() => {
                    // setForm({
                    //   ...form,
                    //   courseId: course[0]?.value,
                    // });
                    return course[0]?.value;
                  }}
                  style={{ width: 120 }}
                  onChange={(value) => {
                    setForm({
                      ...form,
                      courseId: value,
                    });
                  }}
                  options={course}
                />
              )}
            </div>
          </div>
          {/* <div className="shadow rounded-lg h-min bg-white p-3">
            <div className="w-full font-semibold border-b p-2">Information</div>
            <div className="flex justify-between px-3 py-2">
              <div className="text-sm font-semibold  ">Created</div>
              <div className="text-sm">now</div>
            </div>
            <div className="flex justify-between px-3 py-2">
              <div className="text-sm font-semibold  ">By</div>
              <div className="text-sm">-</div>
            </div>
          </div> */}
        </div>
      </CustomLayout>
    </>
  );
}
