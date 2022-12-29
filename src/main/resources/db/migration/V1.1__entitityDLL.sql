CREATE TABLE `examples` (
  id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NULL,
   `description` VARCHAR(255) NULL,
   CONSTRAINT pk_entity PRIMARY KEY (id)
);
CREATE TABLE `users` (
    id BIGINT AUTO_INCREMENT NOT NULL,
	`username` VARCHAR(50) NOT NULL COLLATE 'latin1_swedish_ci',
	`password` VARCHAR(500) NOT NULL COLLATE 'latin1_swedish_ci',
	`roles` VARCHAR(500) NULL,
	PRIMARY KEY (id)
)