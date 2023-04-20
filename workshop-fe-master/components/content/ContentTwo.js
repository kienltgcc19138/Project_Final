import React, { useState, useEffect } from "react";
import Link from "next/link";
import { withErrorBoundary } from "react-error-boundary";
import img from "assets/images/alumni.jpg";
import Image from "next/image";
const ContentTwo = ({}) => {
  return (
    <div className="mt-12 container mx-auto">
      <div className="flex justify-between mb-12">
        <div className="mx-2">
          <div className="text-4xl textPrimary2 font-bold " >Alumni</div>
          <div className="bgPrimary2 h-1 w-20 my-4"></div>
        </div>
        <div className="flex justify-end textLink font-medium text-[18px] cursor-pointer h-12 ">
          <div className="px-10 py-3 hover:bg-gray-200 rounded-lg hover:textPrimary2">
            Find out more
          </div>
        </div>
      </div>
      <div className="flex relative justify-end flex-wrap md:flex-nowrap  ">
        <div className="mt-20  relative md:absolute z-10 left-0 w-full md:w-[52%]">
          <img src={img.src} alt="" />
        </div>
        <div className="bg-[#edf2f8] w-full md:w-[70%] pl-8 md:pl-[25%] pt-12 ">
          <div className="textPrimary2 text-xl py-4">
            Carry on the pride of Greenwich Alumni
          </div>
          <div className=" text-black text-lg w-[80%] mb-32">
            Greenwich Vietnam alumni network has over 15,000 students from 12
            countries and is part of the global network of more than 30,000
            students based in more than 30 countries including the United
            Kingdom. Together, we carry on the pride of Greenwich Alumni.
          </div>
        </div>
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

export default withErrorBoundary(ContentTwo, {
  FallbackComponent: ErrorComponent,
});
