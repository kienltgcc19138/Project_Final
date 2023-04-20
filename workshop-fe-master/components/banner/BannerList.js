import React, { useState, useEffect, useRef } from "react";
import Link from "next/link";
import { withErrorBoundary } from "react-error-boundary";
import { Swiper, SwiperSlide } from "swiper/react";
import banner1 from "assets/images/welcome2.jpg";
import banner2 from "assets/images/web.png";
import banner3 from "assets/images/metaverse.jpg";
import { AiOutlineRight, AiOutlineLeft } from "react-icons/ai";

// Import Swiper styles
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";

// import required modules
import { Navigation, Pagination, Mousewheel, Keyboard,Autoplay } from "swiper";

const BannnerList = () => {
  const prevRef = useRef(null);
  const nextRef = useRef(null);
  return (
    <Swiper
      cssMode={true}
      autoplay={{
        delay: 8000,

        disableOnInteraction: false,
      }}
      loop={true}
      pagination={true}
      mousewheel={true}
      onInit={(swiper) => {
        swiper.params.navigation.prevEl = prevRef.current;
        swiper.params.navigation.nextEl = nextRef.current;
        swiper.navigation.init();
        swiper.navigation.update();
      }}
      keyboard={true}
      modules={[Navigation, Pagination, Mousewheel, Keyboard,Autoplay]}
      className="mySwiper md:absolute  z-0 "
    >
      <SwiperSlide>
        <img className="h-[210px] md:h-[598px]" src={banner1.src} alt="" />
      </SwiperSlide>
      <SwiperSlide>
        <img className= " h-[210px] md:h-[598px]" src={banner2.src} alt="" />
      </SwiperSlide>
      <SwiperSlide>
        <img className="h-[210px] md:h-[598px]" src={banner3.src} alt="" />
      </SwiperSlide>
      <div
        className="flex justify-between absolute top-[60%] bottom-0 z-30 w-full px-[2%]
      "
      >
        <div
          ref={prevRef}
          className="bg-gray-500 rounded-full w-8 h-8 flex items-center justify-center"
        >
          <AiOutlineLeft className="text-white cursor-pointer" />
        </div>
        <div
          ref={nextRef}
          className="bg-gray-500 cursor-pointer rounded-full w-8 h-8 flex items-center justify-center"
        >
          <AiOutlineRight className="text-white" />
        </div>
      </div>
    </Swiper>
  );
};

function ErrorComponent({ error }) {
  return (
    <div className="p-4 text-red-500 border border-red-500">
      <span>{error.message}</span>
    </div>
  );
}

export default withErrorBoundary(BannnerList, {
  FallbackComponent: ErrorComponent,
});
