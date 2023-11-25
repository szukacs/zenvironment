import { getSessionId } from "@/lib/session";
import { FC, useState, PropsWithChildren } from "react";
import { Navigation } from "./Navigation";
import { Container } from "./Container";
import { Onboarding } from "./Onboarding";

interface LayoutProps extends PropsWithChildren {}

const Layout: FC<LayoutProps> = ({ children }) => {
  const [status, setStatus] = useState<"unauthenticated" | "authenticated">(
    () => {
      let sessionId = null;
      if (typeof window !== "undefined") {
        sessionId = getSessionId();
      }

      if (!sessionId) {
        return "unauthenticated";
      }
      return "authenticated";
    }
  );

  return (
    <>
      {status === "authenticated" && (
        <>
          {children} <Navigation />
        </>
      )}
      {status === "unauthenticated" && (
        <Container>
          <Onboarding
            onSuccess={() => {
              setStatus("authenticated");
            }}
          />
        </Container>
      )}
    </>
  );
};

export default Layout;
