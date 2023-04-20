import CustomLayout from "@/layouts/LayoutAnt";
import {
  editWorkshopServie,
  exportEvent,
  getDetailWorkshopService,
} from "@/services/adminService";
import { Checkbox } from "antd";

import { Table, Tag } from "antd";
import { InputNumber } from "antd";
import dynamic from "next/dynamic";
const ReactQuill = dynamic(() => import("react-quill"), { ssr: false });
import "react-quill/dist/quill.snow.css";
import { Input } from "antd";
import { useRouter } from "next/router";
import { ArrowLeftOutlined } from "@ant-design/icons";
import { useEffect, useState } from "react";
import { UploadOutlined } from "@ant-design/icons";
import { Button, Upload } from "antd";
import { uploadFile } from "@/services/uploadService";

import locale from "antd/lib/locale/vi_VN";
import { toast } from "react-toastify";
import { DatePicker, Space } from "antd";
import dayjs from "dayjs";
import customParseFormat from "dayjs/plugin/customParseFormat";
dayjs.extend(customParseFormat);
const { RangePicker } = DatePicker;
const range = (start, end) => {
  const result = [];
  for (let i = start; i < end; i++) {
    result.push(i);
  }
  return result;
};

// eslint-disable-next-line arrow-body-style
const disabledDate = (current) => {
  // Can not select days before today and today
  return current && current + 2 < dayjs().endOf("day");
};
const disabledDateTime = () => ({
  disabledHours: () => range(0, 24).splice(4, 20),
  disabledMinutes: () => range(30, 60),
});
// const disabledRangeTime = (_, type) => {
//   if (type === "start") {
//     return {
//       disabledHours: () => range(0, 60).splice(4, 20),
//       disabledMinutes: () => range(30, 60),
//       disabledSeconds: () => [55, 56],
//     };
//   }
//   return {
//     disabledHours: () => range(0, 60).splice(20, 4),
//     disabledMinutes: () => range(0, 31),
//     disabledSeconds: () => [55, 56],
//   };
// };
import {
  fetchUserHistoryService,
  updateScoreEventService,
} from "@/services/userService";
const { TextArea } = Input;
export default function CreateWorkshop() {
  const dateFormat = "YYYY-MM-DD HH:mm:ss";
  const [idFile2, setIdFile2] = useState(null);
  const [data, setData] = useState(null);

  const handleDateTimeChange = (value, dateString) => {
    console.log(dateString);
    setForm({ ...form, time: dateString });
  };

  const formatDate = (value) => {
    return moment(value).format("YYYY-MM-DD HH:mm");
  };
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
      className: "font-bold w-[70%]",
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
      title: "Score",
      dataIndex: "score",
      key: "score",
      className: "text-left",
      render: (text, record) => {
        return <div>{text ? text : 0}</div>;
      },
    },
    {
      title: "Attendance",
      dataIndex: "attendance",
      key: "attendance",
      className: "text-left",
      render: (text, record) => {
        return (
          <Checkbox
            checked={record.score > 0 ? true : false}
            onChange={(e) => {
              if (e.target.checked) {
                onChange(form.score, record?.id, true);
              } else {
                onChange(0, record?.id, false);
              }
            }}
          ></Checkbox>
          // <InputNumber
          //   className="w-14"
          //   min={0}
          //   max={form.score}
          //   defaultValue={record.score}
          //   onChange={(value) => {
          //     onChange(value, record?.id);
          //   }}
          // />
        );
      },
    },

    // {
    //   title: "Action",
    //   key: "action",
    //   render: (_, record) => (
    //     <Space size="middle">
    //       <EditOutlined
    //         className="cursor-pointer"
    //         onClick={() => {
    //           router.push("workshops/" + record.id);
    //         }}
    //       />
    //       <a>
    //         <DeleteOutlined
    //           onClick={() => {
    //             handleDelete(record.id);
    //           }}
    //         />
    //       </a>
    //     </Space>
    //   ),
    // },
  ];
  const onChange = async (value, userId, isJoin) => {
    const data = [
      {
        usersId: userId,
        eventId: id,
        score: value,
        isJoin: isJoin,
      },
    ];
    const res = await updateScoreEventService(data);
    switch (res.data.statusCode) {
      case "200":
        fetchData();
        break;

      default:
        break;
    }
  };
  const handleExport = async () => {
    try {
      const res = await exportEvent(id);
      // Create a Blob object from the response data
      const blob = new Blob([res.data]);

      // Create a URL for the blob
      const url = URL.createObjectURL(blob);
      // Create a temporary link element
      const link = document.createElement("a");
      link.href = url;
      link.download = `${form.name}.xlsx`;

      document.body.appendChild(link);

      // Click the link to start the download
      link.click();

      // Remove the link from the DOM
      document.body.removeChild(link);

      // Revoke the URL to free up memory
      URL.revokeObjectURL(url);
    } catch (error) {
      console.error(error);
    }
  };

  const mapDataTable = (data) => {
    const list = data.map((item, index) => {
      return {
        key: index + 1,
        id: item.usersResponse.usersId,
        name: item.usersResponse?.fullName,
        score: item.score,
      };
    });
    setData(list);
  };
  const [form, setForm] = useState({
    name: "",
    topic: "",
    location: "",
    time: [],
    score: "",
    description: "",
    idFile: [],
  });
  const [create, setCreate] = useState({
    name: "",
    time: "",
  });
  const [errors, setErrors] = useState({
    name: "",
    topic: "",
    location: "",
    time: [],
    score: "",
    description: "",
  });

  const { name, topic, location, time, score, description } = form;

  const {
    name: nameErr,
    topic: topicErr,
    location: locationErr,
    time: timeErr,
    score: scoreErr,
    description: descriptionErr,
  } = errors;

  const router = useRouter();
  const { id, attendance } = router.query;
  function handleChange(html) {
    setForm({
      ...form,
      description: html,
    });
  }
  const handleRangeChange = (dates, dateStrings) => {
    setForm({
      ...form,
      time: dateStrings,
    });
    console.log(dateStrings);
  };
  const fetchData = async () => {
    console.log("a");
    const data = {
      pageNumber: 0,
      pageSize: 10,
      eventId: id,
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
  const fetchDetail = async (id) => {
    try {
      const res = await getDetailWorkshopService(id);
      switch (res.data.statusCode) {
        case "200":
          let data = res.data.data;
          setForm({
            ...form,
            name: data.name,
            topic: data.topic,
            description: data.description,
            location: data.location,
            score: data.score,
            idFile: [
              {
                uid: "0",
                name: data.fileDataResponses[0]?.fileDataName,
                status: "done",
                url: data.fileDataResponses[0]?.link,
                thumbUrl: data.fileDataResponses[0]?.link,
              },
            ],
            time: [data.timeStart, data.timeEnd],
          });
          setIdFile2(data.fileDataResponses[0]?.fileDataId);

          setCreate({
            name: data.createdBy,
            time: data.createdAt,
          });

          break;

        default:
          break;
      }
    } catch (error) {}
  };
  useEffect(() => {
    if (id) {
      fetchDetail(id);
      fetchData();
    }
  }, [id]);

  const validateForm = () => {
    let isValid = true;
    const errors = {};

    if (!form.name.trim()) {
      errors.name = "Name is required";
      isValid = false;
    }

    if (!form.topic.trim()) {
      errors.topic = "Topic is required";
      isValid = false;
    }

    if (!form.location.trim()) {
      errors.location = "Location is required";
      isValid = false;
    }

    if (form.time.length !== 2) {
      errors.time = "Time is required";
      isValid = false;
    }

    if (!`${form.score}`.trim()) {
      errors.score = "Score is required";
      isValid = false;
    }
    if (!/^\d+$/.test(form.score)) {
      errors.score = "Score must be a number";
      isValid = false;
    }
    if (!form.description.trim()) {
      errors.description = "Description is required";
      isValid = false;
    }

    setErrors(errors);

    return isValid;
  };

  const handleUpload = async (file) => {
    const formData = new FormData();
    formData.append("file", file.originFileObj);
    const res = await uploadFile(formData);
    switch (res.data.statusCode) {
      case "200":
        setIdFile2(res.data.data.fileDataId);
        break;

      default:
        break;
    }
  };
  const handleSubmit = async () => {
    const isValid = validateForm();

    if (!isValid) {
      return;
    }

    const data = {
      name: form.name,
      description: form.description,
      topic: form.topic,
      location: form.location,
      timeStart: form.time[0] + ":00",
      timeEnd: form.time[1] + ":00",
      score: form.score,
      fileId: idFile2 ? idFile2 : null,
    };
    try {
      const res = await editWorkshopServie(data, id);
      switch (res.data.statusCode) {
        case "200":
          toast.success("Update Success");
          break;

        default:
          break;
      }
    } catch (error) {}
  };

  return (
    <>
      {!attendance ? (
        <CustomLayout>
          <div
            className="text-xl cursor-pointer "
            onClick={() => router.push("/admin/workshops")}
          >
            <ArrowLeftOutlined />
          </div>
          <div className="flex justify-between items-center my-8">
            <div className="text-2xl font-medium">
              <span className="font-bold">Update Workshop:</span> {form.name}
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
                  Name <span className="text-red-500">*</span>
                  {nameErr && <span className="text-red-500">{nameErr}</span>}
                </div>
                <Input
                  placeholder=""
                  required
                  value={name}
                  onChange={(e) =>
                    setForm({
                      ...form,
                      name: e.target.value,
                    })
                  }
                />
              </div>
              <div>
                <div className="text-xs font-semibold mb-2">
                  Topic <span className="text-red-500">*</span>
                  {topicErr && <span className="text-red-500">{topicErr}</span>}
                </div>
                <Input
                  placeholder=""
                  required
                  value={topic}
                  onChange={(e) =>
                    setForm({
                      ...form,
                      topic: e.target.value,
                    })
                  }
                />
              </div>
              <div>
                <div className="text-xs font-semibold mb-2">
                  Location <span className="text-red-500">*</span>
                  {locationErr && (
                    <span className="text-red-500">{locationErr}</span>
                  )}
                </div>
                <Input
                  placeholder=""
                  required
                  value={location}
                  onChange={(e) =>
                    setForm({
                      ...form,
                      location: e.target.value,
                    })
                  }
                />
              </div>
              <div>
                <div className="text-xs font-semibold mb-2">
                  Date From - Date To <span className="text-red-500">*</span>
                  {timeErr && <span className="text-red-500">{timeErr}</span>}
                </div>
                {time.length > 0 && (
                  <RangePicker
                    defaultValue={[
                      dayjs(time[0], "YYYY-MM-DD HH:mm"),
                      dayjs(time[1], "YYYY-MM-DD HH:mm"),

                    ]}
                    onChange={handleDateTimeChange}
                    showTime={{
                      hideDisabledOptions: true,
                      defaultValue: [
                        dayjs("00:00:00", "HH:mm:ss"),
                        dayjs("11:59:59", "HH:mm:ss"),
                      ],
                    }}
                    format="YYYY-MM-DD HH:mm"
                  />
                  // <RangePicker
                  //   defaultValue={[
                  //     dayjs(time[0], dateFormat),
                  //     dayjs(time[1], dateFormat),
                  //   ]}
                  //   className="w-full"
                  //   format={dateFormat}
                  //   locale={locale}
                  //   placeholder={["from", "to"]}
                  //   bordered={true}
                  //   onChange={handleRangeChange}
                  // />
                  // <RangePicker
                  //   showTime={{ format: "HH:mm" }}
                  //   format="YYYY-MM-DD HH:mm"
                  //   placeholder={["Start Time", "End Time"]}
                  //   onChange={handleDateTimeChange}
                  //   onOk={() => console.log(time)}
                  //   value={
                  //     time.length
                  //       ? [
                  //           moment(time[0], "YYYYMMDD HH:mm"),
                  //           moment(time[1], "YYYYMMDD HH:mm"),
                  //         ]
                  //       : [
                  //           moment("2023-03-23 12:31", "YYYY-MM-DD HH:mm"),
                  //           moment("2023-04-11 12:31", "YYYY-MM-DD HH:mm"),
                  //         ]
                  //   }
                  //   renderExtraFooter={() => (
                  //     <>
                  //       <TimePicker
                  //         defaultValue={moment("00:00", "HH:mm")}
                  //         format="HH:mm"
                  //       />
                  //       <span style={{ margin: "0 8px" }}>-</span>
                  //       <TimePicker
                  //         defaultValue={moment("23:59", "HH:mm")}
                  //         format="HH:mm"
                  //       />
                  //     </>
                  //   )}
                  // />
                  // <RangePicker
                  //   className="text-black"
                  //   showTime={{ format: "HH:mm" }}
                  //   format="YYYY-MM-DD HH:mm"
                  //   placeholder={["Start Time", "End Time"]}
                  //   onChange={handleDateTimeChange}
                  //   onOk={() => console.log(form.time)}
                  //   defaultValue={[
                  //     moment("2023-03-23 12:31", "YYYY-MM-DD HH:mm"),
                  //     moment("2023-04-11 12:31", "YYYY-MM-DD HH:mm"),
                  //   ]}
                  //   renderExtraFooter={() => (
                  //     <>
                  //       <TimePicker
                  //         defaultValue={moment("09:00", "HH:mm")}
                  //         format="HH:mm"
                  //       />
                  //       <span style={{ margin: "0 8px" }}>-</span>
                  //       <TimePicker
                  //         defaultValue={moment("10:00", "HH:mm")}
                  //         format="HH:mm"
                  //       />
                  //     </>
                  //   )}
                  // />
                )}
              </div>
              <div>
                <div className="text-xs font-semibold mb-2">
                  Score <span className="text-red-500">*</span>
                  {scoreErr && <span className="text-red-500">{scoreErr}</span>}
                </div>
                <Input
                  placeholder=""
                  required
                  value={score}
                  onChange={(e) =>
                    setForm({
                      ...form,
                      score: e.target.value,
                    })
                  }
                />
              </div>
              <div>
                <div className="text-xs font-semibold mb-2">Image</div>

                {form.idFile.length > 0 && (
                  <Upload
                    listType="picture"
                    maxCount={1}
                    onRemove={() => setIdFile2(null)}
                    defaultFileList={[...form.idFile]}
                    onChange={(info) => {
                      const { status, response } = info.file;
                      if (status === "done") {
                        handleUpload(info.file);
                        // Do something with the response data
                      } else if (status === "error") {
                        message.error(`${info.file.name} upload failed.`);
                        // Handle the error
                      }
                    }}
                  >
                    <Button icon={<UploadOutlined />}>Upload</Button>
                  </Upload>
                )}
              </div>
              {description && (
                <div className="col-span-2">
                  <div className="text-xs font-semibold mb-2">
                    Description <span className="text-red-500">*</span>
                    {descriptionErr && (
                      <span className="text-red-500">{descriptionErr}</span>
                    )}
                  </div>
                  <ReactQuill
                    theme="snow"
                    value={description}
                    onChange={handleChange}
                  />
                </div>
              )}
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
          {/* <div className="shadow rounded-lg bg-white my-12 ">
            <div className="flex justify-between items-center">
              <div className="px-8  py-4 font-bold">List user</div>
              <div
                onClick={() => handleExport()}
                className="bgPrimary px-6 w-32 flex justify-center items-center mr-12  py-2 h-10 text-white rounded-lg cursor-pointer"
              >
                Export file
              </div>
            </div>

            <Table
              columns={columns}
              dataSource={data}
              className="w-full"
            ></Table>
          </div> */}
        </CustomLayout>
      ) : (
        <CustomLayout>
          <div
            className="text-xl cursor-pointer "
            onClick={() => router.push("/admin/workshops")}
          >
            <ArrowLeftOutlined />
          </div>
          <div className="flex justify-between items-center mb-12">
            <div className="text-2xl font-medium"> {form.name}</div>
            {/* <div
              onClick={handleSubmit}
              className="bgPrimary px-6 py-2 text-white rounded-lg cursor-pointer"
            >
              Save
            </div> */}
          </div>

          <div className="shadow rounded-lg bg-white my-12 ">
            <div className="flex justify-between items-center">
              <div className="px-8  py-4 font-bold">List user</div>
              <div
                onClick={() => handleExport()}
                className="bgPrimary px-6 w-32 flex justify-center items-center mr-12  py-2 h-10 text-white rounded-lg cursor-pointer"
              >
                Export file
              </div>
            </div>

            <Table
              columns={columns}
              dataSource={data}
              className="w-full"
            ></Table>
          </div>
        </CustomLayout>
      )}
    </>
  );
}
