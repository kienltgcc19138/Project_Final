import React, { useState, useEffect } from "react";
import Link from "next/link";
import { useRouter } from "next/router";
import { withErrorBoundary } from "react-error-boundary";
const Footer = () => {
  return (
    <footer className="relative z-10">
      <div className="w-full bg-[#2455a6]">
        <div className="container mx-auto py-8  md:px-28 md:py-20 grid grid-cols-1 md:grid-cols-4 ">
          <div className="px-12">
            <div>
              <div className="text-xl text-white font-bold">About Us</div>
              <div className="bgPrimary2 h-1 w-20 my-4"></div>
            </div>
            <div className="grid grid-flow-row gap-4">
              <div className="text-[#DDDDDD] text-sm">
                Greenwich Vietnam is an international alliance between the
                University of Greenwich, United Kingdom and FPT University,
                Vietnam. Until now, it has had a global network of nearly 20.000
                students and alumni from over 12 countries in the world
              </div>
              <div className="text-[#DDDDDD] text-sm">
                With the internationally recognized Bachelor’s Degree from the
                University of Greenwich, United Kingdom, students can further
                study for a Master’s degree or PhD in many countries around the
                world. Contact Us
              </div>
            </div>
          </div>
          <div className="col-span-2 px-12">
            <div>
              <div className="text-xl text-white font-bold">Campuses</div>
              <div className="bgPrimary2 h-1 w-20 my-4"></div>
            </div>
            <div className="grid grid-cols-2 gap-8">
              <div className="grid grid-flow-row gap-4 ">
                <div className="text-white font-medium text-sm">HA NOI</div>
                <div className="text-[#DDDDDD] text-sm">
                  Golden Park Tower, 2 Pham Van Bach, Yen Hoa, Cau Giay, Hanoi
                  Telephone: 024.7300.2266 Hotline: 0971.274.545 | 0981558080
                </div>
              </div>
              <div className="grid grid-flow-row gap-8">
                <div className="text-white font-medium text-sm">
                  HO CHI MINH
                </div>
                <div className="text-[#DDDDDD] text-sm">
                  20 Cong Hoa, Ward 12, Tan Binh, HCMC
                  <br />
                  Telephone: 028.7300.2266
                  <br />
                  Hotline: 0933.108.554 | 0971.294.545
                </div>
              </div>
              <div className="grid grid-flow-row gap-4 ">
                <div className="text-white font-medium text-sm">DA NANG</div>
                <div className="text-[#DDDDDD] text-sm">
                  Location 1: 658 Ngo Quyen, Son Tra, Da Nang Location 2: FPT
                  Building, Road No.1, An Don Industrial Park, An Hai Bac, Son
                  Tra, Da Nang Telephone: 0236.730.2266 Hotline: 0934.892.687
                </div>
              </div>
              <div className="grid grid-flow-row gap-4">
                <div className="text-white font-medium text-sm">CAN THO</div>
                <div className="text-[#DDDDDD] text-sm">
                  160, 30/4 Street, An Phu, Ninh Kieu, Can Tho Telephone:
                  0292.730.0068 Hotline: 0968.670.804 | 0936.600.861
                </div>
              </div>
            </div>
          </div>
          <div className="px-12">
            <div>
              <div className="text-xl text-white font-bold">Fanpage</div>
              <div className="bgPrimary2 h-1 w-20 my-4"></div>
            </div>
            <div className="grid grid-flow-row gap-4">
              <iframe
                src="https://www.facebook.com/plugins/page.php?href=https%3A%2F%2Fwww.facebook.com%2FGreenwichVietnam&tabs&width=340&height=130&small_header=false&adapt_container_width=true&hide_cover=false&show_facepile=true&appId"
                width="340"
                height="130"
                // style={{ border: none, overflow: hidden }}
                frameBorder="0"
                allowFullScreen={true}
                allow="autoplay; clipboard-write; encrypted-media; picture-in-picture; web-share"
              ></iframe>
            </div>
          </div>
        </div>
      </div>
      <div className=" bgPrimary w-full flex justify-center  text-sm items-center p-5 text-white mr-4">
        © 2023 Greenwich.edu.vn. All Right Reserved
      </div>
    </footer>
  );
};

function ErrorComponent({ error }) {
  return (
    <div className="p-4 text-red-500 border border-red-500">
      <span>{error.message}</span>
    </div>
  );
}

export default withErrorBoundary(Footer, {
  FallbackComponent: ErrorComponent,
});
