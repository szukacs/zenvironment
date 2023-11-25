import {
  Box,
  Card,
  CardContent,
  Typography,
  useMediaQuery,
} from "@mui/material";

const ItemCard = ({
  image,
  unit,
  value,
  description,
  color,
}: {
  image: string;
  unit: string;
  value: number;
  description: string;
  color: string;
}) => {
  return (
    <Card sx={{ flex: 1 }}>
      <CardContent sx={{ display: "flex", gap: 2, alignItems: "center" }}>
        <Box component="img" src={image} maxWidth={50} />
        <Box>
          <Typography variant="h6" color={color}>
            <strong>
              {value} {unit}
            </strong>
          </Typography>
          <Typography>{description}</Typography>
        </Box>
      </CardContent>
    </Card>
  );
};

export const AllO2andCO2 = ({ co2, o2 }: { co2: number; o2: number }) => {
  const isMobile = useMediaQuery("(max-width:600px)", { noSsr: true });

  let oxygenUnit = "kg";
  let oxygenProduction: string | number = o2;
  if (oxygenProduction < 1.0) {
    oxygenProduction = oxygenProduction * 1000.0;
    oxygenUnit = "g";
  }
  oxygenProduction = Math.round(oxygenProduction * 100) / 100;

  let co2Unit = "kg";
  let co2Fixation: string | number = co2;
  if (co2Fixation < 1) {
    co2Fixation = co2Fixation * 1000.0;
    co2Unit = "g";
  }
  co2Fixation = Math.round(co2Fixation * 100) / 100;
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: isMobile ? "column" : "row",
        gap: 2,
      }}
    >
      <ItemCard
        image="co2.png"
        unit={co2Unit}
        value={co2Fixation}
        description="Fixated CO2 by the community"
        color="#52c454"
      />
      <ItemCard
        image="o2.png"
        unit={oxygenUnit}
        value={oxygenProduction}
        description="Oxygen production of the community"
        color="#34c0eb"
      />
    </Box>
  );
};
