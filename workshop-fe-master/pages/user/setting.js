import ChangePass from "@/components/account/ChangePass";
import Profile from "@/components/account/Profile";
import LoginLayout from "@/layouts/LoginLayout";
import { Menu, Tab, Transition } from "@headlessui/react";
import Cookies from "js-cookie";
import { useRouter } from "next/router";
import { Fragment, useEffect, useRef, useState } from "react";

export default function Setting() {
  const [activeTab, setActiveTab] = useState(0);
  const router = useRouter()
  if (!Cookies.get("accessToken")  && process.browser) {
    router.push("/login");
  }

  return (
    <LoginLayout>
      <div className="container mx-auto my-40">
        <div className="flex justify-between mb-12">
          <div className="mx-2">
            <div className="text-4xl textPrimary2 font-bold ">
              Account Settings
            </div>
            <div className="bgPrimary2 h-1 w-20 my-4"></div>
          </div>
        </div>
        <Tab.Group>
          <Tab.List className="flex border-b border-blue-200 gap-2">
            <Tab
              className={`px-4 py-1.5 font-medium textPrimary2 ${
                activeTab === 0 ? "border-b-2 border-orange-600 textLink" : ""
              }`}
              selected={activeTab === 0}
              onClick={() => setActiveTab(0)}
            >
              Profile
            </Tab>
            <Tab
              className={`px-4 py-1.5 font-medium textPrimary2 ${
                activeTab === 1 ? "border-b-2 border-orange-600 textLink" : ""
              }`}
              selected={activeTab === 1}
              onClick={() => setActiveTab(1)}
            >
              Change Password
            </Tab>
          </Tab.List>
          <Tab.Panels>
            <Tab.Panel className={`py-12`}>
              <Profile />
            </Tab.Panel>
            <Tab.Panel className={`py-12`}>
              <ChangePass />
            </Tab.Panel>
          </Tab.Panels>
        </Tab.Group>
      </div>
    </LoginLayout>
  );
}
