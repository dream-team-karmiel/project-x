import React from "react";
import Grid from "@mui/material/Grid";
import "./Dashboard.scss";

type DashboardProps = {
  children: React.ReactNode[];
};

const Dashboard: React.FC<DashboardProps> = ({ children }) => {
  return (
    <Grid container spacing={2}>
      {children.map((child, index) => (
        // <Grid item xs={12} sm={6} md={4} lg={4} key={index}>
        <Grid item sm={12} md={6} lg={4} key={index}>
          {child}
        </Grid>
      ))}
    </Grid>
  );
};

export default Dashboard;
