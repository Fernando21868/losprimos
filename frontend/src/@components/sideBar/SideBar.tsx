// import { useSelector } from "react-redux";
// import { RootState } from "../../store/store";
import { Menubar, MenubarMenu, MenubarTrigger } from "../ui/menubar";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faArrowLeft,
  faArrowRight,
  faBedPulse,
  faRightFromBracket,
} from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { ModeToggle } from "../mode-toggle";
import { useDispatch } from "react-redux";
import { logout } from "../../store/authSlice";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "../ui/alert-dialog";

/**
 * Sidebar layout for the site
 * @date 10/6/2023 - 3:07:38 AM
 *
 * @returns {*}
 */
export const SideBar = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [sidebarIsHidden, setSidebarIsHidden] = useState(false);

  /**
   * Function to hide or show sidebar
   * @date 10/3/2023 - 12:53:46 AM
   */
  function onSideBar() {
    setSidebarIsHidden((previousState) => !previousState);
  }

  return (
    <Menubar
      className={`flex flex-col h-screen fixed z-50 ${
        sidebarIsHidden ? "w-[15%]" : "w-[50%]"
      } ${sidebarIsHidden ? "sm:max-w-[5%]" : "sm:max-w-[12%]"}`}
    >
      <MenubarMenu>
        {!sidebarIsHidden ? (
          <>
            <ModeToggle sidebarIsHidden={sidebarIsHidden}></ModeToggle>
          </>
        ) : (
          <>
            <ModeToggle sidebarIsHidden={sidebarIsHidden}></ModeToggle>
          </>
        )}
      </MenubarMenu>
      <MenubarMenu>
        <MenubarTrigger
          onClick={onSideBar}
          className={`w-full flex flex-row gap-4 cursor-pointer hover:bg-accent ${
            !sidebarIsHidden ? "justify-start" : "justify-center"
          } items-center py-2`}
        >
          {!sidebarIsHidden ? (
            <>
              <FontAwesomeIcon icon={faArrowLeft} />
              <span>Ocultar barra</span>
            </>
          ) : (
            <>
              <FontAwesomeIcon icon={faArrowRight} />
            </>
          )}
        </MenubarTrigger>
      </MenubarMenu>
      <MenubarMenu>
        <MenubarTrigger
          className="w-full flex py-2 cursor-pointer hover:bg-accent"
          onClick={() => setSidebarIsHidden(true)}
        >
          {!sidebarIsHidden ? (
            <NavLink
              className="flex gap-4 justify-start items-center"
              to={"patientAdmission"}
            >
              <FontAwesomeIcon icon={faBedPulse} />
              <span className="text-start">Admision del paciente</span>
            </NavLink>
          ) : (
            <NavLink
              className={({ isActive }) =>
                isActive ? "w-full justify-center" : "w-full justify-center"
              }
              to={"patientAdmission"}
            >
              <FontAwesomeIcon icon={faBedPulse} />
            </NavLink>
          )}
        </MenubarTrigger>
      </MenubarMenu>
      <MenubarMenu>
        <AlertDialog>
          <AlertDialogTrigger className="w-full py-2 flex select-none items-center rounded-sm px-3 text-sm font-medium outline-none focus:bg-accent focus:text-accent-foreground data-[state=open]:bg-accent data-[state=open]:text-accent-foreground cursor-pointer hover:bg-accent">
            {!sidebarIsHidden ? (
              <div className="flex flex-row gap-4 justify-center items-center">
                <FontAwesomeIcon icon={faRightFromBracket} />
                <span>Cerrar Sesion</span>
              </div>
            ) : (
              <div className="flex w-full justify-center">
                <FontAwesomeIcon icon={faRightFromBracket} />
              </div>
            )}
          </AlertDialogTrigger>
          <AlertDialogContent>
            <AlertDialogHeader>
              <AlertDialogTitle>
                ¿Esta seguro que desea cerrar su sesion?
              </AlertDialogTitle>
              <AlertDialogDescription>
                Esta accion cerrara su sesion.
              </AlertDialogDescription>
            </AlertDialogHeader>
            <AlertDialogFooter>
              <AlertDialogCancel>Cancelar</AlertDialogCancel>
              <AlertDialogAction
                onClick={() => {
                  dispatch(logout());
                  navigate("/");
                }}
              >
                Continuar
              </AlertDialogAction>
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialog>
      </MenubarMenu>
    </Menubar>
  );
};
