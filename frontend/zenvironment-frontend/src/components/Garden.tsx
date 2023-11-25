import { Box, keyframes } from "@mui/material";
import { FC, useState } from "react";
import { AddPlantDialog } from "./AddPlantDialog";
import { PlantDto } from "@/lib/api/generated/generated-api";
import { baseURL } from "@/lib/constans";
import { api } from "@/lib/api/api";
import { useQueryClient } from "@tanstack/react-query";
import { myGardenQueryKeys } from "./queries";
import { PlantDetailsDialog } from "./PlantDetailsDialog";
import { getSessionIdOrThrow } from "@/lib/session";

interface GardenProps {
  plants: PlantDto[];
  tileWidth?: number;
  isAddDisabled?: boolean;
}

const COLS = 5;
const ROWS = 5;

export const Garden: FC<GardenProps> = ({
  plants,
  isAddDisabled = false,
  tileWidth = 75,
}) => {
  const tileHeight = tileWidth * 0.645;
  const queryClient = useQueryClient();
  const [addDialog, setAddDialog] = useState<
    { x: number; y: number } | undefined
  >(undefined);
  const [selectedPlant, setSelectedPlant] = useState<string | undefined>(
    undefined
  );

  return (
    <>
      <Box
        position="relative"
        sx={{
          margin: "0 auto",
          width: (tileWidth + GAP) * COLS,
          height: (tileHeight + GAP) * ROWS + 60,
        }}
      >
        {new Array(COLS).fill(true).map((_, x) =>
          new Array(ROWS).fill(true).map((_, y) => {
            const plant = plants.find(
              (plant) => plant.x === x && plant.y === y
            );

            return (
              <Tile
                key={`${x}-${y}`}
                plant={plant}
                x={x}
                y={y}
                isAddDisabled={isAddDisabled}
                tileWidth={tileWidth}
                tileHeight={tileHeight}
                onCloseDetails={() => {
                  setSelectedPlant(undefined);
                }}
                selected={plant ? plant.id === selectedPlant : false}
                onClick={() => {
                  if (plant) {
                    setSelectedPlant(plant.id!);
                  } else if (!isAddDisabled) {
                    setAddDialog({ x, y });
                  }
                }}
              />
            );
          })
        )}
      </Box>
      <AddPlantDialog
        isOpen={addDialog !== undefined}
        onSelect={async (plantType) => {
          await api.gardens.addPlant1(getSessionIdOrThrow(), {
            plantTypeId: plantType.id,
            plantedAt: new Date().toISOString(),
            x: addDialog?.x,
            y: addDialog?.y,
          });
          queryClient.invalidateQueries({
            queryKey: myGardenQueryKeys.myGarden(),
          });
        }}
        onClose={() => {
          setAddDialog(undefined);
        }}
      />
    </>
  );
};

interface TileProps {
  x: number;
  y: number;
  plant?: PlantDto;
  onClick: VoidFunction;
  onCloseDetails: VoidFunction;
  selected?: boolean;
  tileWidth: number;
  tileHeight: number;
  isAddDisabled: boolean;
}

const GAP = 5;

const Tile: FC<TileProps> = ({
  x,
  y,
  plant,
  onClick,
  selected,
  onCloseDetails,
  tileWidth,
  tileHeight,
  isAddDisabled,
}) => {
  return (
    <>
      <Box
        onClick={onClick}
        sx={{
          position: "absolute",
          width: tileWidth,
          height: tileHeight,
          cursor: !plant ? (isAddDisabled ? undefined : "pointer") : "pointer",
          left:
            tileWidth * 2.25 +
            (x * tileWidth) / 2 +
            x * GAP -
            (y * tileWidth) / 2 -
            y * GAP,
          top:
            (x * tileHeight) / 2 +
            (x * GAP) / 2 +
            (y * tileHeight) / 2 +
            y * GAP,
          "&:hover": {
            filter: !plant
              ? isAddDisabled
                ? undefined
                : "brightness(1.2)"
              : "brightness(1.2)",
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
              src={`${baseURL}${plant.plantType?.imageUrl}`}
              sx={{
                width: tileWidth / 1.5,
                animation: `${plantAnimation} 2s`,
                transformOrigin: "bottom",
              }}
            />
            <Box
              sx={{
                position: "absolute",
                width: tileWidth * 0.4,
                height: tileWidth * 0.4,
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
      {plant && (
        <PlantDetailsDialog
          plant={plant}
          isOpen={!!selected}
          onClose={onCloseDetails}
        />
      )}
    </>
  );
};

const plantAnimation = keyframes`
0% {
  transform: scale(0)
}
100% {
  transform: scale(1)
}
`;
