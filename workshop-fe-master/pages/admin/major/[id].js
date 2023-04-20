import CustomLayout from "@/layouts/LayoutAnt";
import {
  editMajorServie,
  getDetailMajorService,
} from "@/services/adminService";
import { Input } from "antd";
import { ArrowLeftOutlined } from "@ant-design/icons";

import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
const { TextArea } = Input;
export default function CreateMajor() {
  const [name, setName] = useState("");
  const router = useRouter();
  const [nameErr, setNameErr] = useState("");
  const [create, setCreate] = useState({
    name: "",
    time: "",
  });
  const { id } = router.query;

  const validateName = (name) => {
    if (!name || name.trim() === "") {
      return "Name is required";
    }
    return "";
  };
  const handleSubmit = async () => {
    let data = {
      name: name,
    };
    try {
      const nameError = validateName(name);
      if (nameError) {
        setNameErr(nameError);
        return;
      }
      const res = await editMajorServie(data, id);
      switch (res.data.statusCode) {
        case "200":
          toast.success("Update success");
          break;

        default:
          break;
      }
    } catch (error) {}
  };
  const fetchDetail = async (id) => {
    try {
      const res = await getDetailMajorService(id);
      switch (res.data.statusCode) {
        case "200":
          let data = res.data.data;
          setName(data.name);
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
        <div
          className="text-xl cursor-pointer "
          onClick={() => router.push("/admin/major")}
        >
          <ArrowLeftOutlined />
        </div>
        <div className="flex justify-between items-center my-8">
          <div className="text-2xl font-medium"><span className="font-bold">Update Major:</span> {name}</div>
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
          </div>
          
        
        </div>
      </CustomLayout>
    </>
  );
}
