import React from 'react';
import {Stack, SxProps, Theme, Typography} from "@mui/material";

export interface StatDisplayProps {
  title: string
  amount: string
  unit: string
  sx?:  SxProps<Theme> | undefined
}

export const StatDisplay: React.FC<StatDisplayProps> = ({title, amount, unit, sx}) => {
  return (
    <Stack sx={sx} spacing={2} padding="0 2rem">
      <Typography sx={{}}>{title}</Typography>
      <Typography sx={{textAlign: 'right'}}>{`${amount} ${unit}`}</Typography>
    </Stack>
  );
}
