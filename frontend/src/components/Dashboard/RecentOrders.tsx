import React from "react";
import { RootState } from "../../types/interfaces";
import { useSelector } from "react-redux";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
  Box,
  Stack,
} from "@mui/material";
import Dot from "./Dot";
import { Order } from "../../types/types";

function RecentOrders() {
  const orders = useSelector((state: RootState) => state.orders);

  const OrderStatus = ({ order }: { order: Order }) => {
    let color;
    let title = order.orderStatus;

    switch (order.orderStatus) {
      case "NEW":
        color = "warning";

        break;
      case "CONFIRMED":
        color = "success";

        break;
      case "CANCELED":
        color = "error";

        break;
      case "DONE":
        color = "info";

        break;
      default:
        color = "primary";
        title = "None";
    }

    return (
      <Stack direction='row' spacing={1} alignItems='center'>
        <Dot color={color} />
        <Typography>{title}</Typography>
      </Stack>
    );
  };

  return (
    <Box>
      <TableContainer
        sx={{
          width: "100%",
          overflowX: "auto",
          position: "relative",
          display: "block",
          maxWidth: "100%",
          "& td, & th": { whiteSpace: "nowrap" },
        }}
      >
        <Table
          aria-labelledby='tableTitle'
          sx={{
            "& .MuiTableCell-root:first-of-type": {
              pl: 2,
            },
            "& .MuiTableCell-root:last-of-type": {
              pr: 3,
            },
          }}
        >
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
                <TableCell align='left'>
                  <OrderStatus order={order} />
                </TableCell>

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
    </Box>
  );
}

export default RecentOrders;
