# Disable foreign key constraints temporarily
SET FOREIGN_KEY_CHECKS = 0;

# Reset tables
TRUNCATE TABLE products;
TRUNCATE TABLE warehouses;

# Reset auto-increment counters
ALTER TABLE warehouses AUTO_INCREMENT = 1;
ALTER TABLE products AUTO_INCREMENT = 1;

# Warehouse Setup
INSERT INTO warehouses (name, location, capacity) VALUES
('Fulfillment Center', 'TYS1', 1000),
('Sort Center', 'TYS5', 800);

# Products for Fulfillment Center (TYS1)
INSERT INTO products (name, description, quantity, warehouse_id) VALUES
('USB Cables', '3-pack of braided 6ft USB-C cords', 100, 1),
('Portable Power Banks', '10,000mAh external batteries', 150, 1),
('Bluetooth Keyboards', 'Wireless keyboard with touchpad', 120, 1),
('Noise-Canceling Headphones', 'Over-ear comfort design', 90, 1),
('Webcams', 'HD 1080p USB video camera', 80, 1),
('Mouse Pads', 'Non-slip ergonomic pads', 70, 1),
('Laptop Stands', 'Adjustable aluminum stands', 50, 1),
('Barcode Scanners', 'USB handheld warehouse scanners', 40, 1),
('HDMI Splitters', 'Dual output signal device', 30, 1),
('Thermal Labels', 'Roll of 1,000 adhesive shipping labels', 40, 1);

# Products for Sort Center (TYS5)
INSERT INTO products (name, description, quantity, warehouse_id) VALUES
('Flash Drives', 'Pack of 5 USB 3.0 drives, 64GB each', 120, 2),
('Packing Tape', '6-pack of heavy-duty rolls', 100, 2),
('Monitor Mounts', 'Articulated arms for screens', 90, 2),
('Power Adapters', 'Universal plug sets for sorting robots', 80, 2),
('Label Printers', 'Compact thermal label printer', 70, 2),
('Work Gloves', 'Nitrile-coated protection gear', 60, 2),
('RFID Tags', 'Batch of 100 for real-time tracking', 50, 2),
('Battery Packs', 'Lithium units for equipment recharge', 40, 2),
('Clipboard Sets', 'Plastic clipboards with attached pen', 40, 2),
('Handheld Radios', 'Warehouse communication sets', 40, 2);

# Optional quick check
SELECT COUNT(*) FROM products;

# Re-enable foreign key constraints
SET FOREIGN_KEY_CHECKS = 1;