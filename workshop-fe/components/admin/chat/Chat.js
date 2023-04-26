import useStomp from "@/hooks/Stomp";
import ClientComponent from "@/layouts/Client";
import {
  adminSendMessageInEvent,
  searchAdminChat,
  searchAllChatUser,
} from "@/services/chatService";
import Cookies from "js-cookie";
import { useEffect, useRef, useState } from "react";
import SockJsClient from "react-stomp";
const SOCKET_URL = process.env.NEXT_PUBLIC_SOCKET_URL;
import Stomp from "stompjs";
export default function Chat() {
  const [connected, setConnected] = useState(false);
  const userId = Cookies.get("userId");

  const [message, setMessage] = useState([]);
  const [listChat, setListChat] = useState([]);
  const [activeChat, setActiveChat] = useState("");
  const [messages, setMessages] = useState([]);
  const [defaultMesages, setDefaultMessages] = useState([]);

  const handleIncomingMessage = (msg) => {
    if (msg[0]) {
      msg?.map((item, index) => {
        setMessages((prevMessages) => [...prevMessages, item]);
        setDefaultMessages(msg);
      });
    } else {
      setMessages((prevMessages) => [...prevMessages, msg]);
      setDefaultMessages(msg);
    }
  };
  const [stompClient, isConnected] = useStomp(
    SOCKET_URL,
    `/user/${userId}/message`,
    handleIncomingMessage
  );

  useEffect(() => {
    if (stompClient && isConnected) {
      console.log(
        "Stomp connected and subscribed to /user/" + userId + "/message"
      );
    }
  }, [stompClient, isConnected]);
  const messagesEndRef = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };
  const getListChat = async () => {
    const res = await searchAdminChat({
      pageNumber: 0,
      pageSize: 20,
      sort: [
        {
          key: "createdAt",
          asc: false,
        },
      ],
    });
    // console.log(res.data.data.content);
    // setActiveChat(res.data.data.content[0]?.usersResponse.usersId)
    setListChat(res.data.data.content);
    setActiveChat(res.data.data.content[0]?.usersResponse.usersId);
    // getChat();
  };
  useEffect(() => {
    if (activeChat) {
      setMessage([]);
      getChat();
    }
  }, [activeChat]);

  const getChat = async () => {
    const res = await searchAllChatUser(activeChat, {
      pageNumber: 0,
      pageSize: 20,
      sort: [
        {
          key: "createdAt",
          asc: false,
        },
      ],
    });
    const mess = res.data.data.content.reverse();
    setMessages([...mess]);
  };
  useEffect(() => {
    scrollToBottom();
  }, [messages]);
  useEffect(() => {
    getListChat();
  }, []);

  const sendMessage = async (content) => {
    try {
      const data = {
        content: content,
      };
      const res = await adminSendMessageInEvent(activeChat, data);
      setMessage("");
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    if (activeChat) {
      getChat();
    }
  }, []);
  const customHeaders = {
    "X-ACCESS-TOKEN": "Bearer " + Cookies.get("accessToken"),
  };
  return (
    <div className="container mx-auto shadow-lg rounded-lg">
      <div className="px-5 py-5 flex justify-between items-center bg-white border-b-2">
        <div className="font-semibold text-2xl">Chat</div>
      </div>
      {/* <ClientComponent>
        {userId &&  <SockJsClient
          url={SOCKET_URL}
          headers={customHeaders}
          topics={[`/user/${userId}/message`]}
          onConnect={onConnected}
          onDisconnect={console.log("Disconnected!")}
          onMessage={(msg) => onMessageReceived(msg)}
          onConnectFailure={(error) => {
            try {
              handleConnectionError(error);
            } catch (e) {}
          }}
          debug={true}
        />}
       
      </ClientComponent> */}

      <div className={`flex flex-row justify-between bg-white $`}>
        <div className="flex flex-col w-2/5 border-r-2 overflow-y-auto">
          {listChat.map((item, index) => {
            return (
              <div
                key={index}
                onClick={() => setActiveChat(item?.usersResponse.usersId)}
                className={`flex cursor-pointer flex-row py-4 px-2 justify-center items-center border-b-2 ${
                  activeChat == item?.usersResponse?.usersId
                    ? "border-b-2 border-l-4 border-[#2D3791]"
                    : ""
                }`}
              >
                <div className="w-1/4">
                  <div className="w-12 h-12 flex - justify-center items-center rounded-full bgPrimary text-white font-bold text-xl">
                    {`${item.usersResponse.fullName}`.slice(0, 1)}
                  </div>
                </div>
                <div className="w-full">
                  <div className="text-lg font-semibold">
                    {item.usersResponse.fullName}
                  </div>
                  <div className="text-gray-500">
                    {item.usersResponse.email}
                  </div>
                  <div className="text-gray-500">{item.lastCreatedMessage}</div>
                </div>
              </div>
            );
          })}
        </div>
        <div className="w-full  px-5 flex flex-col justify-between">
          <div className="flex flex-col ">
            <div className=" grid-cols-12 gap-y-2 h-[550px] overflow-y-scroll pr-4 ">
              {messages?.map((message, index) => {
                return (
                  <div
                    key={index}
                    className={` ${
                      message.createdBy === userId ||
                      message.createdBy === "DEFAULT_MESSAGE"
                        ? "col-start-6 col-end-13 "
                        : "col-start-1 col-end-8"
                    } p-3 rounded-lg`}
                  >
                    <div
                      className={`flex flex-row items-center ${
                        message.createdBy === userId ||
                        message.createdBy === "DEFAULT_MESSAGE"
                          ? "justify-start flex-row-reverse"
                          : ""
                      }`}
                    >
                      <div className="flex items-center justify-center h-10 w-10 overflow-hidden rounded-full bg-indigo-500 flex-shrink-0">
                        {message.createdBy !== userId &&
                        message.createdBy !== "DEFAULT_MESSAGE" ? (
                          <img
                            src={
                              "https://www.redditstatic.com/avatars/defaults/v2/avatar_default_2.png"
                            }
                          />
                        ) : (
                          <img
                            src={
                              "https://styles.redditmedia.com/t5_2hcc92/styles/profileIcon_snoo-nftv2_bmZ0X2VpcDE1NToxMzdfNjIyZDhmZWE0NjAzYmE5ZWRhZjEwODRiNDA3MDUyZDhiMGE5YmVkN185MjEyOQ_rare_ccd5f2e6-4af3-439c-9640-9a770501caf9-headshot.png?width=64&height=64&frame=1&auto=webp&crop=64:64,smart&v=enabled&s=a746ffb113a2372a936d5460db8180acc5dfa964"
                            }
                          />
                        )}
                      </div>
                      <div
                        className={`relative ml-3 text-sm ${
                          message.createdBy === userId ||
                          message.createdBy === "DEFAULT_MESSAGE"
                            ? "bg-white  mr-3"
                            : "bg-orange-500 text-white"
                        }  py-2 px-4 shadow rounded-xl`}
                      >
                        <div> {message?.description}</div>
                      </div>
                    </div>
                  </div>
                );
              })}
              <div ref={messagesEndRef} />
            </div>
          </div>
          <div className="py-5">
            <input
              onChange={(e) => setMessage(e.target.value)}
              value={message}
              onKeyDown={(e) => {
                if (e.key === "Enter") {
                  sendMessage(e.target.value);
                }
              }}
              className="flex w-full border rounded-xl focus:outline-none focus:border-indigo-300 pl-4 h-10"
              type="text"
              placeholder="type your message here..."
            />
          </div>
        </div>
      </div>
    </div>
  );
}
