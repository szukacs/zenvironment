import React from 'react';
import {GardenDto} from "@/lib/api/generated/generated-api";
import {Box, Stack} from "@mui/material";
import {GardenCard} from "@/components/GardenCard";

export interface MyCommunityGardensProps {
  gardenList: GardenDto[]
}

export const MyCommunityGardens: React.FC<MyCommunityGardensProps> = ({gardenList}) => {
  return (
    <Box sx={{display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(350px, 1fr))', gap: 3}}>
      {gardenList.map(garden => (<GardenCard key={garden.id} garden={garden} />))}
    </Box>
  );
}
