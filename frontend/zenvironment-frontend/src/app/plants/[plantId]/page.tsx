"use client";

import {StatDisplay} from "@/components/StatDisplay";
import {useGetPlantById} from "@/components/mygardenQueries";
import {Box, CircularProgress} from "@mui/material";

interface PlantPageParams {
  params: {
    plantId: string;
  };
}

export default function PlantPage({params}: PlantPageParams) {
  const getPlantQuery = useGetPlantById(params.plantId)

  if (getPlantQuery.isLoading) {
    return (
      <Box display="flex" justifyContent="center">
        <CircularProgress color="success"/>
      </Box>
    )
  }

  if (getPlantQuery.status == 'error') {
    return (<>Error, this is definitely not our fault! Drop out your laptop and buy a new
      one! :)</>)
  }

const plant = getPlantQuery.data!.data
  return (
    <>
      {params.plantId}
      <StatDisplay
        sx={{color: '#34c0eb'}}
        title='All Oxygen production'
        amount={`${plant.allProducedOxygenInKilograms ?? 0}`}
        unit="kg"
        fact="Medium consumption for a human for a day is 0.85 kg"
      />
      <StatDisplay
        sx={{color: '#52c454'}}
        title='All fixated carbon-dioxid'
        amount={`${plant.allFixatedCO2InKilograms ?? 0}`}
        unit="kg"
        fact='Petrol produces 2.3 kg of CO2 per litre burnt. '
      />
    </>
  )
}