import { Order, Container } from "./types";

export interface RootState {
  orders: Order[];
  containers: Container[];
}

export interface UnknownAction {
  type: unknown;
}

export interface AddOrderAction {
  type: "ADD_ORDER";
  payload: Order;
}

export interface SetOrdersAction {
  type: "SET_ORDERS";
  payload: Order[];
}

export interface AddContainerAction {
  type: "ADD_CONTAINER";
  payload: Container;
}

export interface SetContainersAction {
  type: "SET_CONTAINERS";
  payload: Container[];
}
