import { Page } from "@/components/Page";
import {useGetMyGardenQuery} from "@/components/mygardenQueries";

export default function MyGarden() {
  const myGardenQuery = useGetMyGardenQuery()
  return (
    <Page title="My Garden">
      <span>my garden</span>
    </Page>
  );
}
