import { configureStore } from "@reduxjs/toolkit";
import ordersReducer from "./reducers/ordersReducer";
import containersReducer from "./reducers/containersReducer";
import themeReducer from "./reducers/themeReducer";
import { RootState } from "../types/interfaces";

const loadState = (): RootState | undefined => {
  try {
    const serializedState = localStorage.getItem("state");
    if (serializedState === null) {
      return undefined;
    }
    return JSON.parse(serializedState);
  } catch (err) {
    return undefined;
  }
};

const saveState = (state: RootState) => {
  try {
    const serializedState = JSON.stringify(state);
    localStorage.setItem("state", serializedState);
  } catch (err) {
    console.log("Ошибка записи в localStorage", err);
  }
};

const persistedState = loadState();

const store = configureStore({
  reducer: {
    orders: ordersReducer,
    containers: containersReducer,
    themeMode: themeReducer,
  },
  preloadedState: persistedState,
});

store.subscribe(() => {
  saveState({
    orders: store.getState().orders,
    containers: store.getState().containers,
    themeMode: store.getState().themeMode,
  });
});

export default store;
