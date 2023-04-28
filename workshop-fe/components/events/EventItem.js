import React, { useState, useEffect } from "react";
import Link from "next/link";
import { withErrorBoundary } from "react-error-boundary";
import {
  AiOutlineCalendar,
  AiOutlineEnvironment,
  AiOutlineTeam,
  AiOutlineDoubleRight,
} from "react-icons/ai";
import { fetchUserHistoryService, regEvent } from "@/services/userService";
import { toast } from "react-toastify";
import Cookies from "js-cookie";
import ModalRegister from "../modal/ModalRegister";
import { useRouter } from "next/router";
const EventItem = ({ handleRegEvent, data, refresh, check }) => {
  const [isRegister, setIsRegister] = useState(true);
  const router = useRouter();
  const handleFormatDay = (time) => {
    const inputDate = new Date(time);
    const formattedDate = inputDate.toLocaleString("en-US", {
      hour: "numeric",
      minute: "numeric",
      day: "numeric",
      month: "long",
      year: "numeric",
    });
    return formattedDate;
  };

  // You can then use the variables as needed
  // console.log(`Today is ${month} ${day}`); // Output: "Today is March 6"

  const fetchData = async (id) => {
    const data = {
      pageNumber: 0,
      pageSize: 10,
      usersId: Cookies.get("userId"),
      eventId: id,
    };
    try {
      const res = await fetchUserHistoryService(data);
      switch (res.data.statusCode) {
        case "200":
          if (res.data.data.content?.length == 0) {
            setIsRegister(false);
          }
          refresh();
          break;
        default:
          break;
      }
    } catch (error) {
      console.log("err" + error);
    }
  };
  useEffect(() => {
    if (data && Cookies.get("userId")) {
      fetchData(data?.eventId);
    }
  }, []);

  return (
    <>
      <div className="w-full shadow rounded mb-1 h-full">
        <Link href={`/workshop/${data.eventId}`}>
          <img
            src={data.fileDataResponses[0]?.link}
            className="h-40 object-cover"
          />
        </Link>

        <div className=" py-4 px-2">
          <Link href={`/workshop/${data.eventId}`}>
            <div className="textPrimary2 text-left font-medium line-clamp-2 mb-2">
              {data.name}
            </div>
          </Link>
          <div className="flex items-start gap-2 mb-2 textPrimary2 text-sm ">
            <AiOutlineCalendar />
            <div className=" flex flex-col justify-start items-start">
              <div className="">
                <span className="font-bold">Time start:</span>{" "}
                {handleFormatDay(data.timeStart)}
              </div>
              <div className="">
                <span className="font-bold">Time end:</span>{" "}
                {handleFormatDay(data.timeEnd)}
              </div>
            </div>
          </div>
          <div className="flex gap-4 justify-between items-center">
            <div className="flex items-center gap-1 textPrimary2 text-sm">
              <AiOutlineEnvironment />
              <span>{data.location}</span>
            </div>
          </div>
          <div className="flex items-center gap-1 textPrimary2 text-sm">
            Score:
            <span>{data.score}</span>
          </div>
          <div className="flex justify-between mt-4 items-center  text-base flex-wrap">
            {Cookies.get("role") === "ROLE_USER" && data.status == "UPCOMING"&& (
              <button
                disabled={isRegister}
                onClick={() => {
                  handleRegEvent(data.eventId, Cookies.get("userId"));
                }}
                className="flex justify-center disabled:bg-gray-500 items-center bgPrimary text-white border px-4 py-2 rounded "
              >
                {!isRegister ? "Register" : "Registered"}
              </button>
            )}
            {!Cookies.get("role") && (
              <button
                onClick={() => {
                  router.push("/login");
                }}
                className="flex justify-center disabled:bg-gray-500 items-center bgPrimary text-white border px-4 py-2 rounded "
              >
                {"Register"}
              </button>
            )}
            <Link
              href={`/workshop/${data.eventId}`}
              className="textPrimary2 hover:textLink flex items-center
        justify-center"
            >
              <span> More</span> <AiOutlineDoubleRight />
            </Link>
          </div>
        </div>
      </div>
    </>
  );
};

function ErrorComponent({ error }) {
  return (
    <div className="p-4 text-red-500 border border-red-500">
      <span>{error.message}</span>
    </div>
  );
}

export default withErrorBoundary(EventItem, {
  FallbackComponent: ErrorComponent,
});
