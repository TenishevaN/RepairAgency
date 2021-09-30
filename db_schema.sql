-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema db_repair_agency
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_repair_agency
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_repair_agency` DEFAULT CHARACTER SET utf8 ;
USE `db_repair_agency` ;

-- -----------------------------------------------------
-- Table `db_repair_agency`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_repair_agency`.`role` ;

CREATE TABLE IF NOT EXISTS `db_repair_agency`.`role` (
  `name` ENUM('admin', 'manager', 'master', 'user') NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_repair_agency`.`language`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_repair_agency`.`language` ;

CREATE TABLE IF NOT EXISTS `db_repair_agency`.`language` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(2) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_repair_agency`.`account_localization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_repair_agency`.`account_localization` ;

CREATE TABLE IF NOT EXISTS `db_repair_agency`.`account_localization` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `language_id` INT NOT NULL,
  `account_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_account_localization_language1`
    FOREIGN KEY (`language_id`)
    REFERENCES `db_repair_agency`.`language` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_account_localization_language1_idx` ON `db_repair_agency`.`account_localization` (`language_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `db_repair_agency`.`account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_repair_agency`.`account` ;

CREATE TABLE IF NOT EXISTS `db_repair_agency`.`account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(15) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `role_id` INT NULL,
  `email` VARCHAR(60) NULL,
  `deleted` INT NOT NULL DEFAULT 0,
  `name` VARCHAR(60) NOT NULL,
  `account_localization_id` INT NULL,
  `invoice_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_account_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `db_repair_agency`.`role` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_account_account_localization1`
    FOREIGN KEY (`account_localization_id`)
    REFERENCES `db_repair_agency`.`account_localization` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `db_repair_agency`.`account` (`id` ASC) VISIBLE;

CREATE INDEX `fk_account_role1_idx` ON `db_repair_agency`.`account` (`role_id` ASC) VISIBLE;

CREATE INDEX `fk_account_account_localization1_idx` ON `db_repair_agency`.`account` (`account_localization_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `db_repair_agency`.`status_localization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_repair_agency`.`status_localization` ;

CREATE TABLE IF NOT EXISTS `db_repair_agency`.`status_localization` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `status_id` INT NULL,
  `language_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_service_localization_status_language1`
    FOREIGN KEY (`language_id`)
    REFERENCES `db_repair_agency`.`language` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_service_localization_status_language1_idx` ON `db_repair_agency`.`status_localization` (`language_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `db_repair_agency`.`status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_repair_agency`.`status` ;

CREATE TABLE IF NOT EXISTS `db_repair_agency`.`status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` ENUM('new', 'on_the_job', 'paid', 'canceled', 'done', 'waiting_for_payment') NOT NULL DEFAULT 'new',
  `status_localization_status_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_status_status_localization_status`
    FOREIGN KEY (`status_localization_status_id`)
    REFERENCES `db_repair_agency`.`status_localization` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_status_status_localization_status1_idx` ON `db_repair_agency`.`status` (`status_localization_status_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `db_repair_agency`.`repair_request`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_repair_agency`.`repair_request` ;

CREATE TABLE IF NOT EXISTS `db_repair_agency`.`repair_request` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cost` DECIMAL(10) UNSIGNED ZEROFILL NULL,
  `date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status_id` INT NOT NULL DEFAULT 1,
  `master_id` INT NULL DEFAULT NULL,
  `account_id` INT NOT NULL,
  `description` VARCHAR(21000) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_repair_request_status`
    FOREIGN KEY (`status_id`)
    REFERENCES `db_repair_agency`.`status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_repair_request_master`
    FOREIGN KEY (`master_id`)
    REFERENCES `db_repair_agency`.`account` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_repair_request_account`
    FOREIGN KEY (`account_id`)
    REFERENCES `db_repair_agency`.`account` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `db_repair_agency`.`repair_request` (`id` ASC) VISIBLE;

CREATE INDEX `fk_repair_request_status1_idx` ON `db_repair_agency`.`repair_request` (`status_id` ASC) VISIBLE;

CREATE INDEX `fk_repair_request_account2_idx` ON `db_repair_agency`.`repair_request` (`master_id` ASC) VISIBLE;

CREATE INDEX `fk_repair_request_account1_idx` ON `db_repair_agency`.`repair_request` (`account_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `db_repair_agency`.`review`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_repair_agency`.`review` ;

CREATE TABLE IF NOT EXISTS `db_repair_agency`.`review` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `comment` VARCHAR(1000) NULL,
  `date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `repair_request_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_review_repair_request1`
    FOREIGN KEY (`repair_request_id`)
    REFERENCES `db_repair_agency`.`repair_request` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `fk_review_repair_request1_idx` ON `db_repair_agency`.`review` (`repair_request_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `db_repair_agency`.`invoice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_repair_agency`.`invoice` ;

CREATE TABLE IF NOT EXISTS `db_repair_agency`.`invoice` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `account_id` INT NOT NULL,
  `ammount` DECIMAL(10) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_invoice_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `db_repair_agency`.`account` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `fk_invoice_account1_idx` ON `db_repair_agency`.`invoice` (`account_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `db_repair_agency`.`invoice_balance`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_repair_agency`.`invoice_balance` ;

CREATE TABLE IF NOT EXISTS `db_repair_agency`.`invoice_balance` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `invoice_id` INT NULL,
  `repair_request_id` INT NULL,
  `ammount` DECIMAL(10) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_invoice_balance_invoice1`
    FOREIGN KEY (`invoice_id`)
    REFERENCES `db_repair_agency`.`invoice` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_invoice_balance_repair_request1`
    FOREIGN KEY (`repair_request_id`)
    REFERENCES `db_repair_agency`.`repair_request` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `fk_payment_invoice1_idx` ON `db_repair_agency`.`invoice_balance` (`invoice_id` ASC) VISIBLE;

CREATE INDEX `fk_payment_repair_request1_idx` ON `db_repair_agency`.`invoice_balance` (`repair_request_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `db_repair_agency`.`account_archive`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_repair_agency`.`account_archive` ;

CREATE TABLE IF NOT EXISTS `db_repair_agency`.`account_archive` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(15) NOT NULL,
  `password` VARCHAR(11) NOT NULL,
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `email` VARCHAR(60) NULL,
  `account_id` INT NOT NULL DEFAULT 0,
  `name` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `db_repair_agency`.`account_archive` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `db_repair_agency`.`repair_request_archive`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_repair_agency`.`repair_request_archive` ;

CREATE TABLE IF NOT EXISTS `db_repair_agency`.`repair_request_archive` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cost` DECIMAL(10,0) UNSIGNED ZEROFILL NULL,
  `date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` VARCHAR(21000) NULL,
  `document_id` INT NULL,
  `status_id` INT NULL,
  `master_id` INT NULL,
  `account_id` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `db_repair_agency`.`repair_request_archive` (`id` ASC) VISIBLE;

USE `db_repair_agency` ;

-- -----------------------------------------------------
-- View `db_repair_agency`.`repair_request_full`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `db_repair_agency`.`repair_request_full` ;
USE `db_repair_agency`;
CREATE  OR REPLACE VIEW `repair_request_full` AS SELECT repair_request.id, repair_request.cost, repair_request.date, repair_request.status_id, repair_request.master_id, repair_request.account_id, repair_request.description, status.name AS status_name, master.name AS master_name, user.name AS user_name FROM repair_request LEFT JOIN status ON repair_request.status_id = status.id LEFT JOIN account master ON repair_request.master_id = master.id LEFT JOIN account user ON repair_request.account_id = user.id;
USE `db_repair_agency`;

DELIMITER $$

USE `db_repair_agency`$$
DROP TRIGGER IF EXISTS `db_repair_agency`.`account_BEFORE_INSERT` $$
USE `db_repair_agency`$$
CREATE DEFINER = CURRENT_USER TRIGGER `db_repair_agency`.`account_BEFORE_INSERT` BEFORE INSERT ON `account` FOR EACH ROW
BEGIN
set  new.invoice_id = (SELECT MAX( id ) FROM invoice) + 1;
END$$


USE `db_repair_agency`$$
DROP TRIGGER IF EXISTS `db_repair_agency`.`account_AFTER_INSERT` $$
USE `db_repair_agency`$$
CREATE DEFINER = CURRENT_USER TRIGGER `db_repair_agency`.`account_AFTER_INSERT` AFTER INSERT ON `account` FOR EACH ROW
BEGIN
INSERT INTO invoice (account_id) values (new.id);
END$$


USE `db_repair_agency`$$
DROP TRIGGER IF EXISTS `db_repair_agency`.`account_BEFORE_DELETE` $$
USE `db_repair_agency`$$
CREATE DEFINER = CURRENT_USER TRIGGER `db_repair_agency`.`account_BEFORE_DELETE` BEFORE DELETE ON `account` FOR EACH ROW
BEGIN
INSERT INTO account_archive(account_id, login, password, email, name)
    VALUES(old.id, old.login, old.password, old.email, old.name);
 DELETE FROM repair_request WHERE account_id = old.id;       
END$$


USE `db_repair_agency`$$
DROP TRIGGER IF EXISTS `db_repair_agency`.`repair_request_BEFORE_DELETE` $$
USE `db_repair_agency`$$
CREATE DEFINER = CURRENT_USER TRIGGER `db_repair_agency`.`repair_request_BEFORE_DELETE` BEFORE DELETE ON `repair_request` FOR EACH ROW
BEGIN
INSERT INTO repair_request_archive(document_id, status_id, master_id, account_id, cost, description)
    VALUES(old.id, old.status_id, old.master_id, old.account_id, old.cost, old.description);
END$$


DELIMITER ;

-- -----------------------------------------------------
-- Data for table `db_repair_agency`.`role`
-- -----------------------------------------------------
START TRANSACTION;
USE `db_repair_agency`;
INSERT INTO `db_repair_agency`.`role` (`name`, `id`) VALUES ('admin', 1);
INSERT INTO `db_repair_agency`.`role` (`name`, `id`) VALUES ('manager', 2);
INSERT INTO `db_repair_agency`.`role` (`name`, `id`) VALUES ('master', 3);
INSERT INTO `db_repair_agency`.`role` (`name`, `id`) VALUES ('user', 4);

COMMIT;


-- -----------------------------------------------------
-- Data for table `db_repair_agency`.`language`
-- -----------------------------------------------------
START TRANSACTION;
USE `db_repair_agency`;
INSERT INTO `db_repair_agency`.`language` (`id`, `code`) VALUES (1, 'en');
INSERT INTO `db_repair_agency`.`language` (`id`, `code`) VALUES (2, 'uk');
INSERT INTO `db_repair_agency`.`language` (`id`, `code`) VALUES (3, 'ru');

COMMIT;


-- -----------------------------------------------------
-- Data for table `db_repair_agency`.`account_localization`
-- -----------------------------------------------------
START TRANSACTION;
USE `db_repair_agency`;
INSERT INTO `db_repair_agency`.`account_localization` (`id`, `name`, `language_id`, `account_id`) VALUES (DEFAULT, 'master', 1, 3);
INSERT INTO `db_repair_agency`.`account_localization` (`id`, `name`, `language_id`, `account_id`) VALUES (DEFAULT, 'мастер', 3, 3);
INSERT INTO `db_repair_agency`.`account_localization` (`id`, `name`, `language_id`, `account_id`) VALUES (DEFAULT, 'майстер', 2, 3);
INSERT INTO `db_repair_agency`.`account_localization` (`id`, `name`, `language_id`, `account_id`) VALUES (DEFAULT, 'master1', 1, 4);
INSERT INTO `db_repair_agency`.`account_localization` (`id`, `name`, `language_id`, `account_id`) VALUES (DEFAULT, 'мастер2', 3, 4);
INSERT INTO `db_repair_agency`.`account_localization` (`id`, `name`, `language_id`, `account_id`) VALUES (DEFAULT, 'майстер2', 2, 4);

COMMIT;


-- -----------------------------------------------------
-- Data for table `db_repair_agency`.`account`
-- -----------------------------------------------------
START TRANSACTION;
USE `db_repair_agency`;
INSERT INTO `db_repair_agency`.`account` (`id`, `login`, `password`, `create_time`, `role_id`, `email`, `deleted`, `name`, `account_localization_id`, `invoice_id`) VALUES (1, 'admin', 'uNooe+r3zubSU9zpgsXL2w==', DEFAULT, 1, 'servicemailtest2021@gmail.com', DEFAULT, 'admin', NULL, NULL);
INSERT INTO `db_repair_agency`.`account` (`id`, `login`, `password`, `create_time`, `role_id`, `email`, `deleted`, `name`, `account_localization_id`, `invoice_id`) VALUES (2, 'manager', 'uNooe+r3zubSU9zpgsXL2w==', DEFAULT, 2, 'servicemailtest2021@gmail.com', DEFAULT, 'manager', NULL, NULL);
INSERT INTO `db_repair_agency`.`account` (`id`, `login`, `password`, `create_time`, `role_id`, `email`, `deleted`, `name`, `account_localization_id`, `invoice_id`) VALUES (3, 'master', 'uNooe+r3zubSU9zpgsXL2w==', DEFAULT, 3, 'servicemailtest2021@gmail.com', DEFAULT, 'master', NULL, NULL);
INSERT INTO `db_repair_agency`.`account` (`id`, `login`, `password`, `create_time`, `role_id`, `email`, `deleted`, `name`, `account_localization_id`, `invoice_id`) VALUES (4, 'master2', 'uNooe+r3zubSU9zpgsXL2w==', DEFAULT, 3, 'servicemailtest2021@gmail.com', DEFAULT, 'master2', NULL, NULL);
INSERT INTO `db_repair_agency`.`account` (`id`, `login`, `password`, `create_time`, `role_id`, `email`, `deleted`, `name`, `account_localization_id`, `invoice_id`) VALUES (5, 'user1', 'uNooe+r3zubSU9zpgsXL2w==', DEFAULT, 4, 'servicemailtest2021@gmail.com', DEFAULT, 'user1', NULL, NULL);
INSERT INTO `db_repair_agency`.`account` (`id`, `login`, `password`, `create_time`, `role_id`, `email`, `deleted`, `name`, `account_localization_id`, `invoice_id`) VALUES (6, 'user2', 'uNooe+r3zubSU9zpgsXL2w==', DEFAULT, 4, 'servicemailtest2021@gmail.com', DEFAULT, 'user2', NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `db_repair_agency`.`status_localization`
-- -----------------------------------------------------
START TRANSACTION;
USE `db_repair_agency`;
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'new', 1, 1);
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'новий', 1, 2);
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'новый', 1, 3);
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'on the job', 2, 1);
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'в работе', 2, 3);
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'в роботі', 2, 2);
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'paid', 3, 1);
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'сплачено', 3, 2);
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'оплачено', 3, 3);
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'canceled', 4, 1);
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'відмінено', 4, 2);
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'отменено', 4, 3);
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'done', 5, 1);
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'виконано', 5, 2);
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'выполнено', 5, 3);
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'waiting for payment', 6, 1);
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'чекає на оплату', 6, 2);
INSERT INTO `db_repair_agency`.`status_localization` (`id`, `name`, `status_id`, `language_id`) VALUES (DEFAULT, 'ожидает оплату', 6, 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `db_repair_agency`.`status`
-- -----------------------------------------------------
START TRANSACTION;
USE `db_repair_agency`;
INSERT INTO `db_repair_agency`.`status` (`id`, `name`, `status_localization_status_id`) VALUES (1, 'new', NULL);
INSERT INTO `db_repair_agency`.`status` (`id`, `name`, `status_localization_status_id`) VALUES (2, 'on_the_job', NULL);
INSERT INTO `db_repair_agency`.`status` (`id`, `name`, `status_localization_status_id`) VALUES (3, 'paid', NULL);
INSERT INTO `db_repair_agency`.`status` (`id`, `name`, `status_localization_status_id`) VALUES (4, 'canceled', NULL);
INSERT INTO `db_repair_agency`.`status` (`id`, `name`, `status_localization_status_id`) VALUES (5, 'done', NULL);
INSERT INTO `db_repair_agency`.`status` (`id`, `name`, `status_localization_status_id`) VALUES (6, 'waiting_for_payment', NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `db_repair_agency`.`repair_request`
-- -----------------------------------------------------
START TRANSACTION;
USE `db_repair_agency`;
INSERT INTO `db_repair_agency`.`repair_request` (`id`, `cost`, `date`, `status_id`, `master_id`, `account_id`, `description`) VALUES (DEFAULT, NULL, DEFAULT, DEFAULT, 3, 5, 'new request');
INSERT INTO `db_repair_agency`.`repair_request` (`id`, `cost`, `date`, `status_id`, `master_id`, `account_id`, `description`) VALUES (DEFAULT, 100000, '2021-09-15', 2, 4, 6, 'request 1');
INSERT INTO `db_repair_agency`.`repair_request` (`id`, `cost`, `date`, `status_id`, `master_id`, `account_id`, `description`) VALUES (DEFAULT, 500000, '2021-09-16', DEFAULT, 4, 5, 'request 2');
INSERT INTO `db_repair_agency`.`repair_request` (`id`, `cost`, `date`, `status_id`, `master_id`, `account_id`, `description`) VALUES (DEFAULT, 200000, '2021-09-16', 3, 3, 6, 'request 3');
INSERT INTO `db_repair_agency`.`repair_request` (`id`, `cost`, `date`, `status_id`, `master_id`, `account_id`, `description`) VALUES (DEFAULT, 500000, '2021-09-17', DEFAULT, 4, 5, 'request 4');
INSERT INTO `db_repair_agency`.`repair_request` (`id`, `cost`, `date`, `status_id`, `master_id`, `account_id`, `description`) VALUES (DEFAULT, 200000, '2021-09-01', 2, 3, 5, 'request 5');
INSERT INTO `db_repair_agency`.`repair_request` (`id`, `cost`, `date`, `status_id`, `master_id`, `account_id`, `description`) VALUES (DEFAULT, 100000, '2021-08-01', 5, 4, 6, 'request 6');
INSERT INTO `db_repair_agency`.`repair_request` (`id`, `cost`, `date`, `status_id`, `master_id`, `account_id`, `description`) VALUES (DEFAULT, 400000, '2021-09-11', 2, 4, 5, 'request 7');

COMMIT;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
