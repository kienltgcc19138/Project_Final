import StudentList from "@/components/admin/student/StudentList";
import CustomLayout from "@/layouts/LayoutAnt";



export default function Student() {
  return (
    <>
        <CustomLayout>
          <StudentList/>
       </CustomLayout>
    </>
  );
}
