import React, { useState, useEffect } from "react";
import Link from "next/link";
import { withErrorBoundary } from "react-error-boundary";
import img from "assets/images/jane.jpg";
import Image from "next/image";
import { fetchWorkshopService } from "@/services/adminService";
import { useRouter } from "next/router";
// const dataEvents = [1,2,3,4,5,6]

const EventCom = ({}) => {
  const [dataEvents, setDataEvents] = useState([]);
  const router = useRouter();

  const handleFormatDay = (time) => {
    const currentDate = new Date(time);
    const day = currentDate.getDate();
    const month = currentDate.toLocaleString("en-US", { month: "long" });
    return [day, month];
  };

  const fetchData = async () => {
    const data = {
      pageNumber: 0,
      pageSize: 6,
      keyword: "",
    };
    console.log("fetch data event index");
    try {
      const res = await fetchWorkshopService(data);
      switch (res.data.statusCode) {
        case "200":
          setDataEvents(res.data.data.content);
          console.log(res.data.data.content);
          break;
        default:
          break;
      }
    } catch (error) {
      console.log("err" + error);
    }
  };
  useEffect(() => {
    fetchData();
  }, []);
  return (
    <div className="mt-12 container mx-auto">
      <div className="flex justify-between mb-12">
        <div className="mx-2">
          <div className="text-4xl textPrimary2 font-bold ">Events</div>
          <div className="bgPrimary2 h-1 w-20 my-4"></div>
        </div>
        <div className="flex justify-end textLink font-medium text-[18px] cursor-pointer h-12 ">
          {/* <div className="px-10 py-3 hover:bg-gray-200 rounded-lg hover:textPrimary2">
            Find out more
          </div> */}
        </div>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4 md:gap-12">
        {dataEvents.map((item, index) => {
          return (
            <div
              onClick={() => router.push("/workshop/" + item.eventId)}
              className="flex gap-4 mx-4 cursor-pointer"
              key={index}
            >
              <div>
                <div className="textLink text-3xl font-bold">
                  {handleFormatDay(item.timeStart)[0]}
                </div>
                <div className="text-black font-bold">
                  {handleFormatDay(item.timeStart)[1]}
                </div>
                <div className="bgPrimary2 h-1 w-full my-4"></div>
              </div>
              <div className="flex flex-col justify-between pb-4">
                <div className="text-[#161616] text-lg font-bold">
                  {item.name}
                </div>
                <div className="textLink text-sm">{item.timeStart}</div>
                <div className="textLink text-sm">{item.timeEnd}</div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

function ErrorComponent({ error }) {
  return (
    <div className="p-4 text-red-500 border border-red-500">
      <span>{error.message}</span>
    </div>
  );
}

export default withErrorBoundary(EventCom, {
  FallbackComponent: ErrorComponent,
});
