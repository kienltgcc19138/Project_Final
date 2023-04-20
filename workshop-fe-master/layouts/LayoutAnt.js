import { Layout, Menu, ConfigProvider } from "antd";
import React, { useEffect, useState } from "react";
import { ToastContainer, toast } from 'react-toastify';
const { Header, Sider, Content, Footer } = Layout;
import {
  PieChartOutlined,
  DesktopOutlined,
  TeamOutlined,
  FileOutlined,
  DashboardOutlined,
  WalletOutlined, 
  UserOutlined,
  MailOutlined,
  MenuUnfoldOutlined,
  MenuFoldOutlined,
  ContactsOutlined,
  VideoCameraAddOutlined,
  MessageOutlined,
  SettingOutlined,
  EditOutlined,
} from "@ant-design/icons";

import logo from "assets/images/logo.png";
import mLogo from "assets/images/mini-logo.jpg";
import Link from "next/link";
import Cookies from "js-cookie";
import { useRouter } from "next/router";
import 'react-toastify/dist/ReactToastify.css';
function getItem(label, key, icon, children) {
  return {
    key,
    icon,
    children,
    label,
  };
}

const items = [
  getItem("Students", "/admin/student", <TeamOutlined />),
  getItem("Events", "/admin/workshops", <VideoCameraAddOutlined />),
  getItem("Major", "/admin/major", <WalletOutlined />),
  getItem("Course", "/admin/course",   <ContactsOutlined />),
  getItem("Recommend", "/admin/recommend",  <MailOutlined />),
  getItem("Chat", "/admin/chat",  <MessageOutlined />
  ),
];

const CustomLayout = ({ children }) => {
  const [collapsed, setCollapsed] = useState(false);
  const [fullName, setFullName] = useState("");
  const toggle = () => {
    setCollapsed(!collapsed);
  };

  const router = useRouter();
  const { pathname } = router;
  let count = pathname?.split("/").length - 1; // đếm số lượng ký tự "/"
  let newPath =
    count >= 3
      ? pathname.substring(
          0,
          pathname.indexOf("/", pathname.indexOf("/", pathname.indexOf("/") + 1) + 1)
        )
      : pathname;
  const selectedKeys = [newPath];

  const handleClick = (event) => {
    const { key } = event;

    // Push the route to the router
    router.push(key);
  };
  useEffect(() => {
    setFullName(Cookies.get("name"));
  }, []);

  return (
    <ConfigProvider
      theme={{
        token: {  colorPrimary: '#2D3791', colorLink: '#2D3791', colorLinkHover: '#2D3791'},
      }}
    > <ToastContainer  />
      <Layout style={{ minHeight: "100vh" }}>
        <Sider
          width={200}
          style={{
            position: "fixed",
            left: 0,
            top: 0,
            bottom: 0,
            background: "#fff",
          }}
          className="shadow "
          collapsed={collapsed}
          onCollapse={toggle}
        >
          <Link
            href="/"
            className="flex justify-center items-center bg-white mb-2"
          >
            <img
              src={!collapsed ? logo.src : mLogo.src}
              alt=""
              className={`cursor-pointer `}
              style={{ height: "64px" }}
            />
          </Link>
          <Menu
            defaultSelectedKeys={selectedKeys}
            mode="inline"
            items={items}
            onClick={handleClick}
          />
        </Sider>
        <Layout style={{ marginLeft: collapsed ? 80 : 200 }}>
          <Header
            className="shadow fixed w-full z-[9999] "
            style={{
              background: "#fff",
              paddingInlineEnd: !collapsed ? 220 : 100,
              paddingInlineStart: 20,
            }}
          >
            <div className="flex gap-2 w-full justify-between textPrimary2 ">
              {React.createElement(
                collapsed ? MenuUnfoldOutlined : MenuFoldOutlined,
                {
                  className: "trigger",
                  onClick: () => setCollapsed(!collapsed),
                }
              )}
              <div className="textPrimary2">
                Wellcome <span className="font-medium"> {fullName}!</span>
              </div>
            </div>
          </Header>
          <Content
            style={{
              margin: "64px 0 0",
              // minHeight: "calc(100vh - 64px - 24px ) ",
            }}
          >
            <div
              style={{
                padding: 24,
                paddingBottom: 0,
              }}
            >
              {children}
            </div>
          </Content>
          <Footer
            className="bg-white shadow"
            style={{ textAlign: "center", background: "#fff" }}
          >
            {" "}
            © 2023 Greenwich.edu.vn. All Right Reserved
          </Footer>
        </Layout>
      </Layout>
    </ConfigProvider>
  );
};

export default CustomLayout;
