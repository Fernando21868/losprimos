import axios from "axios";
import { createAsyncThunk } from "@reduxjs/toolkit";
import { TUserAuth } from "../types/typesUser";

const backendURL = "http://127.0.0.1:8080";

export const registerUser = createAsyncThunk(
  "auth/register",
  async ({ username, password }: TUserAuth, { rejectWithValue }) => {
    try {
      const config = {
        headers: {
          "Content-Type": "application/json",
        },
      };
      await axios.post(
        `${backendURL}/api/v1/clients`,
        { username, password },
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
