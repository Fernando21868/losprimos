import { Outlet } from "react-router-dom";
import Footer from "../footer/Footer";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { RootState } from "../../store/store";
import { useGetUserDetailsQuery } from "../../data/authActions";
import { useEffect } from "react";
import { setCredentials } from "../../store/authSlice";
import Header from "../header/Header";

function LayoutClient() {
  const { userInfo, username } = useSelector((state: RootState) => state.auth);
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

  return (
    <div className="flex flex-col">
      <Header userInfo={userInfo}></Header>
      <Outlet></Outlet>
      <Footer></Footer>
    </div>
  );
}

export default LayoutClient;
