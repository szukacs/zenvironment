"use client";

import { Garden } from "@/components/Garden";
import { Page } from "@/components/Page";
import { useGetMyGardenQuery } from "@/components/mygardenQueries";
import { Box, CircularProgress } from "@mui/material";

export default function MyGarden() {
  const myGardenQuery = useGetMyGardenQuery();
  if (myGardenQuery.isLoading) {
    return (
      <Box display="flex" justifyContent="center">
        <CircularProgress color="success" />
      </Box>
    );
  }

  return (
    <Page title="My Garden">
      {myGardenQuery.status == "error" && (
        <>
          Error, this is definitely not our fault! Drop out your laptop and buy
          a new one! :)
        </>
      )}
      {myGardenQuery.status == "success" && <Garden />}
    </Page>
  );
}
