import BannerList from "@/components/banner/BannerList";
import LoginLayout from "@/layouts/LoginLayout";
import EventList from "@/components/events/EventList";

export default function Workshop() {
  return (
    <>
      <LoginLayout>
        <BannerList />
        <div className="shadow py-4">
          <div className="container mx-auto flex items-center justify-between">
            <div className="textPrimary font-bold text-xl"> Search Workshop</div>
            <form className="flex items-center">
              <div className="mr-2">
                <label className="p-2 text-gray-500 font-bold" htmlFor="checkin">
                  Date from
                </label>
                <input
                  className="border cursor-pointer  rounded py-2 px-3 leading-tight focus:outline-none focus:shadow-outline"
                  id="checkin"
                  type="date"
                />
              </div>
              <div className="mr-2">
                <label className="p-2 text-gray-500 font-bold" htmlFor="checkout">
                 Date to
                </label>
                <input
                  className="border cursor-pointer  rounded py-2 px-3 leading-tight focus:outline-none focus:shadow-outline"
                  id="checkout"
                  type="date"
                />
              </div>
              <button
                className="bgPrimary hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                type="button"
              >
                Search
              </button>
            </form>
          </div>
        </div>
        <EventList/>
      </LoginLayout>
    </>
  );
}
