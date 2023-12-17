import React from "react";
import { PieChart, Pie, Tooltip, Legend, Cell } from "recharts";
import { RootState } from "../../../types/interfaces";

import { Typography, Box } from "@mui/material";
import { useSelector } from "react-redux";

function ChartSection() {
  const orders = useSelector((state: RootState) => state.orders);

  const data = [
    {
      name: "New",
      value: orders.filter((order) => order.orderStatus === "NEW").length,
    },
    {
      name: "Confirmed",
      value: orders.filter((order) => order.orderStatus === "CONFIRMED").length,
    },
    {
      name: "Canceled",
      value: orders.filter((order) => order.orderStatus === "CANCELED").length,
    },
    {
      name: "Done",
      value: orders.filter((order) => order.orderStatus === "DONE").length,
    },
  ];

  const COLORS = ["#FFBB28", "#0088FE", "#FF8042", "#00C49F"];

  return (
    <div style={{ display: "flex", flexDirection: "column", height: "100%" }}>
      <Typography variant='h3' align='center'>
        Orders state
      </Typography>
      <Box
        display='flex'
        flexDirection='column'
        justifyContent='center'
        alignItems='center'
        height='100%'
      >
        <PieChart width={350} height={300}>
          <Pie
            dataKey='value'
            isAnimationActive={false}
            data={data}
            // cx={100}
            // cy={100}
            outerRadius={100}
            innerRadius={50}
            fill='#8884d8'
            stroke='none'
            label
          >
            {data.map((entry, index) => (
              <Cell
                key={`cell-${index}`}
                fill={COLORS[index % COLORS.length]}
              />
            ))}
          </Pie>

          <Tooltip />
          <Legend />
        </PieChart>
      </Box>
    </div>
  );
}

export default ChartSection;
