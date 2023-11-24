import { Card } from "@mui/material";
import { Challenge } from "./Challenge";

const challengeList = [
  {
    level: 1,
    title: "Oxigen",
    currentPoint: 25,
    maxPoint: 50,
    progressColor: "yellow",
  },
  {
    level: 2,
    title: "Tomato",
    currentPoint: 3,
    maxPoint: 10,
    progressColor: "red",
  },
];

export const Challenges = () => {
  return (
    <Card>
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
    </Card>
  );
};
