"use client";

import { BottomNavigation, BottomNavigationAction, Paper } from "@mui/material";
import { FC } from "react";
import GardenIcon from "@mui/icons-material/LocalFlorist";
import CommunityIcon from "@mui/icons-material/People";
import AgricultureIcon from "@mui/icons-material/Agriculture";
import Link from "next/link";
import { usePathname } from "next/navigation";

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
  {
    label: "Gardener AI",
    Icon: AgricultureIcon,
    path: "/garden-advisor",
  },
];

export const Navigation: FC<NavigationProps> = ({}) => {
  const pathname = usePathname();

  const value = items.findIndex((item) =>
    item.path === "/" ? item.path === pathname : pathname.startsWith(item.path)
  );

  return (
    <Paper
      sx={{ position: "fixed", bottom: 0, left: 0, right: 0, zIndex: 1000 }}
      elevation={3}
    >
      <BottomNavigation
        showLabels
        value={value}
        sx={{ backgroundColor: "#0d5e41" }}
      >
        {items.map((item) => (
          <BottomNavigationAction
            key={item.path}
            LinkComponent={Link}
            label={item.label}
            href={item.path}
            icon={<item.Icon />}
            sx={{ color: "#bde7bd", "&.Mui-selected": { color: "#11c786" } }}
          />
        ))}
      </BottomNavigation>
    </Paper>
  );
};
