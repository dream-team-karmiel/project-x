import { createTheme } from "@mui/material/styles";

const Palette = (mode) => {
  return createTheme({
    palette: {
      mode,
      primary: {
        main: "#0d47a1",
      },
      secondary: {
        main: "#f50057",
      },
      background: {
        default: mode === "light" ? "#fafafa" : "#000000",
      },
    },
  });
};

export default Palette;
