import {
  Box,
  Card,
  CardContent,
  LinearProgress,
  Typography,
} from "@mui/material";

interface ChallenegParams {
  level: number;
  title: string;
  currentPoint: number;
  maxPoint: number;
  progressColor: string;
}

export const Challenge = ({
  level,
  title,
  currentPoint,
  maxPoint,
  progressColor,
}: ChallenegParams) => {
  return (
    <Card
      sx={(theme) => ({ padding: theme.spacing(1), margin: theme.spacing(1) })}
    >
      <CardContent>
        <Typography
          variant="h5"
          sx={(theme) => ({ padding: theme.spacing(1) })}
        >
          {title}
        </Typography>
        <Box
          sx={(theme) => ({
            display: "flex",
            justifyContent: "space-between",
            padding: theme.spacing(1),
          })}
        >
          <Box>
            <Typography variant="subtitle1">Level {level}</Typography>
          </Box>
          <Box>
            <Typography variant="subtitle1">
              {currentPoint}/{maxPoint}
            </Typography>
          </Box>
        </Box>
        <Box>
          <LinearProgress
            variant="determinate"
            value={(currentPoint * 100) / maxPoint}
            sx={(theme) => ({
              height: 30,
              borderRadius: 3,
              backgroundColor: "#e9e9e9",
              "& .MuiLinearProgress-bar": {
                backgroundColor: progressColor,
                borderRadius: 3,
              },
            })}
          />
        </Box>
      </CardContent>
    </Card>
  );
};
