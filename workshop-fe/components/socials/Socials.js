import React, { useState, useEffect } from "react";
import Link from "next/link";
import { withErrorBoundary } from "react-error-boundary";

import { AiFillFacebook, AiFillInstagram, AiFillYoutube } from "react-icons/ai";
import { FaTiktok } from "react-icons/fa";

const Socials = ({}) => {
  return (
    <div className="border-t container md:mx-auto  border-black w-full flex justify-center h-24 gap-16 items-center font-bold textPrimary2">
      <div className="flex flex-col justify-center items-center cursor-pointer w-[40%] md:w-[6%] py-2 rounded-lg hover:bgPrimary2 hover:text-white">
        <AiFillFacebook size={"2em"} />
        FaceBook
      </div>
      <div className="flex flex-col justify-center items-center cursor-pointer w-[40%] md:w-[6%] py-2 rounded-lg hover:bgPrimary2 hover:text-white">
        <FaTiktok size={"2em"} />
        Tiktok
      </div>
      <div className="flex flex-col justify-center items-center cursor-pointer w-[40%] md:w-[6%] py-2 rounded-lg hover:bgPrimary2 hover:text-white">
        <AiFillInstagram size={"2em"} />
        Instagram
      </div>
      <div className="flex flex-col justify-center items-center cursor-pointer w-[40%] md:w-[6%] py-2 rounded-lg hover:bgPrimary2 hover:text-white">
        <AiFillYoutube size={"2em"} />
        YouTube
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

export default withErrorBoundary(Socials, {
  FallbackComponent: ErrorComponent,
});
