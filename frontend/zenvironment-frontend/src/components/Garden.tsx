import { Box } from "@mui/material";
import { FC } from "react";

interface GardenProps {}

const COLS = 5;
const ROWS = 5;

export const Garden: FC<GardenProps> = ({}) => {
  return (
    <Box
      position="relative"
      sx={{
        margin: "0 auto",
        width: (TILE_WIDTH + GAP) * COLS,
        height: (TILE_HEIGHT + GAP) * ROWS + 60,
      }}
    >
      {new Array(COLS).fill(true).map((_, x) =>
        new Array(ROWS).fill(true).map((_, y) => {
          return <Tile x={x} y={y} />;
        })
      )}
    </Box>
  );
};

interface TileProps {
  x: number;
  y: number;
}

const TILE_WIDTH = 75;
const TILE_HEIGHT = TILE_WIDTH * 0.645;
const GAP = 5;

const Tile: FC<TileProps> = ({ x, y }) => {
  return (
    <Box
      component="img"
      src="grass-tile.svg"
      sx={{
        position: "absolute",
        width: TILE_WIDTH,
        height: TILE_HEIGHT,
        left:
          155 + (x * TILE_WIDTH) / 2 + x * GAP - (y * TILE_WIDTH) / 2 - y * GAP,
        top:
          (x * TILE_HEIGHT) / 2 +
          (x * GAP) / 2 +
          (y * TILE_HEIGHT) / 2 +
          y * GAP,
      }}
    />
  );
};
