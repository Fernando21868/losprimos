import { createSlice } from "@reduxjs/toolkit";
import { StateAuthSlice } from "../types/interfacesSlices";
import { registerUser } from "../data/authActions";

const initialState: StateAuthSlice = {
  loading: false,
  userInfo: undefined,
  userToken: undefined,
  error: undefined,
  success: false,
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(registerUser.pending, (state) => {
        state.loading = true;
        state.error = undefined;
      })
      .addCase(registerUser.fulfilled, (state, { payload }) => {
        state.loading = false;
        state.success = true; // registration successful
      })
      .addCase(registerUser.rejected, (state, { payload }) => {
        state.loading = false;
        if (typeof payload === "string") {
          state.error = payload;
        }
      });
  },
});

export default authSlice.reducer;
