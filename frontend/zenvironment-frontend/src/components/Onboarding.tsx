import { FC, useState } from "react";
import { Box, Button, Input, Stack, Typography } from "@mui/material";
import { useMutation } from "@tanstack/react-query";
import { api } from "@/lib/api/api";
import { setSessionId } from "@/lib/session";

interface OnboardingProps {
  onSuccess: VoidFunction;
}

export const Onboarding: FC<OnboardingProps> = ({ onSuccess }) => {
  const [name, setName] = useState("");
  const mutation = useMutation({
    mutationFn: async () => {
      return api.gardens.createGarden({ name });
    },
  });

  return (
    <Box p={4} sx={{ textAlign: "center" }}>
      <Box
        component="img"
        src="/onboarding.png"
        sx={{ width: "300px" }}
        pb={4}
      />
      <Typography variant="h5">
        <strong>What’s your name?</strong>
      </Typography>
      <form
        onSubmit={async (e) => {
          e.preventDefault();
          const data = await mutation.mutateAsync();
          setSessionId(data.data.id!);
          onSuccess();
        }}
      >
        <Stack spacing={4}>
          <Input
            required
            autoFocus
            fullWidth
            sx={{ fontSize: 20 }}
            value={name}
            onChange={(e) => {
              setName(e.target.value);
            }}
          />
          <Button
            disabled={mutation.status === "pending"}
            type="submit"
            size="large"
            variant="contained"
            fullWidth
          >
            Start Adventure!
          </Button>
        </Stack>
      </form>
    </Box>
  );
};
