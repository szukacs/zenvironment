"use client";

import { BottomNavigation, BottomNavigationAction, Paper } from "@mui/material";
import { FC } from "react";
import GardenIcon from "@mui/icons-material/LocalFlorist";
import FavoriteIcon from "@mui/icons-material/Favorite";
import CommunityIcon from "@mui/icons-material/People";
import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";

interface NavigationProps {}

const items = [
  {
    label: "My Garden",
    Icon: GardenIcon,
    path: "/",
  },
  {
    label: "Community",
    Icon: CommunityIcon,
    path: "/community",
  },
];

export const Navigation: FC<NavigationProps> = ({}) => {
  const pathname = usePathname();

  const value = items.findIndex((item) =>
    item.path === "/" ? item.path === pathname : pathname.startsWith(item.path)
  );

  return (
    <Paper
      sx={{ position: "fixed", bottom: 0, left: 0, right: 0 }}
      elevation={3}
    >
      <BottomNavigation showLabels value={value}>
        {items.map((item) => (
          <BottomNavigationAction
            key={item.path}
            LinkComponent={Link}
            label={item.label}
            href={item.path}
            icon={<item.Icon />}
          />
        ))}
      </BottomNavigation>
    </Paper>
  );
};
