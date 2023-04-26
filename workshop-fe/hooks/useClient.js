import React from 'react'
import { Stomp } from '@stomp/stompjs'

let stompClient

const useClient = () => {
  const [client, setClient] = React.useState(stompClient)

  React.useEffect(() => {
    if (!stompClient) {
      stompClient = Stomp.client(process.env.NEXT_PUBLIC_SOCKET_URL)
    }
    if (!client) {
      setClient(stompClient)
    }
  }, [client, stompClient])

  return client
}

export default useClient
