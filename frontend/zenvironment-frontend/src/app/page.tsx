import { Garden } from "@/components/Garden";
import { Page } from "@/components/Page";
import { Box } from "@mui/material";

export default function MyGarden() {
  return (
    <Page title="My Garden">
      <Box mt={4}>
        <Garden />
      </Box>
    </Page>
  );
}
