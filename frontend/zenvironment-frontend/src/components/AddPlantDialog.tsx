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
    <Dialog maxWidth="md" fullWidth open={isOpen} onClose={onClose}>
      <DialogTitle sx={{ textAlign: "center" }}>Choose Plant</DialogTitle>
      <DialogContent>
        <Box
          sx={{
            display: "grid",
            gap: 2,
          }}
        >
          {query.data?.data.map((plant) => (
            <>
              <Box
                key={plant.name}
                sx={{
                  cursor: "pointer",
                  display: "grid",
                  gridTemplateColumns: "100px 1fr",
                  gap: 2,
                  "&:hover": { filter: "brightness(0.9)" },
                }}
                onClick={() => {
                  onSelect(plant);
                  onClose();
                }}
              >
                <Box
                  component="img"
                  src={plant.imageUrl}
                  sx={{
                    maxWidth: "100%",
                    aspectRatio: "1/1",
                    objectFit: "cover",
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
              <Divider />
            </>
          ))}
        </Box>
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
