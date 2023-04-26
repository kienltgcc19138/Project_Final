import { useState, useEffect, useCallback } from 'react'
import useClient from './useClient'

const useStomp = (topic) => {
  const [message, setMessage] = useState({})
  const client = useClient()

  // subscribe to a channel, then listen to the messages.
  const subscribe = useCallback(() => {
    console.log('Subscribing to topic:', topic)
    client.subscribe(topic, (msg) => {
      const change = JSON.parse(msg.body)
      setMessage(change)
    })
  }, [client, topic])

  // unsubscribe on unmount
  const unSubscribe = useCallback(() => {
    console.log('Unsubscribing from topic:', topic)
    client.unsubscribe()
  }, [client, topic])

  useEffect(() => {
    console.log('Calling subscribe...')
    subscribe()
    return () => {
      console.log('Calling unSubscribe...')
      unSubscribe()
    }
  }, [subscribe, unSubscribe])

  console.log('Client:', client)

  return message
}

export default useStomp
