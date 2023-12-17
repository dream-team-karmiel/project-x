import React from "react";
import "./LastSensors.scss";
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
  LinearProgress,
} from "@mui/material";

function LastSensors() {
  const sensors = useSelector((state: RootState) => state.containers);

  return (
    <div>
      <Typography variant='h3'>Last Sensors</Typography>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell align='center'>Sensor ID</TableCell>
              <TableCell align='center'>Product name</TableCell>
              <TableCell align='center'>Product capacity </TableCell>
              <TableCell align='center'>Quantity</TableCell>
              <TableCell align='center'>Measure</TableCell>
              <TableCell align='center'>Fill percentage</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {sensors.map((sensor) => (
              <TableRow key={sensor.id}>
                <TableCell align='center'>{sensor.id}</TableCell>
                <TableCell align='center'>
                  {sensor.container.product.productName}
                </TableCell>
                <TableCell align='center'>
                  {sensor.container.product.capacity}
                </TableCell>
                <TableCell align='center'>{sensor.quantity}</TableCell>
                <TableCell align='center'>
                  {sensor.container.product.measure.measureName}
                </TableCell>
                <TableCell>
                  <LinearProgress
                    variant='determinate'
                    value={getFillPercentage(
                      Number(sensor.container.product.capacity),
                      Number(sensor.quantity)
                    )}
                    style={{ width: "100%" }}
                    color={getColor(
                      Number(sensor.container.product.capacity),
                      Number(sensor.quantity)
                    )}
                  />
                  {`${getFillPercentage(
                    Number(sensor.container.product.capacity),
                    Number(sensor.quantity)
                  ).toFixed(0)}%`}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
}

function getFillPercentage(capacity: number, filled: number) {
  return Math.round((filled / capacity) * 100);
}

function getColor(capacity: number, filled: number) {
  const percentage = getFillPercentage(capacity, filled);
  if (percentage > 75) {
    return "success";
  } else if (percentage > 50) {
    return "info";
  }
  return "error";
}

export default LastSensors;
