DROP TABLE IF EXISTS logs;
DROP TABLE IF EXISTS alliance_histories;
DROP TABLE IF EXISTS attack_histories;
DROP TABLE IF EXISTS current_alliances;
DROP TABLE IF EXISTS current_attacks;
DROP TABLE IF EXISTS buildings;
DROP TABLE IF EXISTS planets;
DROP TABLE IF EXISTS fleets;
DROP TABLE IF EXISTS buildings_dic;
DROP TABLE IF EXISTS resources;
DROP TABLE IF EXISTS activations;
DROP TABLE IF EXISTS planet_fields;
DROP TABLE IF EXISTS users;

CREATE TABLE users(
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  nickname VARCHAR(32) UNIQUE NOT NULL,
  salt VARCHAR(256) NOT NULL,
  email VARCHAR(256) NOT NULL,
  is_activated BOOLEAN DEFAULT '0' NOT NULL
);

CREATE TABLE planet_fields(
  planet_field_id INT PRIMARY KEY AUTO_INCREMENT,
  coordinate_x INT NOT NULL,
  coordinate_y INT NOT NULL,
  status varchar(6) DEFAULT 'LOCKED', -- "LOCKED", "EMPTY" or "USED",
  UNIQUE(coordinate_x, coordinate_y)
);

CREATE TABLE activations(
  activation_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT UNIQUE NOT NULL,
  planet_field_id INT UNIQUE,
  activation_code VARCHAR(256),
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (planet_field_id) REFERENCES planet_fields(planet_field_id)
);

CREATE TABLE resources(
  resource_id INT PRIMARY KEY AUTO_INCREMENT,
  gadolin INT DEFAULT '0' NOT NULL,
  ununtrium INT DEFAULT '0' NOT NULL,
  last_update TIMESTAMP DEFAULT now()
);

CREATE TABLE buildings_dic(
  buildings_dic_id INT PRIMARY KEY,
  name VARCHAR(16) NOT NULL
);

insert into buildings_dic VALUES(0, 'Gadolin mine');
insert into buildings_dic VALUES(1, 'Ununtrum mine');
insert into buildings_dic VALUES(2, 'Hangar');
insert into buildings_dic VALUES(3, 'Defence systems');

CREATE TABLE fleets(
  fleet_id INT PRIMARY KEY AUTO_INCREMENT,
  warships INT DEFAULT 0 NOT NULL,
  bombers INT DEFAULT 0 NOT NULL,
  ironclads INT DEFAULT 0 NOT NULL
);

CREATE TABLE planets(
  planet_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT UNIQUE NOT NULL,
  name VARCHAR(256) NOT NULL,
  resource_id INT UNIQUE NOT NULL,
  fleet_id INT UNIQUE NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (resource_id) REFERENCES resources(resource_id),
  FOREIGN KEY (fleet_id) REFERENCES fleets(fleet_id)
);

CREATE TABLE buildings(
  building_id INT PRIMARY KEY AUTO_INCREMENT,
  buildings_dic_id INT NOT NULL,
  planet_id INT NOT NULL,
  level INT DEFAULT 1 NOT NULL,
  FOREIGN KEY (buildings_dic_id) REFERENCES buildings_dic(buildings_dic_id),
  FOREIGN KEY (planet_id) REFERENCES planets(planet_id)
);

CREATE TABLE current_attacks(
  current_attack_id INT PRIMARY KEY AUTO_INCREMENT,
  fleet_id INT UNIQUE NOT NULL,
  time_of_sending_attack TIMESTAMP NULL,
  attacked_planet_id INT NULL,
  FOREIGN KEY (attacked_planet_id) REFERENCES planets(planet_id),
  FOREIGN KEY (fleet_id) REFERENCES fleets(fleet_id)
);

CREATE TABLE current_alliances(
  current_alliance_id INT PRIMARY KEY AUTO_INCREMENT,
  fleet_id INT UNIQUE NOT NULL,
  time_of_sending_alliance TIMESTAMP NULL,
  helped_planet_id INT NULL, -- IF PLANET IS SAME AS MOTHER PLANET FOR FLEET THAN IT'S THE RETURN
  FOREIGN KEY (helped_planet_id) REFERENCES planets(planet_id),
  FOREIGN KEY (fleet_id) REFERENCES fleets(fleet_id)
);

CREATE TABLE attack_histories(
  attack_history_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  warships INT NOT NULL,
  bombers INT NOT NULL,
  ironclads INT NOT NULL,
  attacked_planet_id INT NULL,
  result BOOLEAN, -- 0 - LOSE, 1 - WIN
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE alliance_histories(
  alliance_history_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  warships INT NOT NULL,
  bombers INT NOT NULL,
  ironclads INT NOT NULL,
  helped_planet_id INT NULL,
  result BOOLEAN, -- 0 - LOSE , 1 - BACK ON MOTHER PLANET
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE logs(
  log_id INT PRIMARY KEY AUTO_INCREMENT,
  time TIMESTAMP DEFAULT now() NOT NULL,
  class VARCHAR(256) NOT NULL, -- class from where logger is used
  information LONGTEXT NOT NULL,
  level VARCHAR(32) DEFAULT 'INFO' NOT NULL -- INFO, DEBUG or ERROR
)