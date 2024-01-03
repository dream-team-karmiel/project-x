import React from "react";

import { Box, Chip, Grid, Stack, Typography } from "@mui/material";
import MainCard from "./MainCard";

import TrendingUpIcon from "@mui/icons-material/TrendingUp";
import TrendingDownIcon from "@mui/icons-material/TrendingDown";

interface AnalyticEcommerceProps {
  color?:
    | "primary"
    | "secondary"
    | "error"
    | "info"
    | "success"
    | "warning"
    | "default";
  title: string;
  count: string;
  percentage?: number;
  isLoss?: boolean;
  extra: React.ReactNode | string;
}

const AnalyticEcommerce: React.FC<AnalyticEcommerceProps> = ({
  color = "primary",
  title,
  count,
  percentage,
  isLoss,
  extra,
}) => (
  <MainCard contentSX={{ p: 2.25 }}>
    <Stack spacing={0.5}>
      <Typography variant='h6' color='textSecondary'>
        {title}
      </Typography>
      <Grid container alignItems='center'>
        <Grid item>
          <Typography variant='h4' color='inherit'>
            {count}
          </Typography>
        </Grid>
        {percentage && (
          <Grid item>
            <Chip
              variant='filled'
              color={color}
              icon={
                <>
                  {!isLoss && (
                    <TrendingUpIcon
                      style={{ fontSize: "0.75rem", color: "inherit" }}
                    />
                  )}
                  {isLoss && (
                    <TrendingDownIcon
                      style={{ fontSize: "0.75rem", color: "inherit" }}
                    />
                  )}
                </>
              }
              label={`${percentage}%`}
              sx={{ ml: 1.25, pl: 1 }}
              size='small'
            />
          </Grid>
        )}
      </Grid>
    </Stack>
    <Box sx={{ pt: 2.25 }}>
      <Typography variant='caption' color='textSecondary'>
        You made an extra{" "}
        <Typography
          component='span'
          variant='caption'
          sx={{ color: `${color || "primary"}.main` }}
        >
          {extra}
        </Typography>{" "}
        this day
      </Typography>
    </Box>
  </MainCard>
);

export default AnalyticEcommerce;
