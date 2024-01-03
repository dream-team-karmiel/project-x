import { Order, Container } from "./types";
import { TOGGLE_THEME } from "../store/actionsCreators/actionTheme";
import { Action } from "redux";

export interface RootState {
  orders: Order[];
  containers: Container[];
  themeMode: ThemeState;
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

export interface ThemeState {
  theme: string;
}

export type ThemeAction = { type: typeof TOGGLE_THEME } | Action;
