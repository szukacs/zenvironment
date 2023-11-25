import {
  Box,
  Card,
  CardContent,
  LinearProgress,
  Typography,
} from "@mui/material";

interface ChallengeParams {
  level: number;
  name: string;
  description: string;
  prevLevelTarget: number;
  nextLevelTarget: number;
  currentProgress: number;
  progressColor?: string;
}

export const Challenge = ({
  level,
  name,
  description,
  prevLevelTarget,
  nextLevelTarget,
  currentProgress,
  progressColor,
}: ChallengeParams) => {
  return (
    <Card
      sx={(theme) => ({ padding: theme.spacing(1), margin: theme.spacing(1) })}
    >
      <CardContent>
        <Typography
          variant="h5"
          sx={(theme) => ({ padding: theme.spacing(1) })}
        >
          {name}
        </Typography>
        <Typography
          variant="h6"
          sx={(theme) => ({ padding: theme.spacing(1) })}
        >
          {description}
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
              {currentProgress - prevLevelTarget}/{nextLevelTarget}
            </Typography>
          </Box>
        </Box>
        <Box>
          <LinearProgress
            variant="determinate"
            value={
              ((currentProgress - prevLevelTarget) * 100) / nextLevelTarget
            }
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
