import { api } from "@/lib/api/api";
import { removeSession } from "@/lib/session";
import { useQuery } from "@tanstack/react-query";
import { useEffect } from "react";

export const myGardenQueryKeys = {
  root: () => ["root"] as const,
  myGarden: () => [...myGardenQueryKeys.root(), "myGarden"] as const,
  garden: (id: string) => [...myGardenQueryKeys.root(), "garden"] as const,
  myCommunity: () => [...myGardenQueryKeys.root(), "myCommunity"] as const,
  plant: (plantId: string) =>
    [...myGardenQueryKeys.root(), "plant", plantId] as const,
  plantTypes: () => [...myGardenQueryKeys.root(), "plantTypes"] as const,
} as const;

export function useGetMyGardenQuery() {
  return useQuery({
    queryKey: myGardenQueryKeys.myGarden(),
    queryFn: () => api.myGarden.getMyGarden(),
  });
}

export function useGetGardenQuery(id: string, automaticRefreshData: boolean) {
  const query = useQuery({
    queryKey: myGardenQueryKeys.myGarden(),
    queryFn: () => api.gardens.getGardenById(id),
    refetchInterval: automaticRefreshData ? 10000 : false,
  });

  useEffect(() => {
    if (query.isError) {
      removeSession();
      document.location.reload();
    }
  }, [query.isError]);

  return query;
}

export function useGetMyCommunityQuery() {
  return useQuery({
    queryKey: myGardenQueryKeys.myCommunity(),
    queryFn: () => api.myCommunity.getMyCommunity(),
    refetchInterval: 5000,
  });
}

export function useGetPlantById(plantId: string) {
  return useQuery({
    queryKey: myGardenQueryKeys.plant(plantId),
    queryFn: () => api.myGarden.getPlantById(plantId),
  });
}

export function useGetPlantTypes() {
  return useQuery({
    queryKey: myGardenQueryKeys.plantTypes(),
    queryFn: () => api.plantTypes.getPlantTypes(),
  });
}

export const communityQueryKeys = {
  root: () => ["communityRoot"] as const,
  communityChallenges: () =>
    [...communityQueryKeys.root(), "communityChallenges"] as const,
  communityMarket: (id: string) =>
    [...communityQueryKeys.root(), "communityMarket", id] as const,
} as const;

export function useGetCommunityChallengesQuery() {
  return useQuery({
    queryKey: communityQueryKeys.communityChallenges(),
    queryFn: () => api.myCommunity.getMyCommunityChallenges(),
  });
}

export function useGetCommunityMarketQuery(id: string) {
  return useQuery({
    queryKey: communityQueryKeys.communityMarket(id),
    queryFn: () => api.myCommunity.findAllExchangesBelongingToCommuniy(id),
  });
}
