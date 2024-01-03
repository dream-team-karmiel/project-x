import React from "react";
import { Box, Container, Typography, Link, useTheme } from "@mui/material";

function Footer() {
  const theme = useTheme();
  return (
    <Box
      sx={{
        backgroundColor: `${
          theme.palette.mode === "light"
            ? theme.palette.primary.main
            : theme.palette.grey[900]
        }`,
        py: 3,
      }}
    >
      <Container
        maxWidth='lg'
        sx={{
          display: "flex",
          flexDirection: { xs: "column", sm: "row" },
          alignItems: "center",
          justifyContent: "space-between",
          textAlign: { xs: "center", sm: "left" },
        }}
      >
        <Typography variant='body1' sx={{ mb: { xs: 1, sm: 0 } }}>
          © OptimaDevs
        </Typography>
        <Typography variant='body2'>
          <Link color='inherit' href='https://google.com'>
            All rights reserved
          </Link>
        </Typography>
      </Container>
    </Box>
  );
}

export default Footer;
