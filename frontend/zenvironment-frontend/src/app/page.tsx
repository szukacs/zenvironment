"use client";

import { Page } from "@/components/Page";
import {useGetMyGardenQuery} from "@/components/mygardenQueries";
import {CircularProgress} from "@mui/material";

export default function MyGarden() {
  const myGardenQuery = useGetMyGardenQuery()
  if (myGardenQuery.isLoading) {
    return (<CircularProgress color="success" />)
  }
  return (
    <Page title="My Garden">
      {myGardenQuery.status == 'error' && (<>Error, this is definitely not our fault! Drop out your laptop and buy a new one! :)</>)}
      {myGardenQuery.status == 'success' && (
        <>
          {myGardenQuery.data.data.name}
        </>
      )}
    </Page>
  );
}
