import useStomp from "@/hooks/Stomp";
import Cookies from "js-cookie";
import React, { useState, useEffect } from "react";
const SOCKET_URL = "http://45.119.212.77:5021/ws-message";
const userId = Cookies.get("userId");

const MyComponent = () => {
  //   const [stompClient, isConnected] = useStomp(
  //     SOCKET_URL,
  //     `/user/${userId}/message`
  //   );
  const [messages, setMessages] = useState([]);
  console.log(messages);
  const handleIncomingMessage = (message) => {
    console.log(message);
    setMessages((prevMessages) => [...prevMessages, message]);

  };
  const [stompClient, isConnected] = useStomp(
    SOCKET_URL,
    `/user/${userId}/message`,
    handleIncomingMessage
  );


  useEffect(() => {
    return () => {
      if (stompClient) {
        stompClient.disconnect();
      }
    };
  }, [stompClient]);

  // function to send a message to the server

  return (
    <div>
      <h1>My Component</h1>
      {isConnected ? (
        <ul>
          {messages.map((message,index) => (
            <li key={index}>{message.description}</li>
          ))}
        </ul>
      ) : (
        <p>Connecting to Stomp server...</p>
      )}
    </div>
  );
};

export default MyComponent;
