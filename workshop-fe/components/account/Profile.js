import {
  fetchCourseService,
  fetchMajorService,
  getDetailUserService,
  updateUserServie,
} from "@/services/adminService";
import dayjs from "dayjs";

import Cookies from "js-cookie";
import React, { Component, useEffect, useState } from "react";
import { DatePicker, Input, Radio, Select } from "antd";
import { toast } from "react-toastify";

export default function Profile() {
  const [idFile, setIdFile] = useState(null);
  const [course, setCourse] = useState(null);
  const [major, setMajor] = useState(null);
  const [birthday, setBirthday] = useState(null);
  const [create, setCreate] = useState({
    name: "",
    time: "",
  });
  const onChange = (date, dateString) => {

    setBirthday(dateString);
  };
  // const router = useRouter();
  const userId = Cookies.get("userId");
  const fetchData = async () => {
    try {
      const res = await getDetailUserService(userId);
      switch (res.data.statusCode) {
        case "200":
          let user = res.data.data;

          setBirthday(user.birthday);
          setForm({
            ...form,
            id: user?.usersId,
            password: user.password,
            email: user.email,
            birthday: user.birthday,
            fullName: user.fullName,
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
  const [form, setForm] = useState({
    id: "",
    password: "",
    email: "",
    fullName: "",
    phone: "",
    gender: "",
    majorId: "",
    courseId: "",
    score: "",
  });

  const [errors, setErrors] = useState({
    id: "",
    email: "",
    password: "",
    fullName: "",
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

    // if (!form.password.trim()) {
    //   errors.password = "password is required";
    //   isValid = false;
    // }
    if (!form.email.trim()) {
      errors.email = "email is required";
      isValid = false;
    }

    if (!form.fullName.trim()) {
      errors.fullName = "fullName is required";
      isValid = false;
    }
    if (!/^\d+$/.test(form.phone)) {
      errors.phone = "phone must be a number";
      isValid = false;
    }
    if (!form.gender.trim()) {
      errors.gender = "gender is required";
      isValid = false;
    }

    setErrors(errors);

    return isValid;
  };
  const dateFormat = "YYYY-MM-DD";
  const handleSubmit = async () => {
    const isValid = validateForm();

    if (!isValid) {
      return;
    }

    const data = {
      id: form.id,
      // fullName: form.fullName,
      phone: form.phone,
      // email: form.email,
      gender: form.gender,
      birthday: form.birthday + " 00:00:00",
      // majorId: form.majorId,
      // courseId: form.courseId,
      // score: form.score,
    };
    try {
      const res = await updateUserServie(data);
      switch (res.data.statusCode) {
        case "200":
          toast.success("Update success");
          // router.push("/admin/student/" + res.data.data.usersId);
          break;

        default:
          break;
      }
    } catch (error) {
      console.log("err" + error);
      toast.error(error?.response?.data.message);
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
      <div className="hidden sm:block" aria-hidden="true">
        <div className="py-5">
          <div className="border-t border-gray-200" />
        </div>
      </div>

      <div className="mt-10 sm:mt-0">
        <div className="md:grid md:grid-cols-3 md:gap-6">
          <div className="md:col-span-1">
            <div className="px-4 sm:px-0">
              <h3 className="text-lg font-medium leading-6 text-gray-900">
                Personal Information
              </h3>
              <p className="mt-1 text-sm text-gray-600">
                Use a permanent address where you can receive mail.
              </p>
            </div>
          </div>
          <div className="mt-5 md:col-span-2 md:mt-0">
            <div>
              <div className="overflow-hidden shadow sm:rounded-md">
                <div className="bg-white px-4 py-5 sm:p-6">
                  <div className="grid grid-cols-6 gap-6">
                    <div className="col-span-6 sm:col-span-3">
                      <label
                        htmlFor="first-name"
                        className="block text-sm font-medium text-gray-700"
                      >
                        ID Student
                      </label>
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
                    <div className="col-span-6 sm:col-span-3">
                      <label
                        htmlFor="first-name"
                        className="block text-sm font-medium text-gray-700"
                      >
                        Full Name
                      </label>
                      <Input
                        disabled
                        placeholder=""
                        required
                        value={fullName}
                        onChange={(e) =>
                          setForm({
                            ...form,
                            fullName: e.target.value,
                          })
                        }
                      />
                    </div>
                    <div className="col-span-6 sm:col-span-3">
                      <label
                        htmlFor="first-name"
                        className="block text-sm font-medium text-gray-700"
                      >
                        Birthday
                      </label>
                      {birthday && (
                        <DatePicker
                          defaultValue={dayjs(form.birthday, dateFormat)}
                          format={dateFormat}
                          // value={birthday}
                          onChange={(date, dateString) =>
                            setForm({
                              ...form,
                              birthday: dateString,
                            })
                          }
                        />
                      )}
                    </div>
                    <div className="col-span-6 sm:col-span-3">
                      <label
                        htmlFor="email-address"
                        className="block text-sm font-medium text-gray-700"
                      >
                        Email
                      </label>
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
                    {/* <div className="col-span-6 sm:col-span-4">
                      <label
                        htmlFor="email-address"
                        className="block text-sm font-medium text-gray-700"
                      >
                        Email
                      </label>
                      <input
                        type="text"
                        name="email-address"
                        id="email-address"
                        autoComplete="email"
                        className="mt-1 py-2 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                      />
                    </div> */}
                    <div className="col-span-6 sm:col-span-3">
                      <label
                        htmlFor="email-address"
                        className="block text-sm font-medium text-gray-700"
                      >
                        Phone <span className="text-red-500"></span>
                        {phoneErr && (
                          <span className="text-red-500">{phoneErr}</span>
                        )}
                      </label>
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
                    <div className="col-span-6 sm:col-span-3">
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
                    </div>

                    <div className="col-span-6 sm:col-span-3">
                      <div className="text-xs font-semibold mb-2">
                        Major <span className="text-red-500">*</span>
                      </div>

                      {form?.majorId && (
                        <>
                          <Select
                            disabled
                            defaultValue={form?.majorId}
                            style={{ width: 320 }}
                            onChange={(value) => {
                              setForm({
                                ...form,
                                majorId: value,
                              });
                            }}
                        
                            options={major}
                          />
                        </>
                      )}
                    </div>
                    <div className="col-span-6 sm:col-span-3">
                      <div className="text-xs font-semibold mb-2">
                        Course <span className="text-red-500">*</span>
                      </div>

                      {form?.courseId && (
                        <>
                          <Select
                            disabled
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
                        </>
                      )}
                    </div>

                    {/* <div>
                      <div className="text-xs font-semibold mb-2">
                        Score <span className="text-red-500">*</span>
                      </div>
                 
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
                    </div> */}
                  </div>
                </div>
                <div className="bg-gray-50  px-4 py-3 text-right sm:px-6">
                  <button
                    onClick={handleSubmit}
                    // type="submit"
                    className="inline-flex cursor-pointer justify-center rounded-md border border-transparent bg-indigo-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                  >
                    Save
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="hidden sm:block" aria-hidden="true">
        <div className="py-5">
          <div className="border-t border-gray-200" />
        </div>
      </div>

      {/* <div className="mt-10 sm:mt-0">
        <div className="md:grid md:grid-cols-3 md:gap-6">
          <div className="md:col-span-1">
            <div className="px-4 sm:px-0">
              <h3 className="text-lg font-medium leading-6 text-gray-900">
                Notifications
              </h3>
              <p className="mt-1 text-sm text-gray-600">
                Decide which communications you'd like to receive and how.
              </p>
            </div>
          </div>
          <div className="mt-5 md:col-span-2 md:mt-0">
            <form action="#" method="POST">
              <div className="overflow-hidden shadow sm:rounded-md">
                <div className="space-y-6 bg-white px-4 py-5 sm:p-6">
                  <fieldset>
                    <legend className="sr-only">By Email</legend>
                    <div
                      className="text-base font-medium text-gray-900"
                      aria-hidden="true"
                    >
                      By Email
                    </div>
                    <div className="mt-4 space-y-4">
                      <div className="flex items-start">
                        <div className="flex h-5 items-center">
                          <input
                            id="comments"
                            name="comments"
                            type="checkbox"
                            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
                          />
                        </div>
                        <div className="ml-3 text-sm">
                          <label
                            htmlFor="comments"
                            className="font-medium text-gray-700"
                          >
                            Comments
                          </label>
                          <p className="text-gray-500">
                            Get notified when someones posts a comment on a
                            posting.
                          </p>
                        </div>
                      </div>
                      <div className="flex items-start">
                        <div className="flex h-5 items-center">
                          <input
                            id="candidates"
                            name="candidates"
                            type="checkbox"
                            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
                          />
                        </div>
                        <div className="ml-3 text-sm">
                          <label
                            htmlFor="candidates"
                            className="font-medium text-gray-700"
                          >
                            Candidates
                          </label>
                          <p className="text-gray-500">
                            Get notified when a candidate applies for a job.
                          </p>
                        </div>
                      </div>
                      <div className="flex items-start">
                        <div className="flex h-5 items-center">
                          <input
                            id="offers"
                            name="offers"
                            type="checkbox"
                            className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
                          />
                        </div>
                        <div className="ml-3 text-sm">
                          <label
                            htmlFor="offers"
                            className="font-medium text-gray-700"
                          >
                            Offers
                          </label>
                          <p className="text-gray-500">
                            Get notified when a candidate accepts or rejects an
                            offer.
                          </p>
                        </div>
                      </div>
                    </div>
                  </fieldset>
                  <fieldset>
                    <legend className="contents text-base font-medium text-gray-900">
                      Push Notifications
                    </legend>
                    <p className="text-sm text-gray-500">
                      These are delivered via SMS to your mobile phone.
                    </p>
                    <div className="mt-4 space-y-4">
                      <div className="flex items-center">
                        <input
                          id="push-everything"
                          name="push-notifications"
                          type="radio"
                          className="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-500"
                        />
                        <label
                          htmlFor="push-everything"
                          className="ml-3 block text-sm font-medium text-gray-700"
                        >
                          Everything
                        </label>
                      </div>
                      <div className="flex items-center">
                        <input
                          id="push-email"
                          name="push-notifications"
                          type="radio"
                          className="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-500"
                        />
                        <label
                          htmlFor="push-email"
                          className="ml-3 block text-sm font-medium text-gray-700"
                        >
                          Same as email
                        </label>
                      </div>
                      <div className="flex items-center">
                        <input
                          id="push-nothing"
                          name="push-notifications"
                          type="radio"
                          className="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-500"
                        />
                        <label
                          htmlFor="push-nothing"
                          className="ml-3 block text-sm font-medium text-gray-700"
                        >
                          No push notifications
                        </label>
                      </div>
                    </div>
                  </fieldset>
                </div>
                <div className="bg-gray-50 px-4 py-3 text-right sm:px-6">
                  <button
                    type="submit"
                    className="inline-flex justify-center rounded-md border border-transparent bg-indigo-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                  >
                    Save
                  </button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div> */}
    </>
  );
}
