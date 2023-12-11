import { configureStore } from "@reduxjs/toolkit";
import ordersReducer from "./reducers/ordersReducer";
import containersReducer from "./reducers/containersReducer";

const store = configureStore({
  reducer: {
    orders: ordersReducer,
    containers: containersReducer,
  },
});

export default store;
