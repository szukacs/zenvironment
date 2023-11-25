import React from "react";
import {
  Box,
  Card,
  CardContent,
  Stack,
  Typography,
} from "@mui/material";
import { GardenDto } from "@/lib/api/generated/generated-api";
import { Garden } from "./Garden";
import {baseURL} from "@/lib/constans";

export interface GardenCardProps {
  garden: GardenDto;
}

export const GardenCard: React.FC<GardenCardProps> = ({ garden }) => {

  const roundNumber = (num: number) =>{
    return Math.round((num + Number.EPSILON) * 100) / 100
  }
  return (
    <Card>
      <CardContent>
        <Box pt={2}>
          <Garden isAddDisabled tileWidth={60} plants={garden.plants ?? []} />
        </Box>
        <Typography gutterBottom variant="h5" component="div">
          {garden.name}
        </Typography>
        <Stack spacing={1}>
          {(garden.plantSummaries?? []).map(summary => (
            <Stack
              direction="row"
              justifyContent="flex-start"
              alignItems="center"
              spacing={2}
            >
              <Box
                component="img"
                sx={{
                  maxWidth: "100%",
                  width: 30,
                  objectFit: "contain",
                  marginRight: 1,
                }}
                src={`${baseURL}${summary.plantType?.imageUrl}`}
              ></Box>
              <Typography>x</Typography>
              <Typography sx={{color: "#cfa448"}}>{`${summary.plantCount} pcs`}</Typography>
              <Typography sx={{width: '95px', color: "#52c454"}}>{`CO2 ${roundNumber(summary.allFixatedCO2InKilograms ?? 0)} kg`}</Typography>
              <Typography sx={{width: '95px', color: "#34c0eb"}}>{`O2 ${roundNumber(summary.allProducedOxygenInKilograms ?? 0)} kg`}</Typography>
            </Stack>
          ))}

        </Stack>
      </CardContent>
    </Card>
  );
};
