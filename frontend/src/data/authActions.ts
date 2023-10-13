import { createAsyncThunk } from "@reduxjs/toolkit";
import { TUserAuth } from "../types/types";
import axios from "axios";
import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { RootState } from "../store/store";

const backendURL = "http://localhost:8080";

/**
 * Function to login
 * @date 10/10/2023 - 1:03:15 AM
 *
 * @type {*}
 */
export const userLogin = createAsyncThunk(
  "auth/login",
  async ({ username, password }: TUserAuth, { rejectWithValue }) => {
    try {
      // configure header's Content-Type as JSON
      const config = {
        headers: {
          "Content-Type": "application/json",
        },
      };
      const { data } = await axios.post(
        `${backendURL}/api/v1/sign-in`,
        { username, password },
        config
      );
      // store user's token in local storage
      localStorage.setItem("userToken", data.token);
      localStorage.setItem("username", data.username);
      return data;
    } catch (error: any) {
      // return custom error message from API if any
      if (error.response && error.response.data.message) {
        return rejectWithValue(error.response.data.message);
      } else {
        return rejectWithValue(error.message);
      }
    }
  }
);


/**
 * Function to authenticate
 * @date 10/10/2023 - 1:03:25 AM
 *
 * @type {*}
 */
export const authApi = createApi({
  reducerPath: "authApi",
  baseQuery: fetchBaseQuery({
    // base url of backend API
    baseUrl: "http://localhost:8080/",
    // prepareHeaders is used to configure the header of every request and gives access to getState which we use to include the token from the store
    prepareHeaders: (headers, { getState }) => {
      const token = (getState() as RootState).auth.userToken;
      if (token) {
        // include token in req header
        headers.set("authorization", `Bearer ${token}`);
        return headers;
      }
    },
  }),
  endpoints: (builder) => ({
    getUserDetails: builder.query({
      query: ({ username }) => ({
        url: `api/v1/profile/${username}`,
        method: "GET",
      }),
    }),
  }),
});

export const { useGetUserDetailsQuery } = authApi;
