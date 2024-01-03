import React, { useState } from "react";
import Grid from "@mui/material/Grid";
import "./Dashboard.scss";
import { Box, Button, Stack, Typography } from "@mui/material";
import AnalyticEcommerce from "../Cards/AnalyticEcommerce";
import { useSelector } from "react-redux";
import { RootState } from "../../types/interfaces";
import MainCard from "../Cards/MainCard";
import IncomeAreaChart from "./IncomeAreaChart";
import MonthlyBarChart from "./MonthlyBarChart";
import RecentOrders from "./RecentOrders";
import LastSensors from "./LastSensors/LastSensors";

interface Accumulator {
  [key: string]: number;
}

const Dashboard: React.FC = () => {
  const [slot, setSlot] = useState("week");

  const orders = useSelector((state: RootState) => state.orders);

  const ordersStateQuantity = orders.reduce<Accumulator>((acc, curr) => {
    const status = curr.orderStatus;
    acc[status] = (acc[status] || 0) + 1;
    return acc;
  }, {});

  const totalOrders = orders.length;
  const totalRevenue = orders.reduce(
    (acc, curr) => acc + curr.requiredQuantity,
    0
  );

  const newOrders = orders.filter((order) => order.orderStatus === "NEW");
  const confirmedOrders = orders.filter(
    (order) => order.orderStatus === "CONFIRMED"
  ).length;

  return (
    <Grid container rowSpacing={4.5} columnSpacing={2.75} mt={"1rem"}>
      {/* row 1 */}
      <Grid item xs={12} sx={{ mb: -2.25 }}>
        <Typography variant='h5'>Dashboard</Typography>
      </Grid>
      <Grid item xs={12} sm={6} md={4} lg={3}>
        <AnalyticEcommerce
          title='Total Orders'
          count={`${totalOrders} pcs`}
          percentage={59.3}
          extra='35,000'
        />
      </Grid>
      <Grid item xs={12} sm={6} md={4} lg={3}>
        <AnalyticEcommerce
          title='Total Quantity'
          count={`${totalRevenue} pcs`}
          percentage={70.5}
          extra='8,900'
        />
      </Grid>
      <Grid item xs={12} sm={6} md={4} lg={3}>
        <AnalyticEcommerce
          title='New Orders'
          count={`${newOrders.length} pcs`}
          percentage={27.4}
          isLoss
          color='warning'
          extra='-1,943'
        />
      </Grid>
      <Grid item xs={12} sm={6} md={4} lg={3}>
        <AnalyticEcommerce
          title='Confirmed Orders'
          count={`${confirmedOrders} pcs`}
          percentage={27.4}
          isLoss
          color='warning'
          extra='-$20,395'
        />
      </Grid>

      <Grid item xs={12} md={7} lg={8}>
        <Grid container alignItems='center' justifyContent='space-between'>
          <Grid item>
            <Typography variant='h5'>Orders by Date</Typography>
          </Grid>
          <Grid item>
            <Stack direction='row' alignItems='center' spacing={0}>
              <Button
                size='small'
                onClick={() => setSlot("month")}
                color={slot === "month" ? "primary" : "secondary"}
                variant={slot === "month" ? "outlined" : "text"}
              >
                Month
              </Button>
              <Button
                size='small'
                onClick={() => setSlot("week")}
                color={slot === "week" ? "primary" : "secondary"}
                variant={slot === "week" ? "outlined" : "text"}
              >
                Week
              </Button>
            </Stack>
          </Grid>
        </Grid>
        <MainCard content={false} sx={{ mt: 1.5 }}>
          <Box sx={{ pt: 1, pr: 2 }}>
            <IncomeAreaChart slot={slot} />
          </Box>
        </MainCard>
      </Grid>

      <Grid item xs={12} md={5} lg={4}>
        <Grid container alignItems='center' justifyContent='space-between'>
          <Grid item>
            <Typography variant='h5'>Orders state</Typography>
          </Grid>
          <Grid item />
        </Grid>
        <MainCard sx={{ mt: 2 }} content={false}>
          <Box sx={{ p: 3, pb: 0 }}>
            <Stack spacing={2}>
              <Typography variant='h6' color='textSecondary'>
                New type of statistics
              </Typography>
              <Typography variant='h3'>
                {`Total ${totalOrders} pcs`}{" "}
              </Typography>
            </Stack>
          </Box>
          <MonthlyBarChart data={ordersStateQuantity} />
        </MainCard>
      </Grid>

      <Grid item xs={12}>
        <Grid container alignItems='center' justifyContent='space-between'>
          <Grid item>
            <Typography variant='h5'>Recent Orders</Typography>
          </Grid>
          <Grid item />
        </Grid>
        <MainCard sx={{ mt: 2 }} content={false}>
          <RecentOrders />
        </MainCard>
      </Grid>
      <Grid item xs={12}>
        <Grid container alignItems='center' justifyContent='space-between'>
          <Grid item>
            <Typography variant='h5'>Last Sensors</Typography>
          </Grid>
          <Grid item />
        </Grid>
        <MainCard sx={{ mt: 2 }} content={false}>
          <LastSensors />
        </MainCard>
      </Grid>
    </Grid>
  );
};

export default Dashboard;
