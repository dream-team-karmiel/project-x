import { Order } from "../../types/types";

const addOrder = (order: Order) => ({
  type: "ADD_ORDER",
  payload: order,
});

const setOrders = (orders: Order[]) => ({
  type: "SET_ORDERS",
  payload: orders,
});

export { addOrder, setOrders };
