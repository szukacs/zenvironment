import {api} from "@/lib/api/api";
import {useQuery} from "@tanstack/react-query";


export const myGardenQueryKeys = {
  root: () => ['root'] as const,
  myGarden: () => [...myGardenQueryKeys.root(), 'myGarden'] as const,
  myCommunity: () => [...myGardenQueryKeys.root(), 'myCommunity'] as const,
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