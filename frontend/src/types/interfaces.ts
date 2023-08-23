import { TUserRegister } from "./types";

export type StateAuthSlice = {
  loading: boolean | undefined;
  userInfo: TUserRegister | null;
  userToken: string | null;
  error: string | undefined;
  success: boolean | undefined;
  username: string | null;
};
