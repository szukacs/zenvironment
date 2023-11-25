import React from "react";
import { GardenDto } from "@/lib/api/generated/generated-api";
import { Box, Stack } from "@mui/material";
import { GardenCard } from "@/components/GardenCard";
import { AllO2andCO2 } from "./AllO2andCO2";

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
  return (
    <Stack spacing={3}>
      <AllO2andCO2 co2={allCO2} o2={allOxigen} />
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
