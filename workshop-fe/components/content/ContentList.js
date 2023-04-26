import React, { useState, useEffect } from "react";
import Link from "next/link";
import { withErrorBoundary } from "react-error-boundary";

const ContentList = ({ data }) => {
  return (
    <div
      className={`bgPrimary container mx-auto w-full flex odd:flex-row-reverse py-4 flex-wrap md:flex-nowrap  `}
    >
      <div className="w-full md:w-[52%] relative ">
        <img
          className={`${data.id === 1 ? "mt-[-40px] " : ""}   ${
            data.id === 3 ? "mb-[-40px] " : ""
          } `}
          src={data.img.src}
          alt=""
        />
        <div
          className={`${data.id === 1 ? "mt-[-30px] " : ""}  ${
            data.id === 3 ? "mb-[-20px] " : ""
          }  absolute z-10 md:w-[92%] h-[98%] top-[1%] right-[4%] left-[4%] bottom-[1%] border  `}
        ></div>
      </div>
      <div className=" w-full pl-4 pr-5 md:w-[48%] mx-8 ">
        <div className="text-white my-2 text-4xl font-bold">{data.title}</div>
        <div className="bgPrimary2 h-1 w-20 my-4"></div>  
        <div className="text-white leading-6 text-[18px]">{data.content}</div>
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

export default withErrorBoundary(ContentList, {
  FallbackComponent: ErrorComponent,
});
