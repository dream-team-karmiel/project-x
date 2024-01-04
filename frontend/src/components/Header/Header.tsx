import React from "react";
import { AppBar, Toolbar, Typography, Button, IconButton } from "@mui/material";
import FactorySharpIcon from "@mui/icons-material/FactorySharp";
import { LightDarkSwitch } from "../LightDarkSwitch/LightDarkSwitch";

import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import { toggleTheme } from "../../store/actionsCreators/actionTheme";
import { RootState } from "../../types/interfaces";

function Header() {
  const dispatch = useDispatch();
  const themeMode = useSelector((state: RootState) => state.themeMode);

  const handleChange = () => {
    dispatch(toggleTheme());
  };
  return (
    <AppBar position='static'>
      <Toolbar>
        <IconButton
          edge='start'
          color='inherit'
          aria-label='logo'
          sx={{ mr: 2 }}
        >
          <FactorySharpIcon />
        </IconButton>
        <Typography variant='h6' component='div' sx={{ flexGrow: 1 }}>
          OptimaDevs
        </Typography>
        <LightDarkSwitch
          sx={{ m: 1 }}
          onChange={handleChange}
          checked={themeMode.theme === "dark"}
        />
        <Button color='inherit'>Войти</Button>
      </Toolbar>
    </AppBar>
  );
}

export default Header;
