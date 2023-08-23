import { Link } from "react-router-dom";
import { ModeToggle } from "../mode-toggle";
import { Button } from "../ui/button";
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
import { useDispatch } from "react-redux";
import { logout } from "../../store/authSlice";
import { TUserRegister } from "../../types/types";
import { useEffect } from "react";

type Props = {
  userInfo: TUserRegister | null;
};

const Header = ({ userInfo }: Props) => {
  const dispatch = useDispatch();

  return (
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
        {userInfo !== null ? (
          <Avatar className="cursor-pointer">
            <AvatarImage src="https://github.com/shadcn.png" />
            <AvatarFallback>{userInfo.username}</AvatarFallback>
          </Avatar>
        ) : (
          ""
        )}
      </div>
    </Menubar>
  );
};

export default Header;
