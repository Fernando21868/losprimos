import { TUserAuth } from "./typesUser";

export type StateAuthSlice = {
  loading: boolean | undefined,
  userInfo: TUserAuth | undefined,
  userToken: string | undefined,
  error: string | undefined,
  success: boolean | undefined,
}