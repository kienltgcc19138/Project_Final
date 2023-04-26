import React from "react";
import Stomp from "stompjs";
import SockJS from "sockjs-client";
import { StompSubscriber } from "react-stomp";

const MyComponent = () => {
  const onMessage = (msg) => {
    console.log("Received message:", msg);
  };

  return (
    <StompSubscriber
      url={SOCKET_URL}
      topics={[`/user/${userId}/message`]}
      onConnect={onConnected}
      onDisconnect={console.log("Disconnected!")}
      onMessage={(msg) => onMessageReceived(msg)}
    />
  );
};

export default MyComponent;
