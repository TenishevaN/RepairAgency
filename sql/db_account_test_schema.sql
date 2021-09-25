
CREATE TABLE IF NOT EXISTS `db_repair_agency_test`.`account`(
                                                                id int NOT NULL auto_increment,
                                                                login varchar(100),
                                                                password varchar(100),
                                                                email varchar(100),
                                                                name varchar(100),
                                                                invoice_id int,
                                                                role_id int,
                                                                deleted int,
                                                                PRIMARY KEY (id))
    ENGINE = InnoDB;


