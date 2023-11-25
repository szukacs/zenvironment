"use client";

import { FC, useEffect, useState } from "react";
import {
  Card,
  CardContent,
  IconButton,
  InputAdornment,
  Stack,
  TextField,
  Typography,
  useMediaQuery,
} from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
import Image from "next/image";
import { api } from "@/lib/api/api";
import { Page } from "@/components/Page";

export interface Message {
  sender: string;
  message: string;
}
const oldSam = "Old Sam";

let messagesState: Message[] = [
  {
    sender: oldSam,
    message:
      "Well, howdy there, stranger! Gather 'round, and let me tip my hat to ya. Name's Old Sam, been wanderin' these digital plains for a spell now. Reckon you're new in town, so I'm here to give you a warm welcome. Now, what can this old-timer do for ya? Need some guidance or information? Just spit it out, and Old Sam'll do his best to help ya out.",
  },
];
export default function GardenAdvisor() {
  const [messages, setMessages] = useState(messagesState);
  const [currentMessage, setCurrentMessage] = useState("");

  const sendMessage = async () => {
    setMessages((prevState) => [
      ...prevState,
      { sender: "You", message: currentMessage },
    ]);
    setCurrentMessage("");

    await api.assistant
      .askForAssistance({ message: currentMessage })
      .then((resp) => {
        if (resp.data != null && resp.data.message != null) {
          setMessages((prevState) => [
            ...prevState,
            { sender: oldSam, message: resp.data.message! },
          ]);
        }
      });
  };

  useEffect(() => {
    messagesState = messages;
  }, [messages]);

  return (
    <Page>
      <Stack direction="column" spacing={2} sx={{ paddingBottom: "56px" }}>
        {messages.map((message) => (
          <MessageDisplay
            key={message.message}
            sender={message.sender}
            message={message.message}
          />
        ))}

        <form
          onSubmit={(e) => {
            e.preventDefault();
            sendMessage();
          }}
        >
          <TextField
            id="filled-basic"
            label="Ask from Old Sam"
            variant="filled"
            value={currentMessage}
            onChange={(event) => {
              setCurrentMessage(event.target.value);
            }}
            sx={{
              position: "fixed",
              left: 0,
              bottom: 56,
              width: "100%",
              backgroundColor: "#fff",
            }}
            InputProps={{
              endAdornment: (
                <InputAdornment position="end">
                  <IconButton
                    type="submit"
                    edge="end"
                    aria-label="Toggle password visibility"
                  >
                    <SendIcon />
                  </IconButton>
                </InputAdornment>
              ),
            }}
          />
        </form>
      </Stack>
    </Page>
  );
}

const MessageDisplay: FC<Message> = ({ sender, message }) => {
  const isOldSamMessage = sender === oldSam;
  const isMobile = useMediaQuery("(max-width:600px)", { noSsr: true });

  const image = isOldSamMessage ? (
    <Image
      style={{ borderRadius: "50%", marginLeft: "1.2rem" }}
      src="/farmer.png"
      alt="Farmer picture"
      height={isMobile ? 60 : 100}
      width={isMobile ? 60 : 100}
    />
  ) : (
    <Image
      style={{ borderRadius: "50%", marginRight: "1.2rem" }}
      src="/user.png"
      alt="User picture"
      height={isMobile ? 54 : 90}
      width={isMobile ? 54 : 90}
    />
  );

  return (
    <Stack
      spacing={1}
      sx={
        isOldSamMessage
          ? { flexDirection: "row" }
          : { flexDirection: "row-reverse" }
      }
    >
      {image}
      <Card style={{ margin: "0 1.2rem" }}>
        <CardContent>
          <Stack spacing={1}>
            <Typography color="textSecondary">{sender}</Typography>
            <Typography>{message}</Typography>
          </Stack>
        </CardContent>
      </Card>
    </Stack>
  );
};
