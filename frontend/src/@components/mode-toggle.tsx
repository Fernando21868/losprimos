import { Moon, Sun } from "lucide-react";

import { Button } from "./ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "./ui/dropdown-menu";
import { useTheme } from "../@components/theme-provider";

type Props = {
  sidebarIsHidden: boolean;
};

export function ModeToggle({ sidebarIsHidden }: Props) {
  const { setTheme } = useTheme();
  
  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button
          className={`border-none w-full bg-inherit flex ${
            !sidebarIsHidden ? "justify-start" : "justify-center"
          }`}
          variant="outline"
          size="icon"
        >
          <Sun
            className={`${
              !sidebarIsHidden ? "ml-3 mr-4" : ""
            } h-[1.2rem] w-[1.2rem] rotate-0 scale-100 transition-all dark:-rotate-90 dark:scale-0`}
          />
          <Moon
            className={`${
              !sidebarIsHidden ? "ml-3 mr-4" : ""
            } absolute h-[1.2rem] w-[1.2rem] rotate-90 scale-0 transition-all dark:rotate-0 dark:scale-100`}
          />
          <span className="sr-only">Toggle theme</span>
          <span>{!sidebarIsHidden ? "Oscuro/Luminoso" : ""}</span>
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent align="end">
        <DropdownMenuItem onClick={() => setTheme("light")}>
          Dia
        </DropdownMenuItem>
        <DropdownMenuItem onClick={() => setTheme("dark")}>
          Noche
        </DropdownMenuItem>
        <DropdownMenuItem onClick={() => setTheme("system")}>
          Sistema
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  );
}
