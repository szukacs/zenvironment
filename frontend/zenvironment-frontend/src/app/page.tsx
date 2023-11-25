"use client";

import { Garden } from "@/components/Garden";
import { Page } from "@/components/Page";
import { Box, CircularProgress, Stack, useMediaQuery } from "@mui/material";
import { StatDisplay } from "@/components/StatDisplay";
import { useGetGardenQuery, useGetMyGardenQuery } from "@/components/queries";
import { getSessionIdOrThrow } from "@/lib/session";

export default function MyGarden() {
  const isMobile = useMediaQuery("(max-width:600px)", { noSsr: true });

  const myGardenQuery = useGetGardenQuery(getSessionIdOrThrow(), true);
  if (myGardenQuery.isLoading) {
    return (
      <Box display="flex" justifyContent="center" pt={3}>
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
  let oxygenUnit = "kg";
  let oxygenProduction: string | number =
    myGardenQuery.data?.data.allProducedOxygenInKilograms ?? 0;
  if (oxygenProduction < 1.0) {
    oxygenProduction = oxygenProduction * 1000.0;
    oxygenUnit = "g";
  }
  oxygenProduction = Math.round(oxygenProduction * 100) / 100;
  let co2Unit = "kg";
  let co2Fixation: string | number =
    myGardenQuery.data?.data.allFixatedCO2InKilograms ?? 0;
  if (co2Fixation < 1) {
    co2Fixation = co2Fixation * 1000.0;
    co2Unit = "g";
  }
  co2Fixation = Math.round(co2Fixation * 100) / 100;

  return (
    <Page title={name}>
      {myGardenQuery.status == "success" && (
        <Box mt={3}>
          <Box pt={2}>
            <Garden
              tileWidth={isMobile ? 60 : undefined}
              plants={myGardenQuery.data?.data.plants!}
            />
          </Box>
          <Stack spacing={2}>
            <StatDisplay
              sx={{ color: "#34c0eb" }}
              title="All Oxygen production"
              amount={`${oxygenProduction}`}
              unit={oxygenUnit}
              fact="Medium oxygen consumption for a human for a day is 0.85 kg"
            />
            <StatDisplay
              sx={{ color: "#52c454" }}
              title="All fixated carbon-dioxid"
              amount={`${co2Fixation}`}
              unit={co2Unit}
              fact="Petrol produces 2.3 kg of CO2 per litre burnt. "
            />
          </Stack>
        </Box>
      )}
    </Page>
  );
}
