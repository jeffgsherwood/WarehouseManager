# Table for warehouses
CREATE TABLE `warehouse_manager`.`warehouses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `location` VARCHAR(100) NULL,
  `capacity` INT NOT NULL,
  PRIMARY KEY (`id`));

# Table for products
CREATE TABLE `warehouse_manager`.`products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(1000) NULL,
  `quantity` INT NOT NULL,
  `warehouse_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `warehouse_id_idx` (`warehouse_id` ASC) VISIBLE,
  CONSTRAINT `warehouse_fk`
    FOREIGN KEY (`warehouse_id`)
    REFERENCES `warehouse_manager`.`warehouses` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
