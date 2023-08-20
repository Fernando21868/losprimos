"use client";
import LayoutClient from "../layoutClient/LayoutClient";
import Login from "../../pages/login/Login";
import Register from "../../pages/register/Register";
import { BrowserRouter, Route, Routes } from "react-router-dom";


const Router = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LayoutClient></LayoutClient>}>
          <Route path="login" element={<Login></Login>}></Route>
          <Route path="register" element={<Register></Register>}></Route>
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default Router;
