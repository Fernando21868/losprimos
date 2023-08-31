import { useDispatch } from "react-redux";
import { RootState } from "../../store/store";
import { useGetUserDetailsQuery } from "../../data/authActions";
import { useSelector } from "react-redux";
import { useEffect } from "react";
import { setCredentials } from "../../store/authSlice";
import { Outlet } from "react-router-dom";

export const AuthMe = () => {
  const { username } = useSelector((state: RootState) => state.auth);
  const dispatch = useDispatch();

  // automatically authenticate user if token is found
  const { data } = useGetUserDetailsQuery(
    {
      username,
    },
    {
      // perform a refetch every 15mins
      pollingInterval: 900000,
    }
  );

  useEffect(() => {
    if (data) {
      dispatch(setCredentials(data));
    }
  }, [data, dispatch]);


  return <Outlet></Outlet>;
};
