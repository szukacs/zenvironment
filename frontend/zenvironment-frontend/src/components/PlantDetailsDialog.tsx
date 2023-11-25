import { PlantDto } from "@/lib/api/generated/generated-api";
import { baseURL } from "@/lib/constans";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  Typography,
  Box,
  Stack,
  SxProps,
  Card,
  CardContent,
  Theme,
} from "@mui/material";
import { FC } from "react";

interface PlantDetailsDialogProps {
  plant: PlantDto;
  isOpen: boolean;
  onClose: VoidFunction;
}

export const PlantDetailsDialog: FC<PlantDetailsDialogProps> = ({
  isOpen,
  onClose,
  plant,
}) => {
  const plantedAtDate = new Intl.DateTimeFormat("en-US").format(
    new Date(plant.plantedAt!)
  );

  return (
    <Dialog
      maxWidth="sm"
      fullWidth
      open={isOpen}
      onClose={onClose}
      PaperProps={{ style: { backgroundColor: "#0d5e41" } }}
    >
      <DialogTitle sx={{ textAlign: "center", color: "#ade4d1" }}>
        {plant.plantType?.name}
      </DialogTitle>
      <DialogContent sx={{ padding: 0 }}>
        <Stack spacing={2}>
          <Box
            component="img"
            src={`${baseURL}${plant.plantType?.imageUrl}`}
            sx={{
              width: 150,
              height: 150,
              objectFit: "contain",
              display: "block",
              alignSelf: "center",
            }}
          />
          <Stack
            spacing={2}
            pt={4}
            pb={4}
            sx={(theme) => ({
              backgroundColor: "#fff",
              borderRadius: `${theme.shape.borderRadius}px ${theme.shape.borderRadius}px 0 0`,
            })}
          >
            <StatDisplay
              sx={{ color: "#34c0eb", textAlign: "center" }}
              title={`Oxygen produced since ${plantedAtDate}`}
              amount={
                Math.round(plant.allProducedOxygenInKilograms! * 100) / 100
              }
              unit="kg"
            />
            <StatDisplay
              sx={{ color: "#52c454", textAlign: "center" }}
              title={`Fixated carbon-dioxid ${plantedAtDate}`}
              amount={Math.round(plant.allFixatedCO2InKilograms! * 100) / 100}
              unit="kg"
            />
          </Stack>
        </Stack>
      </DialogContent>
    </Dialog>
  );
};

const Stat: FC<{ label: React.ReactNode; value: React.ReactNode }> = ({
  label,
  value,
}) => {
  return (
    <Box>
      <Typography>{label}</Typography>
      <Typography>{value}</Typography>
    </Box>
  );
};

export interface StatDisplayProps {
  title: string;
  amount: string | number;
  unit: string;
  sx?: SxProps<Theme> | undefined;
}

export const StatDisplay: React.FC<StatDisplayProps> = ({
  title,
  amount,
  unit,
  sx,
}) => {
  return (
    <Box>
      <Stack sx={sx} spacing={1}>
        <Typography variant="h6" component="div">
          {title}
        </Typography>
        <Typography variant="h4" component="div">
          {`${amount} ${unit}`}
        </Typography>
      </Stack>
    </Box>
  );
};
