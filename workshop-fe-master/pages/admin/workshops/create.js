import CustomLayout from "@/layouts/LayoutAnt";
import { addWorkshopServie } from "@/services/adminService";
import { Input } from "antd";
import { useRouter } from "next/router";
import { ArrowLeftOutlined } from "@ant-design/icons";
import { useState } from "react";
// import { DatePicker, Space } from "antd";
import { UploadOutlined } from "@ant-design/icons";
import { Button, Upload } from "antd";
import { uploadFile } from "@/services/uploadService";
import dynamic from "next/dynamic";
const ReactQuill = dynamic(() => import("react-quill"), { ssr: false });
import "react-quill/dist/quill.snow.css";

// const { RangePicker } = DatePicker;
import locale from "antd/lib/locale/vi_VN";
import { DatePicker, TimePicker } from "antd";
import moment from "moment";
import { toast } from "react-toastify";

const { RangePicker } = DatePicker;

export default function CreateWorkshop() {
  const [idFile, setIdFile] = useState(null);
  const [form, setForm] = useState({
    name: "",
    topic: "",
    location: "",
    time: [],
    score: "",
    description: "",
  });

  const [errors, setErrors] = useState({
    name: "",
    topic: "",
    location: "",
    time: [],
    score: "",
    description: "",
  });
  function handleChange(html) {
    setForm({
      ...form,
      description: html,
    });
  }
  const { name, topic, location, time, score, description } = form;
  const {
    name: nameErr,
    topic: topicErr,
    location: locationErr,
    time: timeErr,
    score: scoreErr,
    description: descriptionErr,
  } = errors;
  const [dateTimeRange, setDateTimeRange] = useState([]);

  const handleDateTimeChange = (value, dateString) => {
    console.log(dateString);
    setForm({ ...form, time: dateString });
  };

  const formatDate = (value) => {
    return moment(value).format("YYYY-MM-DD HH:mm");
  };
  const router = useRouter();
  const dateFormat = "YYYY-MM-DD HH:mm";
  const handleRangeChange = (dates, dateStrings) => {
    setForm({ ...form, time: dateStrings });
  };

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

    if (!form.score.trim()) {
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
        setIdFile(res.data.data.fileDataId);
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
      fileId: idFile ? idFile : null,
    };
    try {
      const res = await addWorkshopServie(data);
      switch (res.data.statusCode) {
        case "200":
          router.push("/admin/workshops/" + res.data.data.eventId);
          break;

        default:
          break;
      }
    } catch (error) {
      console.log(error);
      if (error.response.status == 422) {
        var str = error.response.data.errorCode;
        str = str.replace(/_/g, " ");
        toast.error(str);
      }
    }
  };

  return (
    <>
      <CustomLayout>
        <div className="text-xl cursor-pointer " onClick={() => router.back()}>
          <ArrowLeftOutlined />
        </div>
        <div className="flex justify-between items-center mb-12">
          <div className="text-2xl font-medium"> Create Event</div>
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
              {/* <RangePicker
                className="w-full"
                format={dateFormat}
                locale={locale}
                placeholder={["from", "to"]}
                bordered={true}
                onChange={handleRangeChange}
              /> */}
              <RangePicker
                className="text-black"
                showTime={{ format: "HH:mm" }}
                format="YYYY-MM-DD HH:mm"
                placeholder={["Start Time", "End Time"]}
                onChange={handleDateTimeChange}
                onOk={() => console.log(dateTimeRange)}
                // value={
                //   dateTimeRange.length
                //     ? [
                //         moment(dateTimeRange[0], "YYYY-MM-DD HH:mm"),
                //         moment(dateTimeRange[1], "YYYY-MM-DD HH:mm"),
                //       ]
                //     : []
                // }
                renderExtraFooter={() => (
                  <>
                    <TimePicker
                      defaultValue={moment("09:00", "HH:mm")}
                      format="HH:mm"
                    />
                    <span style={{ margin: "0 8px" }}>-</span>
                    <TimePicker
                      defaultValue={moment("10:00", "HH:mm")}
                      format="HH:mm"
                    />
                  </>
                )}
              />
              {/* <div>
                Selected Range: {dateTimeRange.map(formatDate).join(" ~ ")}
              </div> */}
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

              <Upload
                listType="picture"
                maxCount={1}
                onChange={(info) => {
                  const { status, response } = info.file;
                  if (status === "done") {
                    console.log(`${info.file} uploaded successfully`);
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
            </div>
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
          </div>
          {/* <div className="shadow rounded-lg bg-white p-3">
            <div className="w-full font-semibold border-b p-2">Information</div>
            <div className="flex justify-between px-3 py-2">
              <div className="text-sm font-semibold  ">Created</div>
              <div className="text-sm">now</div>
            </div>
            <div className="flex justify-between px-3 py-2">
              <div className="text-sm font-semibold  ">By</div>
              <div className="text-sm">-</div>
            </div>
            <div className="flex justify-between px-3 py-2">
              <div className="text-sm font-semibold  ">Last update</div>
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
