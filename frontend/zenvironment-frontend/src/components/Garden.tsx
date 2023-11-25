import { Box, Dialog, DialogContent } from "@mui/material";
import { FC, useEffect, useState } from "react";
import { AddPlantDialog } from "./AddPlantDialog";

interface GardenProps {}

const COLS = 5;
const ROWS = 5;

export const Garden: FC<GardenProps> = ({}) => {
  const [isAddDialogOpen, setIsAddDialogOpen] = useState(false);
  const [PLANTS, SETPLANTS] = useState([
    [3, 2],
    [3, 4],
    [2, 4],
    [1, 2],
  ]);

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
            const hasPlant = Boolean(
              PLANTS.find((plant) => plant[0] === x && plant[1] === y)
            );

            return (
              <Tile
                plant={hasPlant}
                x={x}
                y={y}
                onClick={() => {
                  if (!hasPlant) {
                    setIsAddDialogOpen(true);
                  }
                }}
              />
            );
          })
        )}
      </Box>
      <AddPlantDialog
        isOpen={isAddDialogOpen}
        onSelect={(plantType) => {}}
        onClose={() => {
          setIsAddDialogOpen(false);
        }}
      />
    </>
  );
};

interface TileProps {
  x: number;
  y: number;
  plant: boolean;
  onClick: VoidFunction;
}

const TILE_WIDTH = 75;
const TILE_HEIGHT = TILE_WIDTH * 0.645;
const GAP = 5;

const Tile: FC<TileProps> = ({ x, y, plant, onClick }) => {
  return (
    <Box
      onClick={onClick}
      sx={{
        position: "absolute",
        width: TILE_WIDTH,
        height: TILE_HEIGHT,
        cursor: "pointer",
        left:
          155 + (x * TILE_WIDTH) / 2 + x * GAP - (y * TILE_WIDTH) / 2 - y * GAP,
        top:
          (x * TILE_HEIGHT) / 2 +
          (x * GAP) / 2 +
          (y * TILE_HEIGHT) / 2 +
          y * GAP,
        "&:hover": {
          filter: "brightness(1.2)",
        },
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
