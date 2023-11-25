import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Divider,
  Stack,
  Typography,
} from "@mui/material";
import { FC } from "react";
import { PlantTypeDto } from "@/lib/api/generated/generated-api";
import { useGetPlantTypes } from "./queries";
import { baseURL } from "@/lib/constans";

interface AddPlantDialogProps {
  isOpen: boolean;
  onClose: VoidFunction;
  onSelect: (plantType: PlantTypeDto) => void;
}

export const AddPlantDialog: FC<AddPlantDialogProps> = ({
  isOpen,
  onClose,
  onSelect,
}) => {
  const query = useGetPlantTypes();

  if (query.isLoading || query.isError) {
    return null;
  }

  return (
    <Dialog maxWidth="sm" fullWidth open={isOpen} onClose={onClose}>
      <DialogTitle sx={{ textAlign: "center" }}>Choose Plant</DialogTitle>
      <DialogContent sx={{ padding: 0 }}>
        {query.data?.data.map((plant) => (
          <div key={plant.id}>
            <Box
              key={plant.name}
              sx={(theme) => ({
                cursor: "pointer",
                display: "grid",
                gridTemplateColumns: "100px 1fr",
                gap: 2,
                padding: 2,
                borderBottom: `solid 1px ${theme.palette.divider}`,
                "&:hover": {
                  backgroundColor: "#eee",
                },
              })}
              onClick={() => {
                onSelect(plant);
                onClose();
              }}
            >
              <Box
                component="img"
                src={`${baseURL}${plant.imageUrl}`}
                sx={{
                  maxWidth: "100%",
                  aspectRatio: "1/1",
                  objectFit: "contain",
                }}
              />
              <div>
                <Typography>
                  <strong>{plant.name}</strong>
                </Typography>
                <Stat
                  name="Oxygen production"
                  amount={plant.averageOxygenProductionInKilogramsPerDay ?? 0}
                  unit="kg"
                  color="#34c0eb"
                />
                <Stat
                  name="Fixated carbon-dioxid"
                  amount={plant.averageCO2FixationInKilogramsPerDay ?? 0}
                  unit="kg"
                  color="#52c454"
                />
              </div>
            </Box>
          </div>
        ))}
      </DialogContent>
    </Dialog>
  );
};

const Stat: FC<{
  unit: string;
  amount: number;
  name: string;
  color: string;
}> = ({ name, unit, amount, color }) => {
  return (
    <Typography sx={{ color, textAlign: "left" }}>
      {name}: {`${amount} ${unit}`}
    </Typography>
  );
};
