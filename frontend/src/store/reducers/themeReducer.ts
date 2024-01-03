import { ThemeState } from "../../types/interfaces";
import { ThemeAction } from "../../types/interfaces";
import { TOGGLE_THEME } from "../actionsCreators/actionTheme";

const initialState = {
  theme: "light",
};

const themeReducer = (
  state = initialState,
  action: ThemeAction
): ThemeState => {
  switch (action.type) {
    case TOGGLE_THEME:
      return {
        ...state,
        theme: state.theme === "light" ? "dark" : "light",
      };
    default:
      return state;
  }
};

export default themeReducer;
