import { createAsyncThunk } from "@reduxjs/toolkit";
import { TUserAuth, TUserRegister } from "../types/types";
import axios from "axios";

const backendURL = "http://localhost:8080";

export const registerUser = createAsyncThunk(
  "auth/register",
  async (client: TUserRegister, { rejectWithValue }) => {
    try {
      const config = {
        headers: {
          "Content-Type": "application/json",
        },
      };
      await axios.post(
        `${backendURL}/api/v1/clients/register`,
        client,
        config
      );
    } catch (error: any) {
      if (error.response && error.response.data.message) {
        return rejectWithValue(error.response.data.message);
      } else {
        return rejectWithValue(error.message);
      }
    }
  }
);
