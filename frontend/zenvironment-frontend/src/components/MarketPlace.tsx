import { Box, CircularProgress, Stack } from "@mui/material";
import { MarketPlaceItem } from "./MarketPlaceItem";
import { useGetCommunityMarketQuery, useGetGardenQuery } from "./queries";
import { getSessionIdOrThrow } from "@/lib/session";

const GetMarket = ({
  communityId,
  myGardenId,
}: {
  communityId: string;
  myGardenId: string;
}) => {
  const communityMarketQuery = useGetCommunityMarketQuery(communityId);

  if (communityMarketQuery.isLoading) {
    return (
      <Box display="flex" justifyContent="center">
        <CircularProgress color="success" />
      </Box>
    );
  }

  if (communityMarketQuery.status === "error") {
    return (
      <>
        Error, this is definitely not our fault! Drop out your laptop and buy a
        new one! :)
      </>
    );
  }

  return (
    <Stack spacing={2}>
      {communityMarketQuery.status === "success" &&
        communityMarketQuery.data.data.map((market, key) => (
          <MarketPlaceItem
            key={key}
            profileImage={market.profileImageUrl!}
            description={market.description!}
            gardenName={market.gardenName!}
            productImage={market.productImageUrl!}
            myGardenId={myGardenId}
            vendorId={market.vendorId!}
          />
        ))}
    </Stack>
  );
};

export const MarketPlace = () => {
  const myGardenQuery = useGetGardenQuery(getSessionIdOrThrow(), false);

  if (myGardenQuery.isLoading) {
    return (
      <Box display="flex" justifyContent="center">
        <CircularProgress color="success" />
      </Box>
    );
  }

  if (myGardenQuery.status === "error") {
    return (
      <>
        Error, this is definitely not our fault! Drop out your laptop and buy a
        new one! :)
      </>
    );
  }

  return (
    <Stack>
      {myGardenQuery.status == "success" && (
        <GetMarket
          communityId={myGardenQuery.data.data.community?.id || ""}
          myGardenId={myGardenQuery.data.data.id!}
        />
      )}
    </Stack>
  );
};
