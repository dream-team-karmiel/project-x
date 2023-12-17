import { Container } from "../../types/types";

const addContainer = (container: Container) => ({
  type: "ADD_CONTAINER",
  payload: container,
});

const setContainers = (containers: Container[]) => ({
  type: "SET_CONTAINERS",
  payload: containers,
});

export { addContainer, setContainers };
