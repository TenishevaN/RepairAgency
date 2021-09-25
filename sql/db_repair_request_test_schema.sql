
CREATE TABLE IF NOT EXISTS `db_repair_agency_test`.`repair_request_full` (
                                                                   `id` int NOT NULL auto_increment,
                                                                    `cost` DECIMAL(10)  NULL,
                                                                   `date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                                                                   `status_id` int DEFAULT 1,
                                                                   `master_id` int,
                                                                   `account_id` int ,
                                                                   `description` varchar(100),
                                                                   `status_name` varchar(100),
                                                                   `master_name` varchar(100),
                                                                   `user_name` varchar(100),
                                                                   PRIMARY KEY (`id`))
    ENGINE = InnoDB;
