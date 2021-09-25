
CREATE TABLE IF NOT EXISTS `db_repair_agency_test`.`invoice`(
                            id int NOT NULL auto_increment,
                            account_id int,
                            ammount DECIMAL(10)  NULL,
                            PRIMARY KEY (id))
                            ENGINE = InnoDB;


