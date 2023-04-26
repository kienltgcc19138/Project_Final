import LoginLayout from "@/layouts/LoginLayout";
import EventDetail from "@/components/events/EventDetail";
import Head from "next/head";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { getDetailWorkshopService } from "@/services/adminService";
import Cookies from "js-cookie";

export default function WorkshopDetails() {
  const [detail, setDetail] = useState(null);
  const [userRole, setUserRole] = useState(Cookies.get("role") === "ROLE_USER");

  const router = useRouter();
  const { id } = router.query;
  const fetchDetail = async (id) => {
    try {
      const res = await getDetailWorkshopService(id);
      switch (res.data.statusCode) {
        case "200":
          setDetail(res.data.data);

          break;

        default:
          break;
      }
    } catch (error) {}
  };
  useEffect(() => {
    if (id) {
      fetchDetail(id);
    }
  }, [id]);
  return (
    <>
      <Head>
        <title> Event </title>
      </Head>
      <LoginLayout>
        {detail && <EventDetail userRole={userRole} detail={detail} id={id} />}
      </LoginLayout>
    </>
  );
}
