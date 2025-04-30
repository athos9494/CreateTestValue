CREATE DATABASE test;

CREATE TABLE file_convert_info (
                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                   recv VARCHAR(60),
                                   send VARCHAR(60),
                                   status INT DEFAULT 2
);

CREATE TABLE param_config (
                              sysid VARCHAR(6) PRIMARY KEY,
                              subsysid VARCHAR(6),
                              unitno VARCHAR(8),
                              areacode VARCHAR(6),
                              areaname VARCHAR(60),
                              hostname VARCHAR(30),
                              hostpwd VARCHAR(30),
                              hostip VARCHAR(20),
                              hostport INT,
                              remotepath VARCHAR(50),
                              dgst VARCHAR(60),
                              note1 VARCHAR(30),
                              note2 VARCHAR(60),
                              note3 VARCHAR(100)
);

CREATE TABLE test_person_value (
                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                   workdate CHAR(8),
                                   name VARCHAR(30),
                                   sex CHAR,
                                   birth CHAR(8) DEFAULT '',
                                   idType CHAR DEFAULT '',
                                   idNo VARCHAR(19) DEFAULT '',
                                   addr VARCHAR(300) DEFAULT '',
                                   phoneNo VARCHAR(15) DEFAULT '',
                                   idExpiryDate CHAR(8) DEFAULT ''
);