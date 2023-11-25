"use client";

import { Box, Stack, Typography } from "@mui/material";
import { FC, PropsWithChildren, ReactNode } from "react";

interface PageProps extends PropsWithChildren {
  title: ReactNode;
}

export const Page: FC<PageProps> = ({ children, title }) => {
  return (
    <Stack>
      <Box
        sx={(theme) => ({
          textAlign: "center",
          color: "#052419",
          padding: theme.spacing(1, 2),
        })}
      >
        <Typography variant="h6">{title}</Typography>
      </Box>
      <Box
        sx={(theme) => ({
          padding: theme.spacing(1, 2),
        })}
      >
        {children}
      </Box>
    </Stack>
  );
};
