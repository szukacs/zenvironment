import {api} from "@/lib/api/api";
import {useQuery} from "@tanstack/react-query";


export const myGardenQueryKeys = {
  root: () => ['root'] as const,
  myGarden: () => [...myGardenQueryKeys.root(), 'myGarden'] as const,
  myCommunity: () => [...myGardenQueryKeys.root(), 'myCommunity'] as const,
  plant: (plantId: string) => [...myGardenQueryKeys.root(), 'plant', plantId] as const,
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

export const communityChallengesQueryKeys = {
  root: () => ['root'] as const,
  communityChallenges: () => [...communityChallengesQueryKeys.root(), 'communityChallenges'] as const
} as const;

export function useGetCommunityChallengesQuery() {
  return useQuery({
    queryKey: communityChallengesQueryKeys.communityChallenges(),
    queryFn: () => api.myCommunity.getMyCommunityChallenges()
  })
}

export function useGetPlantById(plantId: string) {
  return useQuery({
    queryKey: myGardenQueryKeys.plant(plantId),
    queryFn: () => api.myGarden.getPlantById(plantId)
  })
}