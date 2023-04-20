import React, { useState, useEffect } from "react";
import Link from "next/link";
import { useRouter } from "next/router";
import { withErrorBoundary } from "react-error-boundary";
import { topMenu, navBar } from "utils/data";
import logo from "assets/images/logo.png";
const Header = () => {
  return (
    <header className="relative z-10 ">
      <div className="h-[32px] hidden bgPrimary w-full md:flex justify-end  text-sm items-center   ">
        {topMenu?.map((item, index) => {
          return (
            <Link
              className="text-white mr-4 hover:textLink "
              key={index}
              href={item.link}
            >
              {item?.title}
            </Link>
          );
        })}
      </div>
      <div className="w-full bg-white">
        <div className="h-[102px] bg-white container mx-auto flex justify-between items-center">
          <div>
            <Link className="text-white mr-4 hover:textLink cursor-pointer " href="/">
              <img src={logo.src} alt="" className="w-[185px] cursor-pointer" />
            </Link>
          </div>
          <div className="hidden md:block">
            {navBar?.map((item, index) => {
              return (
                <Link
                  className="text-[#7a7a7a] px-5  font-medium mr-4 hover:textLink cursor-pointer "
                  key={index}
                  href={item.href}
                >
                  {item?.title}
                </Link>
              );
            })}
          </div>
        </div>
      </div>
    </header>
  );
};

function ErrorComponent({ error }) {
  return (
    <div className="p-4 text-red-500 border border-red-500">
      <span>{error.message}</span>
    </div>
  );
}

export default withErrorBoundary(Header, {
  FallbackComponent: ErrorComponent,
});
