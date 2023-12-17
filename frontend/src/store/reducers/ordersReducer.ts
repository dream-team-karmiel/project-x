import { OrderActions, Order } from "../../types/types";
import { AddOrderAction, SetOrdersAction } from "../../types/interfaces";

const ordersReducer = (state: Order[] = [], action: OrderActions): Order[] => {
  switch (action.type) {
    case "ADD_ORDER":
      return [...state, (action as AddOrderAction).payload];
    case "SET_ORDERS":
      return (action as SetOrdersAction).payload;
    default:
      return state;
  }
};

export default ordersReducer;
