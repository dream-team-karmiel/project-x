import React, { useEffect } from "react";
import { Container } from "@mui/material";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";

import Header from "./components/Dashboard/Header/Header";
import BarChartSection from "./components/Dashboard/BarChartSection/BarChartSection";
import ChartSection from "./components/Dashboard/ChartSection/ChartSection";
import OverviewSection from "./components/Dashboard/OverviewSection/OverviewSection";
import LastSensors from "./components/Dashboard/LastSensors/LastSensors";
import LastOrders from "./components/Dashboard/LastOrders/LastOrders";

import { ORDERS, CONTAINERS } from "../src/utils/testsObjectsInConstants";

import { useDispatch } from "react-redux";
import Footer from "./components/Footer/Footer";

const App: React.FC = () => {
  const darkTheme = createTheme({
    palette: {
      mode: "dark",
    },
  });

  const dispatch = useDispatch();

  useEffect(() => {
    dispatch({ type: "SET_ORDERS", payload: ORDERS });
    dispatch({ type: "SET_CONTAINERS", payload: CONTAINERS });
  }, []);

  return (
    <ThemeProvider theme={darkTheme}>
      <CssBaseline />
      <Container maxWidth='lg'>
        <Header />
        <Container
          sx={{
            width: "100%",
            display: "flex",
            flexWrap: "wrap",
            justifyContent: "space-between",
            gap: "1rem",
            py: 5,
          }}
        >
          <OverviewSection />
          <ChartSection />
          <BarChartSection />
        </Container>
        <Container
          sx={{
            width: "100%",
            display: "flex",
            flexDirection: "row",
            flexWrap: "wrap",
            justifyContent: "space-between",
            gap: "3rem",
            py: 3,
          }}
        >
          <LastOrders />
          <LastSensors />
        </Container>
        <Footer />
      </Container>
    </ThemeProvider>
  );
};

export default App;
