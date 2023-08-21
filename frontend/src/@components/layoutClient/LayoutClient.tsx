import { Outlet } from "react-router-dom";
import {
  Menubar,
  MenubarContent,
  MenubarItem,
  MenubarMenu,
  MenubarRadioGroup,
  MenubarRadioItem,
  MenubarSeparator,
  MenubarTrigger,
} from "../ui/menubar";
import { Avatar, AvatarFallback, AvatarImage } from "../ui/avatar";
import { Button } from "../ui/button";
import { Link } from "react-router-dom";
import Footer from "../footer/Footer";
import { ModeToggle } from "../mode-toggle";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { RootState } from "../../store/store";
import { useGetUserDetailsQuery } from "../../data/authActions";
import { useEffect } from "react";
import { logout, setCredentials } from "../../store/authSlice";

function LayoutClient() {
  const { userInfo, username } = useSelector((state: RootState) => state.auth);
  const dispatch = useDispatch();

  // automatically authenticate user if token is found
  const { data, isFetching, isLoading } = useGetUserDetailsQuery(
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
      <Menubar className="flex flex-row w-screen h-auto justify-between">
        <div className="flex flex-row">
          <MenubarMenu>
            <MenubarTrigger className="cursor-pointer">File</MenubarTrigger>
          </MenubarMenu>
          <MenubarMenu>
            <MenubarTrigger className="cursor-pointer">Edit</MenubarTrigger>
          </MenubarMenu>
          <MenubarMenu>
            <MenubarTrigger className="cursor-pointer">View</MenubarTrigger>
          </MenubarMenu>
          <MenubarMenu>
            <MenubarTrigger className="cursor-pointer">Profiles</MenubarTrigger>
            <MenubarContent>
              <MenubarRadioGroup value="benoit">
                <MenubarRadioItem value="andy">Andy</MenubarRadioItem>
                <MenubarRadioItem value="benoit">Benoit</MenubarRadioItem>
                <MenubarRadioItem value="Luis">Luis</MenubarRadioItem>
              </MenubarRadioGroup>
              <MenubarSeparator />
              <MenubarItem inset>Edit...</MenubarItem>
              <MenubarSeparator />
              <MenubarItem inset>Add Profile...</MenubarItem>
            </MenubarContent>
          </MenubarMenu>
        </div>
        <div className="flex flex-row gap-4">
          <ModeToggle></ModeToggle>
          {userInfo ? (
            <Button onClick={() => dispatch(logout())}>Cerrar Sesion</Button>
          ) : (
            <Button asChild>
              <Link to="/login">Iniciar Sesion</Link>
            </Button>
          )}
          {isFetching ? (
            `Fetching your profile...`
          ) : userInfo !== null ? (
            <Avatar className="cursor-pointer">
              <AvatarImage src="https://github.com/shadcn.png" />
              <AvatarFallback>{userInfo.username}</AvatarFallback>
            </Avatar>
          ) : (
            ""
          )}
        </div>
      </Menubar>
      <Outlet></Outlet>
      <Footer></Footer>
    </div>
  );
}

export default LayoutClient;
