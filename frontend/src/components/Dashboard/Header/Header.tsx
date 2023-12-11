import React from "react";
import { AppBar, Toolbar, Typography, Button, IconButton } from "@mui/material";
import FactorySharpIcon from "@mui/icons-material/FactorySharp";

function Header() {
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
        <Button color='inherit'>Войти</Button>
      </Toolbar>
    </AppBar>
  );
}

export default Header;
