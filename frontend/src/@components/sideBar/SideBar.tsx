import { useSelector } from "react-redux";
import { RootState } from "../../store/store";

export const SideBar = () => {
  const { userInfo } = useSelector((state: RootState) => state.auth);

  return <div>Sidebar {userInfo?.firstName}</div>;
};
