INSERT INTO MEASURES (id, measure_name)
VALUES (1, 'liter');

INSERT INTO PRODUCTS (id, product_name, measure_id, capacity)
VALUES (1, 'Water', 1, 100);

INSERT INTO containers (spot_coordinates, product_id)
VALUES ('A10', 1);