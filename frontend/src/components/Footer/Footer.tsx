import React from "react";
import { Box, Container, Typography, Link } from "@mui/material";

function Footer() {
  return (
    <Box
      sx={{
        backgroundImage:
          "linear-gradient(rgba(255, 255, 255, 0.09), rgba(255, 255, 255, 0.09))",
        py: 3,
      }}
    >
      <Container maxWidth='lg'>
        <Typography variant='body1' color='text.secondary' align='center'>
          © OptimaDevs
        </Typography>
        <Typography variant='body2' color='text.secondary' align='center'>
          <Link color='inherit' href='https://google.com'>
            All rights reserved
          </Link>
        </Typography>
      </Container>
    </Box>
  );
}

export default Footer;
