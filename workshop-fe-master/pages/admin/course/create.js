import CustomLayout from "@/layouts/LayoutAnt";
import { addCourseService } from "@/services/adminService";
import { Input } from "antd";
import { useRouter } from "next/router";
import { useState } from "react";
import { ArrowLeftOutlined } from "@ant-design/icons";
const { TextArea } = Input;
export default function CreateMajor() {
  const [name, setName] = useState("");
  const [nameErr, setNameErr] = useState("");
  // const [description, setDescription] = useState("");
  const router = useRouter();

  const handleSubmit = async () => {
    let data = {
      name: name,
      // description: description,
    };
    try {
      const nameError = validateName(name);
      if (nameError) {
        setNameErr(nameError);
        // Set an error state variable, show an error message, or handle the error in some other way
        return;
      }
      const res = await addCourseService(data);
      console.log(res.data.statusCode);
      switch (res.data.statusCode) {
        case "200":
          router.push("/admin/course/" + res.data.data.courseId);
          break;

        default:
          break;
      }
    } catch (error) {}
  };
  const validateName = (name) => {
    if (!name || name.trim() === "") {
      return "Name is required";
    }
    return "";
  };
  return (
    <>
      <CustomLayout>
        <div className="text-xl cursor-pointer " onClick={() => router.back()}>
          <ArrowLeftOutlined />
        </div>
        <div className="flex justify-between items-center mb-12">
          <div className="text-2xl font-medium"> Create Course</div>
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
                <span className="text-red-500">{nameErr}</span>
              </div>
              <Input
                placeholder=""
                required
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </div>
            {/* <div>
              <div className="text-xs font-semibold mb-2">Description </div>
              <TextArea
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                rows={4}
              />
            </div> */}
          </div>
          
        </div>
      </CustomLayout>
    </>
  );
}
