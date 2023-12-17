import React from "react";
import "./OverviewSection.scss";
import { RootState } from "../../../types/interfaces";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";

import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import AssignmentIcon from "@mui/icons-material/Assignment";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import DoneIcon from "@mui/icons-material/Done";
import PendingIcon from "@mui/icons-material/Pending";
import CancelIcon from "@mui/icons-material/Cancel";
import QueryStatsIcon from "@mui/icons-material/QueryStats";

import { Typography } from "@mui/material";
import { useSelector } from "react-redux";

function OverviewSection() {
  const orders = useSelector((state: RootState) => state.orders);

  const totalOrders = orders.length;
  const totalRevenue = orders.reduce(
    (acc, curr) => acc + curr.requiredQuantity,
    0
  );

  const newOrders = orders.filter((order) => order.orderStatus === "NEW");
  const confirmedOrders = orders.filter(
    (order) => order.orderStatus === "CONFIRMED"
  ).length;
  const canceledOrders = orders.filter(
    (order) => order.orderStatus === "CANCELED"
  ).length;
  const finishedOrders = orders.filter(
    (order) => order.orderStatus === "DONE"
  ).length;

  return (
    <div>
      <Typography variant='h3'>Overview</Typography>
      <List>
        <ListItem>
          <ListItemIcon>
            <AssignmentIcon />
          </ListItemIcon>
          <ListItemText
            primary='Total Orders'
            secondary={`${totalOrders} pcs`}
          />
        </ListItem>
        <ListItem>
          <ListItemIcon>
            <QueryStatsIcon />
          </ListItemIcon>
          <ListItemText
            primary='Total Quantity'
            secondary={`${totalRevenue} pcs`}
          />
        </ListItem>

        <ListItem>
          <ListItemIcon>
            <PendingIcon />
          </ListItemIcon>
          <ListItemText
            primary='New Orders'
            secondary={`${newOrders.length} pcs`}
          />
        </ListItem>

        <ListItem>
          <ListItemIcon>
            <ShoppingCartIcon />
          </ListItemIcon>
          <ListItemText
            primary='Confirmed Orders'
            secondary={`${confirmedOrders} pcs`}
          />
        </ListItem>

        <ListItem>
          <ListItemIcon>
            <CancelIcon />
          </ListItemIcon>
          <ListItemText
            primary='Canceled Orders'
            secondary={`${canceledOrders} pcs`}
          />
        </ListItem>

        <ListItem>
          <ListItemIcon>
            <DoneIcon />
          </ListItemIcon>
          <ListItemText
            primary='Finished Orders'
            secondary={`${finishedOrders} pcs`}
          />
        </ListItem>
      </List>
    </div>
  );
}

export default OverviewSection;
