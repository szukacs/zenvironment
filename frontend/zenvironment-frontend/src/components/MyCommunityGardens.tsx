import React from "react";
import { GardenDto } from "@/lib/api/generated/generated-api";
import { Box, Card, CardContent, Stack, Typography } from "@mui/material";
import { GardenCard } from "@/components/GardenCard";

export interface MyCommunityGardensProps {
  gardenList: GardenDto[];
  allOxigen: number;
  allCO2: number;
}

export const MyCommunityGardens: React.FC<MyCommunityGardensProps> = ({
  gardenList,
  allOxigen,
  allCO2,
}) => {
  let oxygenUnit = "kg";
  let oxygenProduction: string | number = allOxigen;
  if (oxygenProduction < 1.0) {
    oxygenProduction = oxygenProduction * 1000.0;
    oxygenUnit = "g";
  }
  oxygenProduction = Math.round(oxygenProduction * 100) / 100;

  let co2Unit = "kg";
  let co2Fixation: string | number = allCO2;
  if (co2Fixation < 1) {
    co2Fixation = co2Fixation * 1000.0;
    co2Unit = "g";
  }
  co2Fixation = Math.round(co2Fixation * 100) / 100;

  return (
    <Stack spacing={3}>
      <Card>
        <CardContent>
          <Typography>
            <Box component="img" src="co2.png" maxWidth={50} />: {co2Fixation}{" "}
            {co2Unit}, <Box component="img" src="o2.png" maxWidth={50} />:{" "}
            {oxygenProduction} {oxygenUnit}
          </Typography>
        </CardContent>
      </Card>
      <Box
        sx={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fill, minmax(350px, 1fr))",
          gap: 3,
        }}
      >
        {gardenList.map((garden) => (
          <GardenCard key={garden.id} garden={garden} />
        ))}
      </Box>
    </Stack>
  );
};
