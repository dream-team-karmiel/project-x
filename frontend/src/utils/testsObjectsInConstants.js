export const ORDERS = [
  {
    id: "order001",
    spotCoordinates: "A1",
    creationDate: "2023-12-01",
    closeDate: "2023-12-05",
    productName: "A One",
    requiredQuantity: 100.0,
    orderStatus: "NEW",
  },
  {
    id: "order002",
    spotCoordinates: "A2",
    creationDate: "2023-12-02",
    closeDate: "2023-12-06",
    productName: "A Two",
    requiredQuantity: 75.0,
    orderStatus: "CONFIRMED",
  },
  {
    id: "order003",
    spotCoordinates: "A3",
    creationDate: "2023-12-03",
    closeDate: "2023-12-07",
    productName: "A Three",
    requiredQuantity: 50.0,
    orderStatus: "CANCELLED",
  },
  {
    id: "order004",
    spotCoordinates: "D8",
    creationDate: "2023-12-04",
    closeDate: "2023-12-08",
    productName: "D Eight",
    requiredQuantity: 120.0,
    orderStatus: "DONE",
  },
  {
    id: "order005",
    spotCoordinates: "B5",
    creationDate: "2023-12-05",
    closeDate: "2023-12-09",
    productName: "B Five",
    requiredQuantity: 90.0,
    orderStatus: "CONFIRMED",
  },
  {
    id: "order006",
    spotCoordinates: "A10",
    creationDate: "2023-12-05",
    closeDate: "2023-12-09",
    productName: "A Ten",
    requiredQuantity: 30.0,
    orderStatus: "CONFIRMED",
  },
];

export const CONTAINERS = [
  {
    id: 1,
    container: {
      spotCoordinates: "A1",
      product: {
        id: 1635,
        productName: "A One",
        measure: {
          id: 7726,
          measureName: "pcs",
        },
        capacity: 100,
      },
    },
    sensorDate: "2023-12-10T22:17:07.807030",
    quantity: 75,
  },
  {
    id: 2,
    container: {
      spotCoordinates: "A2",
      product: {
        id: 1635,
        productName: "A Two",
        measure: {
          id: 7726,
          measureName: "l",
        },
        capacity: 50,
      },
    },
    sensorDate: "2023-12-10T22:17:07.807041",
    quantity: 50,
  },
  {
    id: 3,
    container: {
      spotCoordinates: "A3",
      product: {
        id: 1635,
        productName: "A Three",
        measure: {
          id: 7726,
          measureName: "kg",
        },
        capacity: 80,
      },
    },
    sensorDate: "2023-12-10T22:17:07.807044",
    quantity: 25,
  },
  {
    id: 4,
    container: {
      spotCoordinates: "B1",
      product: {
        id: 1635,
        productName: "B One",
        measure: {
          id: 7726,
          measureName: "pcs",
        },
        capacity: 100,
      },
    },
    sensorDate: "2023-12-10T22:17:07.807030",
    quantity: 75,
  },
  {
    id: 5,
    container: {
      spotCoordinates: "B2",
      product: {
        id: 1635,
        productName: "B Two",
        measure: {
          id: 7726,
          measureName: "l",
        },
        capacity: 100,
      },
    },
    sensorDate: "2023-12-10T22:17:07.807041",
    quantity: 55,
  },
  {
    id: 6,
    container: {
      spotCoordinates: "B3",
      product: {
        id: 1635,
        productName: "B Three",
        measure: {
          id: 7726,
          measureName: "kg",
        },
        capacity: 100,
      },
    },
    sensorDate: "2023-12-10T22:17:07.807044",
    quantity: 100,
  },
];
