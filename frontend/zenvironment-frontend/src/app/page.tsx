"use client";

import { Garden } from "@/components/Garden";
import { Page } from "@/components/Page";
import { Box, CircularProgress, Stack } from "@mui/material";
import { StatDisplay } from "@/components/StatDisplay";
import { useGetGardenQuery, useGetMyGardenQuery } from "@/components/queries";
import { getSessionIdOrThrow } from "@/lib/session";

export default function MyGarden() {
  const myGardenQuery = useGetGardenQuery(getSessionIdOrThrow());
  if (myGardenQuery.isLoading) {
    return (
      <Box display="flex" justifyContent="center">
        <CircularProgress color="success" />
      </Box>
    );
  }

  if (myGardenQuery.status == "error") {
    return (
      <>
        Error, this is definitely not our fault! Drop out your laptop and buy a
        new one! :)
      </>
    );
  }

  const name = myGardenQuery.data?.data.name ?? "";

  return (
    <Page title={`${name}${name[name.length - 1] === "s" ? "’" : "’s"} Garden`}>
      {myGardenQuery.status == "success" && (
        <Box mt={3}>
          <Garden plants={myGardenQuery.data?.data.plants!} />
          <Stack spacing={2}>
            <StatDisplay
              sx={{ color: "#34c0eb" }}
              title="All Oxygen production"
              amount={`${
                myGardenQuery.data.data.allProducedOxygenInKilograms ?? 0
              }`}
              unit="kg"
              fact="Medium consumption for a human for a day is 0.85 kg"
            />
            <StatDisplay
              sx={{ color: "#52c454" }}
              title="All fixated carbon-dioxid"
              amount={`${
                myGardenQuery.data.data.allFixatedCO2InKilograms ?? 0
              }`}
              unit="kg"
              fact="Petrol produces 2.3 kg of CO2 per litre burnt. "
            />
          </Stack>
        </Box>
      )}
    </Page>
  );
}
