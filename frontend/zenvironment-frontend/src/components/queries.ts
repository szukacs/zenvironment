import { api } from "@/lib/api/api";
import { useQuery } from "@tanstack/react-query";

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

export function useGetGardenQuery(id: string) {
  return useQuery({
    queryKey: myGardenQueryKeys.myGarden(),
    queryFn: () => api.gardens.getGardenById(id),
  });
}

export function useGetMyCommunityQuery() {
  return useQuery({
    queryKey: myGardenQueryKeys.myCommunity(),
    queryFn: () => api.myCommunity.getMyCommunity(),
  });
}

export const communityChallengesQueryKeys = {
  root: () => ["root"] as const,
  communityChallenges: () =>
    [...communityChallengesQueryKeys.root(), "communityChallenges"] as const,
} as const;

export function useGetCommunityChallengesQuery() {
  return useQuery({
    queryKey: communityChallengesQueryKeys.communityChallenges(),
    queryFn: () => api.myCommunity.getMyCommunityChallenges(),
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
