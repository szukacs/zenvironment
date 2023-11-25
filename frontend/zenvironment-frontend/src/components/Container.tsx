"use client";

import { Box } from "@mui/material";
import { FC, PropsWithChildren } from "react";

interface ContainerProps extends PropsWithChildren {}

export const Container: FC<ContainerProps> = ({ children }) => {
  return (
    <Box
      sx={(theme) => ({
        maxWidth: "1200px",
        marginLeft: "auto",
        marginRight: "auto",
        padding: theme.spacing(1, 2),
      })}
    >
      {children}
    </Box>
  );
};
