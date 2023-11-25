"use client";

import { Challenges } from "@/components/Challenges";
import { Page } from "@/components/Page";
import { DisplayTab, SimpleTab } from "@/components/SimpleTab";
import { useGetMyCommunityQuery } from "@/components/queries";
import { Box, CircularProgress } from "@mui/material";

export default function Community() {
  const myCommunityQuery = useGetMyCommunityQuery();

  if (myCommunityQuery.isLoading) {
    return (
      <Box display="flex" justifyContent="center">
        <CircularProgress color="success" />
      </Box>
    );
  }

  const tabs: DisplayTab[] = [
    {
      label: "Timeline",
      content: <>Example tab 1</>,
    },
    {
      label: "Gardens",
      content: <>Example tab 2</>,
    },
    {
      label: "Challenges",
      content: <Challenges />,
    },
    {
      label: "Market",
      content: <>Example tab 4</>,
    },
  ];

  return (
    <Page title="Community">
      {myCommunityQuery.status == "error" && (
        <>
          Error, this is definitely not our fault! Drop out your laptop and buy
          a new one! :)
        </>
      )}
      {myCommunityQuery.status == "success" && (
        <>{myCommunityQuery.data.data.name}</>
      )}
      <SimpleTab tabs={tabs} />
    </Page>
  );
}
