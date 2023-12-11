import React from "react";

import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  CartesianGrid,
} from "recharts";

import { RootState } from "../../../types/interfaces";

import { Typography, Box } from "@mui/material";
import { useSelector } from "react-redux";

import { Order } from "../../../types/types";

function BarChartSection() {
  const orders = useSelector((state: RootState) => state.orders);

  const ordersByDate = orders.reduce(
    (acc: { [key: string]: number }, order: Order) => {
      acc[order.creationDate] = (acc[order.creationDate] || 0) + 1;
      return acc;
    },
    {}
  );

  const dataForBarChart = Object.keys(ordersByDate).map((date) => ({
    date,
    orders: ordersByDate[date],
  }));

  return (
    <div style={{ display: "flex", flexDirection: "column", height: "100%" }}>
      <Typography variant='h3' align='center'>
        Orders by Date
      </Typography>
      <Box
        display='flex'
        flexDirection='column'
        justifyContent='center'
        alignItems='center'
        height='100%'
      >
        <BarChart width={500} height={300} data={dataForBarChart}>
          <CartesianGrid strokeDasharray='3 3' />
          <XAxis dataKey='date' />
          <YAxis />
          <Tooltip />
          <Legend />
          <Bar dataKey='orders' fill='#8884d8' />
        </BarChart>
      </Box>
    </div>
  );
}

export default BarChartSection;
