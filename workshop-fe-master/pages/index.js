import BannerList from "@/components/banner/BannerList";
import { rank, dataContent } from "utils/data";
import ContentList from "@/components/content/ContentList";
import Talk from "@/components/talks/Talk";
import EventCom from "@/components/events/EventCom";
import Socials from "@/components/socials/Socials";
import LoginLayout from "@/layouts/LoginLayout";
import EventList from "@/components/events/EventList";
import Head from "next/head";
import { DatePicker, Space } from "antd";
import { useState } from "react";

const { RangePicker } = DatePicker;

export default function Home() {
  const [form, setForm] = useState({
    time: [],
  });
  const dateFormat = "YYYY-MM-DD HH:mm:ss";
  const handleRangeChange = (dates, dateStrings) => {
    setForm({ ...form, time: dateStrings });
  };

  return (
    <>
      <Head>
        <title> Home</title>
      </Head>
      <LoginLayout>
        <BannerList />
        <div className="shadow py-4">
          <div className="container mx-auto flex items-center justify-between flex-wrap gap-2">
            <div className="textPrimary font-bold md:text-xl ml-2">
              Search Event
            </div>
            <form className="flex items-center justify-between gap-8">
              <div className="flex flex-col">
                {/* <div className="text-xs font-semibold mb-2">
                  Date From - Date To
                </div> */}
                <RangePicker
                  className="w-full"
                  format={dateFormat}
                  placeholder={["from", "to"]}
                  bordered={true}
                  onChange={handleRangeChange}
                />
              </div>
              {/* <button
                className="bgPrimary hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                type="button"
              >
                Search
              </button> */}
            </form>
          </div>
        </div>
        <EventList time={form.time} />
        <div className="mt-20">
          {dataContent?.map((item, index) => {
            return <ContentList data={item} key={index} />;
          })}
        </div>

        <EventCom />
        <Socials />
      </LoginLayout>
      {/* <Layout>
        <BannerList />
        <div className="container mx-auto mb-20 p-6 grid grid-cols-1 md:grid-cols-5 ">
          <div className="col-span-2 pr-16 pl-2">
            <YoutubeEmbed embedId="3ckmZttnOFo" />
          </div>
          <div className="col-span-3 pt-8 pr-0 pb-4 pl-12">
            {rank?.map((item, index) => {
              return (
                <div className="flex items-center gap-4 mb-8" key={index}>
                  <div className="w-8 h-8 bgPrimary2 rounded-full flex justify-center items-center"><FaTrophy className="text-white"/> </div>
                  <span className="text-[#2D3791] font-medium">{item}</span>
                </div>
              );
            })}
            <div className="flex justify-end text-[#2D3791] font-medium text-[18px] cursor-pointer ">
              <div className="px-10 py-3 hover:bgPrimary2 rounded-lg hover:text-white">
                Find out more
              </div>
            </div>
          </div>
        </div>
        {dataContent?.map((item, index) => {
          return <ContentList data={item} key={index} />;
        })}
        <ContentTwo />
        <Talk />
        <EventCom />
        <Socials />
      </Layout> */}
    </>
  );
}
