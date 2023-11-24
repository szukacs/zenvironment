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
    <Card sx={(theme) => ({ padding: theme.spacing(1) })}>
      <CardContent>
        <Typography sx={(theme) => ({ padding: theme.spacing(1) })}>
          Level {level}
        </Typography>
        <Box
          sx={(theme) => ({
            display: "flex",
            justifyContent: "space-between",
            padding: theme.spacing(1),
          })}
        >
          <Box>
            <Typography>{title}</Typography>
          </Box>
          <Box>
            <Typography>
              {currentPoint}/{maxPoint}
            </Typography>
          </Box>
        </Box>
        <Box>
          <LinearProgress
            variant="determinate"
            value={(currentPoint * 100) / maxPoint}
            sx={(theme) => ({
              height: 50,
              borderRadius: 3,
              backgroundColor: theme.palette.primary.light,
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
