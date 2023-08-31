import { createSlice } from "@reduxjs/toolkit";
import { StateAuthSlice } from "../types/interfaces";
import { registerUser } from "../data/authActions";
import { userLogin } from "../data/authActions";
import { PURGE } from "redux-persist";

const userToken = localStorage.getItem("userToken");
const username = localStorage.getItem("username");

const initialState: StateAuthSlice = {
  loading: false,
  userInfo: null,
  userToken,
  error: undefined,
  success: false,
  username,
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    logout: (state) => {
      localStorage.removeItem("userToken");
      localStorage.removeItem("username");
      state.loading = false;
      state.userInfo = null;
      state.userToken = null;
      state.error = undefined;
    },
    setCredentials: (state, { payload }) => {
      state.userInfo = payload;
    },
    alreadyRegister: (state) => {
      state.success = false;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(registerUser.pending, (state) => {
        state.loading = true;
        state.error = undefined;
      })
      .addCase(registerUser.fulfilled, (state, { payload }) => {
        state.loading = false;
        state.success = true;
      })
      .addCase(registerUser.rejected, (state, { payload }) => {
        state.loading = false;
        if (typeof payload === "string") {
          state.error = payload;
        }
      })
      .addCase(userLogin.pending, (state) => {
        state.loading = true;
        state.error = undefined;
      })
      .addCase(userLogin.fulfilled, (state, { payload }) => {
        state.loading = false;
        state.success = true;
      })
      .addCase(userLogin.rejected, (state, { payload }) => {
        state.loading = false;
        if (typeof payload === "string") {
          state.error = payload;
        }
      });
  },
});

export const { logout, setCredentials, alreadyRegister } = authSlice.actions;
export default authSlice.reducer;
