"use client";

import {FC, useEffect, useState} from "react";
import {Card, CardContent, IconButton, InputAdornment, Stack, TextField, Typography} from "@mui/material";
import SendIcon from '@mui/icons-material/Send';
import Image from "next/image";

export interface Message {
  sender: string,
  message: string
}
const oldSam = "Old Sam"

let messagesState: Message[] = [
  {
    sender: oldSam,
    message: 'Well, howdy there, stranger! Gather \'round, and let me tip my hat to ya. Name\'s Old Sam, been wanderin\' these digital plains for a spell now. Reckon you\'re new in town, so I\'m here to give you a warm welcome. Now, what can this old-timer do for ya? Need some guidance or information? Just spit it out, and Old Sam\'ll do his best to help ya out.'
  }
  ]
export default function GardenAdvisor() {
  const [messages, setMessages] = useState(messagesState)
  const [currentMessage, setCurrentMessage] = useState('')

  const sendMessage =  async () => {
    const messagesWithYour = [...messages, {sender: "You", message: currentMessage}]
   // send req to backend
    setMessages(messagesWithYour)
  }


  useEffect(() => {
    messagesState = messages
  }, [messages]);



  return (
    <Stack direction='column' spacing={2}>
      {messages.map(message => (<MessageDisplay key={message.message} sender={message.sender} message={message.message} />))}

      <TextField id="filled-basic" label="Ask from Old Sam" variant="filled"
                 value={currentMessage}
                 onChange={(event)=>{setCurrentMessage(event.target.value)}}
                 InputProps={{
        endAdornment: (
          <InputAdornment position="end">
            <IconButton
              edge="end"
              aria-label="Toggle password visibility"
              onClick={sendMessage}
            >
             <SendIcon />
            </IconButton>
          </InputAdornment>
        ),
      }} />
    </Stack>

  )
}


const MessageDisplay : FC<Message>= ({sender, message}) => {

  const isOldSamMessage = sender === oldSam

  const image = isOldSamMessage ? (
    <Image style={{borderRadius: '50%', marginLeft: '1.2rem'}} src='/farmer.png' alt="Farmer picture" height={100} width={100}/>
  ) : (
    <Image style={{borderRadius: '50%', marginRight: '1.2rem'}} src='/user.png' alt="User picture" height={90} width={90}/>
  )

  return (
    <Stack spacing={1} sx={isOldSamMessage ? {flexDirection: 'row'} : {flexDirection: 'row-reverse'}}>
      {image}
      <Card style={{margin: '0 1.2rem'}}>
        <CardContent>
          <Stack spacing={1} >
            <Typography color="textSecondary" >{sender}</Typography>
            <Typography>{message}</Typography>
          </Stack>
        </CardContent>
      </Card>
    </Stack>
 )
}