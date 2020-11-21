CREATE TABLE IF NOT EXISTS `Family` (
  `FamilyID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Address` varchar(200),
  PRIMARY KEY (`FamilyID`)
) DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Users` (
  `UserID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `UserName` varchar(200) NOT NULL,
  `EmailID` varchar(200) NOT NULL,
  `FamilyID` int(10) unsigned NOT NULL,
  `isAdmin` boolean,
  `ImageURL` varchar(600),
  `Balance` int(10) unsigned NOT NULL,
  PRIMARY KEY (`UserID`),
  FOREIGN KEY (`FamilyID`) REFERENCES Family(`FamilyID`)
) DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Transactions` (
  `TransactionID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `UserID` int(10) unsigned NOT NULL,
  `TransactionType` boolean,
  `Amount` int(10) unsigned NOT NULL,
  `BalanceAfterTransaction` int(10) unsigned NOT NULL,
  `TransactionTime` datetime,
  PRIMARY KEY (`TransactionID`),
  FOREIGN KEY (`UserID`) REFERENCES Users(`UserID`)
) DEFAULT CHARSET=utf8;


/*
-- In case of new family, insert into family table first and use that family id to make new insert in user table
INSERT INTO `Family` (`Address`) VALUES
  ("New Jersey");

INSERT INTO `Users` (`UserName`, `EmailID`, `FamilyID`, `isAdmin`, `ImageURL`,  `Balance`) VALUES
  ('ABC', 'abc@gmail.com', 1, true, null, 0);
  
INSERT INTO `Users` (`UserName`, `EmailID`, `FamilyID`, `isAdmin`, `ImageURL`,  `Balance`) VALUES
  ('DEF', 'def@gmail.com', 1, true, null, 0);

INSERT INTO `Users` (`UserName`, `EmailID`, `FamilyID`, `isAdmin`, `ImageURL`,  `Balance`) VALUES
  ('GHI', 'ghi@gmail.com', 1, true, null, 0);
  
INSERT INTO `Family` (`Address`) VALUES
  ("New York");
  
INSERT INTO `Users` (`UserName`, `EmailID`, `FamilyID`, `isAdmin`, `ImageURL`,  `Balance`) VALUES
  ('PQR', 'pqr@gmail.com', 2, true, null, 0);
  
INSERT INTO `Transactions` (`UserID`, `TransactionType`, `Amount`, `BalanceAfterTransaction`, `TransactionTime`) VALUES
  (1, true, 20, 20, now());
  
INSERT INTO `Transactions` (`UserID`, `TransactionType`, `Amount`, `BalanceAfterTransaction`, `TransactionTime`) VALUES
  (1, true, 20, 40, now());
*/