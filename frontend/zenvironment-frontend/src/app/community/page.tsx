import { Page } from "@/components/Page";
import {DisplayTab, SimpleTab} from "@/components/SimpleTab";
import {Typography} from "@mui/material";

export default function Community() {
  const tabs: DisplayTab[] = [{
    label: "Example 1",
    content: <>Example tab 1</>
  },
    {
      label: "Example 2",
      content: <>Example tab 2</>
    }]

  return (
    <Page title="Community">
      <Typography>My Comunity</Typography>
      <SimpleTab tabs={tabs}/>
    </Page>
  );
}
