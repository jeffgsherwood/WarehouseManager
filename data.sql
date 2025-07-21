INSERT INTO warehouses (name, location, capacity) VALUES
('Fulfillment Center', 'TYS1', 1000),
('Sort Center', 'TYS5', 800);

INSERT INTO products (name, description, quantity, warehouse_id) VALUES
('LED Bulbs', 'Energy-efficient lighting', 200, 1),
('Cable Ties', 'Bundle of 500 units', 300, 1),
('USB Chargers', 'Fast charging blocks', 200, 2);
