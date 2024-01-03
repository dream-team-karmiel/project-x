import React from "react";
import { useEffect, useState } from "react";
import { useTheme } from "@mui/material/styles";
import ReactApexChart from "react-apexcharts";

interface MonthlyBarChartData {
  data: Record<string, number>;
}

interface Options {
  chart?: {
    type:
      | "bar"
      | "line"
      | "area"
      | "pie"
      | "donut"
      | "radialBar"
      | "scatter"
      | "bubble"
      | "heatmap"
      | "candlestick"
      | "boxPlot"
      | "radar"
      | "polarArea"
      | "rangeBar"
      | "rangeArea"
      | "treemap";
    height: number;
    toolbar: {
      show: boolean;
    };
  };
  plotOptions?: {
    bar: {
      columnWidth: string;
      borderRadius: number;
    };
  };
  dataLabels?: {
    enabled: boolean;
  };
  colors?: string[];
  xaxis?: {
    categories?: string[];
    axisBorder?: {
      show: boolean;
    };
    axisTicks?: {
      show: boolean;
    };
    labels?: {
      style: {
        colors: string[];
      };
    };
  };
  yaxis?: {
    show?: boolean;
  };
  grid: {
    show?: boolean;
  };
  tooltip?: {
    theme?: string;
  };
}

const barChartOptions: Options = {
  colors: ["#000000"],
  chart: {
    type: "bar",
    height: 365,
    toolbar: {
      show: false,
    },
  },
  plotOptions: {
    bar: {
      columnWidth: "45%",
      borderRadius: 4,
    },
  },
  dataLabels: {
    enabled: false,
  },
  xaxis: {
    axisBorder: {
      show: false,
    },
    axisTicks: {
      show: false,
    },
  },
  yaxis: {
    show: false,
  },

  grid: {
    show: false,
  },
};

const MonthlyBarChart = ({ data }: MonthlyBarChartData) => {
  const theme = useTheme();

  const { primary, secondary } = theme.palette.text;
  const info = theme.palette.info.light;

  const [series, setSeries] = useState([
    {
      data: Object.values(data),
    },
  ]);

  const [options, setOptions] = useState(barChartOptions);

  useEffect(() => {
    setOptions((prevState) => ({
      ...prevState,
      colors: [info || "#000000"],
      xaxis: {
        categories: Object.keys(data),
      },
      tooltip: {
        theme: theme.palette.mode,
      },
    }));

    setSeries([
      {
        data: Object.values(data),
      },
    ]);
  }, [primary, info, secondary, data]);

  return (
    <div id='chart'>
      <ReactApexChart
        options={options}
        series={series}
        type='bar'
        height={365}
      />
    </div>
  );
};

export default MonthlyBarChart;
