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

function LayoutClient() {
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
          <Avatar className="cursor-pointer">
            <AvatarImage src="https://github.com/shadcn.png" />
            <AvatarFallback>CN</AvatarFallback>
          </Avatar>
          <Button asChild>
            <Link to="/login">Iniciar Sesion</Link>
          </Button>
        </div>
      </Menubar>
      <Outlet></Outlet>
      <Footer></Footer>
    </div>
  );
}

export default LayoutClient;
