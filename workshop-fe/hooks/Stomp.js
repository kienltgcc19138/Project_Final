import { useState, useEffect } from 'react';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';

const useStomp = (endpointUrl, subscriptionPath, handleIncomingMessage) => {
  const [stompClient, setStompClient] = useState(null);
  const [isConnected, setIsConnected] = useState(false);

  useEffect(() => {
    const socket = new SockJS(endpointUrl);
    const stomp = Stomp.over(socket);

    stomp.connect({}, () => {
      // console.log('Connected to Stomp');
      setIsConnected(true);
      stomp.subscribe(subscriptionPath, (message) => {
        handleIncomingMessage(JSON.parse(message.body));
      });
      setStompClient(stomp);
    }, (error) => {
      // console.error(`Error connecting to Stomp: ${error}`);
    });

    return () => {
      if (stompClient) {
        stompClient.disconnect();
        setIsConnected(false);
        setStompClient(null);
      }
    };
  }, [endpointUrl, subscriptionPath]);
  useEffect(() => {
    return () => {
      if (stompClient) {
        stompClient.unsubscribe('/topic/messages');
        stompClient.disconnect();
      }
    };
  }, [stompClient]);
  
  return [stompClient, isConnected];
};

export default useStomp;
