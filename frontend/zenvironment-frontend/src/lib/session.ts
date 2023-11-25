const KEY = "SESSION_ID";

export const getSessionId = () => {
  return window.localStorage.getItem(KEY);
};

export const setSessionId = (id: string) => {
  return window.localStorage.setItem(KEY, id);
};

export const getSessionIdOrThrow = () => {
  const session = window.localStorage.getItem(KEY);
  if (!session) {
    throw new Error("Missing session");
  }
  return session;
};
