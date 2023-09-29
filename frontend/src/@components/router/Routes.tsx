"use client";
import LayoutClient from "../layoutClient/LayoutClient";
import Login from "../../pages/login/Login";
import Register from "../../pages/register/Register";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import ConfirmationEmail from "../../pages/confirmationEmail/ConfirmationEmail";
import AccountVerified from "../../pages/accountVerified/AccountVerified";
import { LayoutAdmin } from "../layoutAdmin/LayoutAdmin";
import { Admin } from "../../pages/admin/Admin";
import { AuthMe } from "../authMe/AuthMe";

const Router = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<AuthMe></AuthMe>}>
          <Route path="/">
            <Route path="login" index element={<Login></Login>}></Route>
            <Route
              path="confirmationEmail"
              element={<ConfirmationEmail></ConfirmationEmail>}
            ></Route>
            <Route
              path="accountVerified"
              element={<AccountVerified></AccountVerified>}
            ></Route>
          </Route>
          <Route path="admin/" element={<LayoutAdmin></LayoutAdmin>}>
            <Route index element={<Admin></Admin>}></Route>
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default Router;
