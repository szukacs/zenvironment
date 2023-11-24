import {api} from "@/lib/api/api";
import {useQuery} from "@tanstack/react-query";


export const myGardenQueryKeys = {
  root: () => ['mygarden'] as const
} as const;

export function useGetMyGardenQuery() {
  return useQuery({
    queryKey: myGardenQueryKeys.root(),
    queryFn: () => api.myGarden.getMyGarden(),
  });
}