import { Box, CircularProgress, Stack } from "@mui/material";
import { Challenge } from "./Challenge";
import { useGetCommunityChallengesQuery } from "./queries";

export const Challenges = () => {
  const challengesQuery = useGetCommunityChallengesQuery();

  if (challengesQuery.isLoading) {
    return (
      <Box display="flex" justifyContent="center">
        <CircularProgress color="success" />
      </Box>
    );
  }
  return (
    <Stack spacing={2}>
      {challengesQuery.status == "error" && (
        <>
          Error, this is definitely not our fault! Drop out your laptop and buy
          a new one! :)
        </>
      )}
      {challengesQuery.status == "success" &&
        challengesQuery.data.data.map((ch, key) => (
          <Challenge
            key={key}
            name={ch.challengeName!}
            description={ch.challengeDescription!}
            image={ch.imageUrl!}
            progressColor={ch.color!}
            level={ch.level!}
            prevLevelTarget={ch.previousLevelTarget!}
            nextLevelTarget={ch.nextLevelTarget!}
            currentProgress={ch.currentProgress!}
          />
        ))}
    </Stack>
  );
};
