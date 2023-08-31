import { SideBar } from "../sideBar/SideBar";
import { Outlet } from "react-router-dom";
import Footer from "../footer/Footer";

export const LayoutAdmin = () => {
  return (
    <div className="flex flex-col">
      <SideBar></SideBar>
      <Outlet></Outlet>
      <Footer></Footer>
    </div>
  );
};
