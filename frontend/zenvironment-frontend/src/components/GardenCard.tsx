import React from "react";
import { Box, Card, CardContent, Stack, Typography } from "@mui/material";
import { GardenDto } from "@/lib/api/generated/generated-api";
import { Garden } from "./Garden";
import { baseURL } from "@/lib/constans";

export interface GardenCardProps {
  garden: GardenDto;
}

export const GardenCard: React.FC<GardenCardProps> = ({ garden }) => {
  const roundNumber = (num: number) => {
    return Math.round((num + Number.EPSILON) * 100) / 100;
  };

  return (
    <Card
      sx={{
        background: "linear-gradient(#aaccc0, #96ccab 70%)",
        color: "#0d5e41",
      }}
    >
      <CardContent>
        <Typography
          gutterBottom
          variant="h5"
          component="div"
          sx={{ textAlign: "center" }}
        >
          <strong>{garden.name}</strong>
        </Typography>
        <Box pt={4}>
          <Garden isAddDisabled tileWidth={60} plants={garden.plants ?? []} />
        </Box>
        <Card>
          <CardContent>
            <Stack spacing={1}>
              {(garden.plantSummaries ?? []).map((summary) => {
                let oxygenUnit = "kg";
                let oxygenProduction: string | number =
                  summary.allProducedOxygenInKilograms ?? 0;
                if (oxygenProduction < 1.0) {
                  oxygenProduction = oxygenProduction * 1000.0;
                  oxygenUnit = "g";
                }
                oxygenProduction = roundNumber(oxygenProduction);
                let co2Unit = "kg";
                let co2Fixation: string | number =
                  summary.allFixatedCO2InKilograms ?? 0;
                if (co2Fixation < 1) {
                  co2Fixation = co2Fixation * 1000.0;
                  co2Unit = "g";
                }
                co2Fixation = roundNumber(co2Fixation);
                return (
                  <Stack
                    key={summary.plantType?.id}
                    direction="row"
                    justifyContent="flex-start"
                    alignItems="center"
                    spacing={1}
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
                    <Typography>×</Typography>
                    <Typography
                      sx={{ color: "#cfa448" }}
                    >{`${summary.plantCount}`}</Typography>
                    <Typography
                      sx={{
                        color: "#52c454",
                        flex: 1,
                        textAlign: "right",
                      }}
                    >
                      CO<sub>2</sub>
                      <strong>{` ${co2Fixation} ${co2Unit}`}</strong>
                    </Typography>
                    <Typography
                      sx={{
                        color: "#34c0eb",
                        flex: 1,
                        textAlign: "right",
                      }}
                    >
                      O<sub>2</sub>
                      <strong>{` ${oxygenProduction} ${oxygenUnit}`}</strong>
                    </Typography>
                  </Stack>
                );
              })}
            </Stack>
          </CardContent>
        </Card>
      </CardContent>
    </Card>
  );
};
