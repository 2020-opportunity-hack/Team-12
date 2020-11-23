CREATE DATABASE IF NOT EXISTS sundayfriends;
USE sundayfriends;

CREATE TABLE IF NOT EXISTS `Family` (
  `familyId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `address` varchar(200),
  PRIMARY KEY (`familyId`)
) DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Users` (
  `userId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `familyId` int(10) unsigned NOT NULL,
  `isAdmin` boolean,
  `imageUrl` varchar(600),
  `balance` int(10) unsigned NOT NULL,
  PRIMARY KEY (`userId`),
  FOREIGN KEY (`familyId`) REFERENCES Family(`familyId`)
) DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Transactions` (
  `transactionid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned NOT NULL,
  `type` boolean,
  `amount` int(10) unsigned NOT NULL,
  `balanceAfterAction` int(10) unsigned NOT NULL,
  `time` datetime,
  PRIMARY KEY (`transactionid`),
  FOREIGN KEY (`userId`) REFERENCES Users(`userId`)
) DEFAULT CHARSET=utf8;


/*
-- In case of new family, insert into family table first and use that family id to make new insert in user table
INSERT INTO `Family` (`address`) VALUES
  ("New Jersey");

INSERT INTO `Users` (`name`, `email`, `familyId`, `isAdmin`, `imageUrl`,  `balance`) VALUES
  ('ABC', 'abc@gmail.com', 1, true, null, 0);
  
INSERT INTO `Users` (`name`, `email`, `familyId`, `isAdmin`, `imageUrl`,  `balance`) VALUES
  ('DEF', 'def@gmail.com', 1, true, null, 0);

INSERT INTO `Users` (`name`, `email`, `familyId`, `isAdmin`, `imageUrl`,  `balance`) VALUES
  ('GHI', 'ghi@gmail.com', 1, true, null, 0);
  
INSERT INTO `Family` (`address`) VALUES
  ("New York");
  
INSERT INTO `Users` (`name`, `email`, `familyId`, `isAdmin`, `imageUrl`,  `balance`) VALUES
  ('PQR', 'pqr@gmail.com', 2, true, null, 0);
  
INSERT INTO `Transactions` (`userId`, `type`, `amount`, `balanceAfterAction`, `time`) VALUES
  (1, true, 20, 20, now());
  
INSERT INTO `Transactions` (`userId`, `type`, `amount`, `balanceAfterAction`, `time`) VALUES
  (1, true, 20, 40, now());
*/
