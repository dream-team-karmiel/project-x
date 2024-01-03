import { configureStore } from "@reduxjs/toolkit";
import ordersReducer from "./reducers/ordersReducer";
import containersReducer from "./reducers/containersReducer";
import themeReducer from "./reducers/themeReducer";

const store = configureStore({
  reducer: {
    orders: ordersReducer,
    containers: containersReducer,
    themeMode: themeReducer,
  },
});

export default store;
