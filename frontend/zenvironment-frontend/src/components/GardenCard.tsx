import React from "react";
import {
  Box,
  Card,
  CardActionArea,
  CardContent,
  CardMedia,
  Stack,
  Typography,
} from "@mui/material";
import { GardenDto } from "@/lib/api/generated/generated-api";
import Image from "next/image";
import { Garden } from "./Garden";

export interface GardenCardProps {
  garden: GardenDto;
}

export const GardenCard: React.FC<GardenCardProps> = ({ garden }) => {
  return (
    <Card
      sx={{
        background: "linear-gradient(#aaccc0, #96ccab 70%)",
        color: "#0d5e41",
      }}
    >
      <CardContent>
        <Box pt={2}>
          <Garden isAddDisabled tileWidth={60} plants={garden.plants ?? []} />
        </Box>
        <Typography gutterBottom variant="h5" component="div">
          {garden.name}
        </Typography>
        <Stack spacing={1}>
          <Stack
            direction="row"
            justifyContent="flex-start"
            alignItems="center"
            spacing={2}
          >
            <Image
              style={{}}
              src="/tomato.png"
              alt="tomato pic"
              height={30}
              width={30}
            />
            <Typography>x</Typography>
            <Typography>4 pcs</Typography>
            <Typography>2.5 kg</Typography>
            <Typography>2.5 kg</Typography>
          </Stack>
        </Stack>
      </CardContent>
    </Card>
  );
};
