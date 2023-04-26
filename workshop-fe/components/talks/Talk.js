import React, { useState, useEffect } from "react";
import Link from "next/link";
import { withErrorBoundary } from "react-error-boundary";
import img from "assets/images/jane.jpg";
import Image from "next/image";

const Talk = ({}) => {
  return (
    <div className="flex container mx-auto justify-between mt-40 flex-wrap md:flex-nowrap flex-col-reverse md:flex-row">
      <div className="bgPrimary w-full md:w-[55%] my-8 ">
        <div className="text-white text-lg font-medium px-12 py-14 leading-7">
          “Students at the University of Greenwich will not only focus on the
          key theoretical and practical aspects of their studies but they will
          also develop a range of those general skills that are essential in any
          career. The ability to communicate well, to think critically, to work
          in a team and to solve problems are all characteristics of Greenwich
          students. These skills are increasingly in demand from employers. “
        </div>
        <div className="text-gray-400 text-end px-4">Professor Jane Harrington, Vice - Chancellor of the University of Greenwich, UK</div>
      </div>
      <div className="w-full md:w-[45%]">
        <img src={img.src} alt="" />
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

export default withErrorBoundary(Talk, {
  FallbackComponent: ErrorComponent,
});
