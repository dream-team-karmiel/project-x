import React, { forwardRef, ReactNode } from "react";

import {
  Card,
  CardContent,
  CardHeader,
  Divider,
  Typography,
  SxProps,
  Theme,
} from "@mui/material";

interface MainCardProps {
  border?: boolean;
  boxShadow?: boolean;
  children?: ReactNode;
  content?: boolean;
  contentSX?: SxProps<Theme>;
  darkTitle?: boolean;
  elevation?: number;
  secondary?: ReactNode;
  shadow?: string;
  sx?: SxProps<Theme>;
  title?: ReactNode | string;
  codeHighlight?: boolean;
}

const headerSX = {
  p: 2.5,
  "& .MuiCardHeader-action": { m: "0px auto", alignSelf: "center" },
};

const MainCard = forwardRef<HTMLDivElement, MainCardProps>(
  (
    {
      children,
      content = true,
      contentSX = {},
      darkTitle,
      elevation,
      secondary,
      sx = {},
      title,
      codeHighlight,
      ...others
    },
    ref
  ) => {
    return (
      <Card
        elevation={elevation || 0}
        ref={ref}
        {...others}
        sx={{
          ...sx,
        }}
      >
        {!darkTitle && title && (
          <CardHeader
            sx={headerSX}
            titleTypographyProps={{ variant: "subtitle1" }}
            title={title}
            action={secondary}
          />
        )}
        {darkTitle && title && (
          <CardHeader
            sx={headerSX}
            title={<Typography variant='h3'>{title}</Typography>}
            action={secondary}
          />
        )}

        {content && <CardContent sx={contentSX}>{children}</CardContent>}
        {!content && children}

        {codeHighlight && (
          <>
            <Divider sx={{ borderStyle: "dashed" }} />
          </>
        )}
      </Card>
    );
  }
);

MainCard.displayName = "MainCard";

export default MainCard;
