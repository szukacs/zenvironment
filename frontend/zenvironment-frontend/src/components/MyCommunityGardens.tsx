import React from 'react';
import {GardenDto} from "@/lib/api/generated/generated-api";
import {Stack} from "@mui/material";
import {GardenCard} from "@/components/GardenCard";

export interface MyCommunityGardensProps {
  gardenList: GardenDto[]
}

export const MyCommunityGardens: React.FC<MyCommunityGardensProps> = ({gardenList}) => {
  return (
    <Stack direction="row" justifyContent="space-evenly" alignItems="center" flexWrap="wrap">
      {gardenList.map(garden => (<GardenCard key={garden.id} garden={garden} />))}
    </Stack>
  );
}
