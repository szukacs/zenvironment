"use client"

import {Page} from "@/components/Page";
import {DisplayTab, SimpleTab} from "@/components/SimpleTab";

export default function Community() {
  const tabs: DisplayTab[] = [
    {
    label: "Timeline",
    content: <>Example tab 1</>
    },
    {
      label: "Gardens",
      content: <>Example tab 2</>
    },
    {
      label: "Challenges",
      content: <>Example tab 3</>
    },
    {
      label: "Market",
      content: <>Example tab 4</>
    }
  ]

  return (
    <Page title="Community">
      <SimpleTab tabs={tabs}/>
    </Page>
  );
}
