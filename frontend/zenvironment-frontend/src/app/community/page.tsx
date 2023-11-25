"use client";

import { Challenges } from "@/components/Challenges";
import { Page } from "@/components/Page";
import { DisplayTab, SimpleTab } from "@/components/SimpleTab";
import { useGetMyCommunityQuery } from "@/components/queries";
import { Box, CircularProgress } from "@mui/material";
import {MyCommunityGardens} from "@/components/MyCommunityGardens";

export default function Community() {
  const myCommunityQuery = useGetMyCommunityQuery();

  if (myCommunityQuery.isLoading) {
    return (
      <Box display="flex" justifyContent="center">
        <CircularProgress color="success" />
      </Box>
    );
  }

  if (myCommunityQuery.status == "error") {
    return (
      <>
        Error, this is definitely not our fault! Drop out your laptop and buy a new one! :)
      </>)
  }

  const tabs: DisplayTab[] = [
    {
      label: "Gardens",
      content: <MyCommunityGardens gardenList={myCommunityQuery.data!.data.gardens ?? []} />,
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
      {myCommunityQuery.status == "success" && (
        <>{myCommunityQuery.data.data.name}</>
      )}
      <SimpleTab tabs={tabs} />
    </Page>
  );
}
