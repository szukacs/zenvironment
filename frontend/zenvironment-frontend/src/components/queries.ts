import { api } from "@/lib/api/api";
import { useQuery } from "@tanstack/react-query";

export const myGardenQueryKeys = {
  root: () => ["root"] as const,
  myGarden: () => [...myGardenQueryKeys.root(), "myGarden"] as const,
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

export function useGetMyCommunityQuery() {
  return useQuery({
    queryKey: myGardenQueryKeys.myCommunity(),
    queryFn: () => api.myCommunity.getMyCommunity(),
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
  root: () => ['root'] as const,
  communityChallenges: () => [...communityQueryKeys.root(), 'communityChallenges'] as const,
  communityMarket: (id: string) => [...communityQueryKeys.root(), 'communityMarket', id] as const
} as const;

export function useGetCommunityChallengesQuery() {
  return useQuery({
    queryKey: communityQueryKeys.communityChallenges(),
    queryFn: () => api.myCommunity.getMyCommunityChallenges()
  })
}

export function useGetCommunityMarketQuery(id: string) {
  return useQuery({
    queryKey: communityQueryKeys.communityMarket(id),
    queryFn: () => api.myCommunity.findAllExchangesBelongingToCommuniy(id)
  })
}