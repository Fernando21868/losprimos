import { SideBar } from "../sideBar/SideBar";
import { Outlet } from "react-router-dom";


export const LayoutAdmin = () => {
  return (
    <div className="flex flex-row">
      <SideBar></SideBar>
      <div className="w-screen h-screen ml-[15%] sm:ml-[5%]">
        <Outlet></Outlet>
      </div>
      {/* <Footer></Footer> */}
    </div>
  );
};
