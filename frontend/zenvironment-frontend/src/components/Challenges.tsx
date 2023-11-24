import { Stack } from "@mui/material";
import { Challenge } from "./Challenge";

const challengeList = [
  {
    level: 1,
    title: "Oxigen",
    currentPoint: 25,
    maxPoint: 50,
    progressColor: "#557085",
  },
  {
    level: 2,
    title: "Tomato",
    currentPoint: 3,
    maxPoint: 10,
    progressColor: "#ee607a",
  },
];

export const Challenges = () => {
  return (
    <Stack>
      {challengeList.map((ch, key) => (
        <Challenge
          key={key}
          level={ch.level}
          currentPoint={ch.currentPoint}
          maxPoint={ch.maxPoint}
          progressColor={ch.progressColor}
          title={ch.title}
        />
      ))}
    </Stack>
  );
};
