"use client";
import LayoutClient from "../layoutClient/LayoutClient";
import Login from "../../pages/login/Login";
import Register from "../../pages/register/Register";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Home from "../../pages/home/Home";
import ConfirmationEmail from "../../pages/confirmationEmail/ConfirmationEmail";
import AccountVerified from "../../pages/accountVerified/AccountVerified";

const Router = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LayoutClient></LayoutClient>}>
          <Route index element={<Home></Home>}></Route>
          <Route path="login" element={<Login></Login>}></Route>
          <Route path="register" element={<Register></Register>}></Route>
          <Route
            path="confirmationEmail"
            element={<ConfirmationEmail></ConfirmationEmail>}
          ></Route>
          <Route
            path="accountVerified"
            element={<AccountVerified></AccountVerified>}
          ></Route>
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default Router;
