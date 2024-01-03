import React, { useEffect } from "react";
import { Container } from "@mui/material";
import CssBaseline from "@mui/material/CssBaseline";
import Header from "./components/Dashboard/Header/Header";
import { ORDERS, CONTAINERS } from "../src/utils/testsObjectsInConstants";
import { useDispatch } from "react-redux";
import Footer from "./components/Footer/Footer";
import Dashboard from "./components/Dashboard/Dashboard";
import ThemeCustomization from "../src/components/themes/index.js";

const App: React.FC = () => {
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch({ type: "SET_ORDERS", payload: ORDERS });
    dispatch({ type: "SET_CONTAINERS", payload: CONTAINERS });
  }, []);

  return (
    <ThemeCustomization>
      <CssBaseline />
      <Container
        maxWidth='lg'
        sx={{
          padding: 0,
        }}
      >
        <Header />
        <Dashboard />

        <Footer />
      </Container>
    </ThemeCustomization>
  );
};

export default App;
