import {
  AddOrderAction,
  SetOrdersAction,
  UnknownAction,
  AddContainerAction,
  SetContainersAction,
} from "./interfaces";

export type Order = {
  id: string;
  spotCoordinates: string;
  creationDate: string;
  closeDate: string;
  productName: string;
  requiredQuantity: number;
  orderStatus: string;
};

export type Container = {
  capacity(
    capacity: any,
    filled: any
  ): import("csstype").Property.BackgroundColor | undefined;
  id: number;
  container: {
    spotCoordinates: string;
    product: {
      id: number;
      productName: string;
      measure: {
        id: number;
        measureName: string;
      };
      capacity: number;
    };
  };
  sensorDate: string;
  quantity: number;
};

export type OrderActions = AddOrderAction | SetOrdersAction | UnknownAction;
export type ContainerActions =
  | AddContainerAction
  | SetContainersAction
  | UnknownAction;
