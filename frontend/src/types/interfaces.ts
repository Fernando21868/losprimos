import { TUserAuth } from "./types";

export type StateAuthSlice = {
  loading: boolean | undefined,
  userInfo: TUserAuth | null,
  userToken: string | null,
  error: string | undefined,
  success: boolean | undefined,
  username: string | null,
}