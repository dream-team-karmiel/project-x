import React from "react";
import { RootState } from "../../../types/interfaces";
import { useSelector } from "react-redux";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Typography,
} from "@mui/material";

function LastOrders() {
  const orders = useSelector((state: RootState) => state.orders);

  return (
    <div>
      <Typography variant='h3'>Last Orders</Typography>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell align='center'>Status</TableCell>
              <TableCell align='center'>Spot/Product Name</TableCell>
              <TableCell align='center'>Quantity</TableCell>
              <TableCell align='center'>Dates</TableCell>
              <TableCell align='center'>Order Id</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {orders.map((order) => (
              <TableRow key={order.id}>
                <TableCell align='left'>{order.orderStatus}</TableCell>
                <TableCell align='center'>
                  {order.spotCoordinates} / {order.productName}
                </TableCell>
                <TableCell align='center'>{order.requiredQuantity}</TableCell>
                <TableCell align='center'>
                  {order.creationDate}
                  <br />
                  {order.closeDate}
                </TableCell>
                <TableCell align='center'>{order.id}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
}

export default LastOrders;
