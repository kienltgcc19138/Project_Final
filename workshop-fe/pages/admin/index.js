
import CustomLayout from "@/layouts/LayoutAnt";
import Table from "@/components/Table";
import { useEffect } from "react";
import { useRouter } from "next/router";

export default function Admin() {
  const router = useRouter();
  useEffect(() => {
    router.push('admin/student');
  })
  return (
    <>
        <CustomLayout>
       </CustomLayout>
    </>
  );
}
