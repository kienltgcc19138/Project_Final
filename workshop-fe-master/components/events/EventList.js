import React, { useState, useEffect } from "react";
import Link from "next/link";
import { withErrorBoundary } from "react-error-boundary";
import event1 from "assets/images/event1.jpg";
import event2 from "assets/images/event2.png";
import { Swiper, SwiperSlide } from "swiper/react";

// Import Swiper styles
import "swiper/css";
import "swiper/css/pagination";
import { Navigation, Pagination } from "swiper";

import EventItem from "./EventItem";
import { fetchWorkshopService } from "@/services/adminService";
import Cookies from "js-cookie";
import { fetchUserHistoryService, regEvent } from "@/services/userService";
import ModalRegister from "../modal/ModalRegister";
import { toast } from "react-toastify";

const EventList = ({ time }) => {
  const [dataEvents, setDataEvents] = useState([]);
  const [check, setCheck] = useState("");
  const [data, setData] = useState(null);
  const [modal, setModal] = useState(false);
  const [eventId, setEventId] = useState("");
  const userId = Cookies.get("userId");

  const fetchData = async () => {
    const data = {
      pageNumber: 0,
      pageSize: 10,
      sort: [
        {
          key: "createdAt",
          asc: false,
        },
      ],
      status: time?.length > 0 ? null : "UPCOMING",
      fromStartDate: time?.length > 0 ? time[0] : null,
      toStartDate: time?.length > 0 ? time[1] : null,
    };
    try {
      const res = await fetchWorkshopService(data);
      switch (res.data.statusCode) {
        case "200":
          console.log(res.data.data.content);
          setDataEvents(res.data.data.content);
          break;
        default:
          break;
      }
    } catch (error) {
      console.log("err" + error);
    }
  };
  const handleRegEvent = async (eventId) => {
    setEventId(eventId);
    setModal(true);
  };
  const fetchData2 = async () => {
    const data = {
      pageNumber: 0,
      pageSize: 10,
      usersId: userId,
      eventId: null,
    };
    try {
      const res = await fetchUserHistoryService(data);
      switch (res.data.statusCode) {
        case "200":
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
  useEffect(() => {
    if (userId) {
      fetchData2();
    }
    fetchData({
      pageNumber: 0,
      pageSize: 10,
      keyword: "",
      fromStartDate: time ? time[0] : null,
      toStartDate: time ? time[1] : null,
    });
  }, [time]);

  return (
    <div className="mt-12 container mx-auto">
      <div className="flex justify-between mb-12">
        <div className="mx-2">
          <div className="md:text-4xl textPrimary2 font-bold ">
            Popular Events
          </div>
          <div className="bgPrimary2 h-1 w-20 my-4"></div>
        </div>
        {/* <div className="flex justify-end textLink font-medium md:text-[18px] cursor-pointer h-12 ">
          <div className="px-10 py-3 hover:bg-gray-200 rounded-lg hover:textPrimary2">
            Find out more
          </div>
        </div> */}
      </div>
      <div className=" mb-8 ">
        <Swiper
          slidesPerView={1}
          spaceBetween={10}
          navigation={true}
          style={{
            "--swiper-navigation-color": "#2D3791",
            "--swiper-navigation-size": "25px",
          }}
          breakpoints={{
            380: {
              slidesPerView: 2,
              spaceBetween: 20,
            },
            768: {
              slidesPerView: 3,
              spaceBetween: 20,
            },
            1024: {
              slidesPerView: 4,
              spaceBetween: 20,
            },
          }}
          modules={[Pagination, Navigation]}
          className="mySwiper mx-1"
        >
          {
            dataEvents==null || dataEvents.length==0 ? (
              <div className="text-center text-2xl font-bold text-gray-500">
                No data
              </div>
            ) : null
          }
          {dataEvents.map((item, index) => {
            return (
              <SwiperSlide key={index}>
                <EventItem
                  className="h-[100%]"
                  refresh={() =>
                    fetchData({
                      pageNumber: 0,
                      pageSize: 10,
                      keyword: "",
                    })
                  }
                  handleRegEvent={handleRegEvent}
                  data={item}
                />
              </SwiperSlide>
            );
          })}
        </Swiper>
      </div>
      {modal && (
        <ModalRegister
          show={modal}
          eventId={eventId}
          handleClose={() => {
            setDataEvents([])
            fetchData();
            setCheck(eventId);
            setModal(false);
          }}
        />
      )}
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

export default withErrorBoundary(EventList, {
  FallbackComponent: ErrorComponent,
});
