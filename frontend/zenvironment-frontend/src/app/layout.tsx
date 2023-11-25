"use client";

import { Inter } from "next/font/google";
import "./globals.css";
import "@fontsource/roboto/300.css";
import "@fontsource/roboto/400.css";
import "@fontsource/roboto/500.css";
import "@fontsource/roboto/700.css";
import { CssBaseline } from "@mui/material";
import { ThemeRegistry } from "@/lib/theme";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { Suspense, useState } from "react";
import dynamic from "next/dynamic";
const Layout = dynamic(() => import("@/components/Layout"), { ssr: false });

const inter = Inter({ subsets: ["latin"] });

const queryClient = new QueryClient();

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <>
      <ThemeRegistry>
        <CssBaseline />
        <html lang="en">
          <body className={inter.className}>
            <QueryClientProvider client={queryClient}>
              <Layout>{children}</Layout>
            </QueryClientProvider>
          </body>
        </html>
      </ThemeRegistry>
    </>
  );
}
