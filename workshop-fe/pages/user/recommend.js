import RecommendList from "@/components/admin/recommend/RecommendList";
import LayoutDashBoardStudent from "@/layouts/LayoutDashBoardStudent";
import LoginLayout from "@/layouts/LoginLayout";

export default function RecommendStudent() {
  return (
    <>
      <LoginLayout>
        <div className="container mx-auto my-40">
          <div className="flex justify-between mb-12">
            <div className="mx-2 ">
              <div className="text-4xl textPrimary2 font-bold ">
              Recommend List
              </div>

              <div className="bgPrimary2 h-1 w-20 my-4"></div>
            </div>
            <div className="text-4xl textPrimary2 font-bold ">
             
            </div>
          </div>
          <div>
            <RecommendList />
          </div>
        </div>
      </LoginLayout>
    </>
  );
}
