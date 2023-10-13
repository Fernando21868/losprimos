import { createAsyncThunk } from "@reduxjs/toolkit";
import { TUserRegister } from "../types/types";
import axios from "axios";

const backendURL = "http://localhost:8080";
const token = localStorage.getItem("userToken");

/**
 * Function to register a user
 * @date 10/10/2023 - 1:02:46 AM
 *
 * @type {*}
 */
export const registerUser = createAsyncThunk(
  "auth/register",
  async (client: TUserRegister, { rejectWithValue }) => {
    try {
      const config = {
        headers: {
          "Content-Type": "application/json",
          "Authorization": token,
        },
      };

      const endpoint = determineEndpoint(client.type);
      console.log("client", client);

      await axios.post(
        `${backendURL}/api/v1/${endpoint}/register`,
        client,
        config
      );
    } catch (error: any) {
      if (error.response && error.response.data.message) {
        console.log(error.response);
        console.log(error.response.data.message);
        return rejectWithValue(error.response.data.message);
      } else {
        console.log(error.response);
        return rejectWithValue(error.message);
      }
    }
  }
);

const determineEndpoint = (typeEmployee: string) => {
  switch (typeEmployee) {
    case "administrator":
      return "administrators";
    case "doctor":
      return "doctors";
    case "nurse":
      return "nurses";
    case "socialWorker":
      return "socialWorkers";
    case "kinesiologist":
      return "kinesiologists";
    case "nutritionist":
      return "nutritionists";
    case "psychologist":
      return "psychologists";
    default:
      return typeEmployee;
  }
};
