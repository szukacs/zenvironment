"use client";

import { Box, Stack, Typography } from "@mui/material";
import { FC, PropsWithChildren, ReactNode } from "react";
import { Container } from "./Container";

interface PageProps extends PropsWithChildren {
  title?: ReactNode;
}

export const Page: FC<PageProps> = ({ children, title }) => {
  return (
    <Box sx={{ paddingBottom: "100px" }}>
      <Box
        sx={(theme) => ({
          textAlign: "center",
          color: "#052419",
          padding: theme.spacing(1, 2),
        })}
      >
        {title && <Typography variant="h6">{title}</Typography>}
      </Box>
      <Container>{children}</Container>
    </Box>
  );
};
