import CustomLayout from "@/layouts/LayoutAnt";
import { editCourseServie ,getDetailCourseService} from "@/services/adminService";
import { Input } from "antd";
import { ArrowLeftOutlined } from "@ant-design/icons";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
const { TextArea } = Input;
export default function CreateCourse() {
  const [name, setName] = useState("");
  // const [description, setDescription] = useState("");
  const router = useRouter();
  const [nameErr, setNameErr] = useState("");
  const [create, setCreate] = useState({
    name: "",
    time: "",
  });
  const {id} = router.query

  const validateName = (name) => {
    if (!name || name.trim() === "") {
      return "Name is required";
    }
    return "";
  };
  const handleSubmit = async () => {
    let data = {
      name: name,
      // description: description,
    };
    try {
      const nameError = validateName(name);
      if (nameError) {
        setNameErr(nameError)
        return;
      }
      const res = await editCourseServie(data,id);
      switch (res.data.statusCode) {
        case "200":
          toast.success("Update success")
          break;

        default:
          break;
      }
    } catch (error) {}
  };
  const fetchDetail = async (id) => {
    try {
      const res = await getDetailCourseService(id);
      switch (res.data.statusCode) {
        case "200":
          let data = res.data.data;
          setName(data.name)
          setDescription(data.description)
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
    }
  }, [id]);
  return (
    <>
      <CustomLayout>
      <div className="text-xl cursor-pointer " onClick={() => router.push('/admin/course')}>
          <ArrowLeftOutlined />
        </div>
        <div className="flex justify-between items-center my-8">
          <div className="text-2xl font-medium"> <span className="font-bold">Update Course:</span> {name}</div>
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
          {/* <div className="shadow rounded-lg bg-white p-3 h-40">
            <div className="w-full font-semibold border-b p-2">Information</div>
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
    </>
  );
}
