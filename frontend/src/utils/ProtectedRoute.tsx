import React, { ReactNode, useEffect, useState } from "react";
import { Outlet, Route, useNavigate } from "react-router-dom";

type Props = {
  children?: React.ReactNode;
};

export const ProtectedRoute: React.FC<Props> = ({ children }) => {
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const checkUserToken = () => {
    const userToken = localStorage.getItem("userToken");
    const username = localStorage.getItem("username");

    const verifyTokenAndUsername =
      !userToken ||
      userToken === "undefined" ||
      !username ||
      username === "undefined";

    if (verifyTokenAndUsername) {
      setIsLoggedIn(false);
      return navigate("/");
    }
    setIsLoggedIn(true);
  };

  useEffect(() => {
    checkUserToken();
  }, [isLoggedIn]);

  // const { userInfo } = useSelector((state: RootState) => state.auth);

  return (
    <React.Fragment>{isLoggedIn ? <Outlet></Outlet> : null}</React.Fragment>
  );
};
