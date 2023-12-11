import { ContainerActions, Container } from "../../types/types";
import {
  AddContainerAction,
  SetContainersAction,
} from "../../types/interfaces";

const containersReducer = (
  state: Container[] = [],
  action: ContainerActions
): Container[] => {
  switch (action.type) {
    case "ADD_CONTAINER":
      return [...state, (action as AddContainerAction).payload];
    case "SET_CONTAINERS":
      return (action as SetContainersAction).payload;
    default:
      return state;
  }
};

export default containersReducer;
