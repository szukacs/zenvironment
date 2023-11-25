import React from 'react';
import {Card, CardActionArea, CardContent, CardMedia, Typography} from "@mui/material";
import {GardenDto} from "@/lib/api/generated/generated-api";

export interface GardenCardProps {
  garden: GardenDto
}

export const GardenCard: React.FC<GardenCardProps> = ({garden}) => {
  return (
    <Card sx={{ maxWidth: 345 }}>
      <CardActionArea>
        <CardMedia
          component="img"
          height="160"
          image="/garden.png"
          alt="garden picture"
        />
        <CardContent>
          <Typography gutterBottom variant="h5" component="div">
            {garden.name}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Lizards are a widespread group of squamate reptiles, with over 6,000
            species, ranging across all continents except Antarctica
          </Typography>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}
