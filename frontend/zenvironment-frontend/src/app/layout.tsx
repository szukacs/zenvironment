"use client";

import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import "@fontsource/roboto/300.css";
import "@fontsource/roboto/400.css";
import "@fontsource/roboto/500.css";
import "@fontsource/roboto/700.css";
import { Navigation } from "@/components/Navigation";
import { CssBaseline } from "@mui/material";
import { ThemeRegistry } from "@/lib/theme";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { Onboarding } from "@/components/Onboarding";
import { getSessionId } from "@/lib/session";
import { useState } from "react";

const inter = Inter({ subsets: ["latin"] });

const queryClient = new QueryClient();

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const [status, setStatus] = useState<"unauthenticated" | "authenticated">(
    () => {
      const sessionId = getSessionId();
      if (!sessionId) {
        return "unauthenticated";
      }
      return "authenticated";
    }
  );

  return (
    <>
      <ThemeRegistry>
        <CssBaseline />
        <html lang="en">
          <body className={inter.className}>
            <QueryClientProvider client={queryClient}>
              {status === "authenticated" ? (
                <>
                  {children} <Navigation />
                </>
              ) : (
                <Onboarding
                  onSuccess={() => {
                    setStatus("authenticated");
                  }}
                />
              )}
            </QueryClientProvider>
          </body>
        </html>
      </ThemeRegistry>
    </>
  );
}
