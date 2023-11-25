import { Box, Dialog, DialogContent } from "@mui/material";
import { FC } from "react";

interface GardenProps {}

const COLS = 5;
const ROWS = 5;

const PLANTS = [
  [3, 2],
  [3, 4],
  [2, 4],
  [1, 2],
];

export const Garden: FC<GardenProps> = ({}) => {
  return (
    <>
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
            return (
              <Tile
                plant={Boolean(
                  PLANTS.find((plant) => plant[0] === x && plant[1] === y)
                )}
                x={x}
                y={y}
              />
            );
          })
        )}
      </Box>
      {/* <Dialog open fullWidth>
        <DialogContent>hello</DialogContent>
      </Dialog> */}
    </>
  );
};

interface TileProps {
  x: number;
  y: number;
  plant: boolean;
}

const TILE_WIDTH = 75;
const TILE_HEIGHT = TILE_WIDTH * 0.645;
const GAP = 5;

const Tile: FC<TileProps> = ({ x, y, plant }) => {
  return (
    <Box
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
    >
      {plant && (
        <Box
          sx={{
            position: "absolute",
            zIndex: 1,
            top: 0,
            left: "50%",
            transform: "translate(-50%, -50%)",
          }}
        >
          <Box
            component="img"
            src="tomato.png"
            sx={{
              width: TILE_WIDTH / 1.5,
            }}
          />
          <Box
            sx={{
              position: "absolute",
              width: 30,
              height: 30,
              background: "rgba(0,0,0, 0.2)",
              borderRadius: "50%",
              filter: "blur(3px)",
              top: "50%",
              left: "50%",
              transform: "translate(-50%, 30%)",
              zIndex: -1,
            }}
          />
        </Box>
      )}
      <Box component="img" src="grass-tile.svg" />
    </Box>
  );
};
