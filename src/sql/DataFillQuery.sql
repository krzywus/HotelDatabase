---- Wypelnienie tabeli bez kluczy obcych ( Hotel, Features, RoomType )

INSERT INTO Hotel(Name,		Town,		Adress, ReceptionPhone, Email, Stars) 
VALUES ('Urnova Wroclaw',	'Wroclaw',	'Powstañców Œl¹skich 25',		'854 865 534', 'urnova.wroclaw@gmail.com', 3)
INSERT INTO Hotel(Name,		Town,		Adress, ReceptionPhone, Email, Stars) 
VALUES ('Urnova Katowice',	'Katowice',	'Powstañców Rzeszowskich 15',	'854 865 578', 'urnova.katowice@gmail.com',	 1)
INSERT INTO Hotel(Name,		Town,		Adress, ReceptionPhone, Email, Stars) 
VALUES ('Urnova Warszawa',	'Warszawa',	'Partyzantów 5',				'854 865 500', 'urnova.hotel@gmail.com', 2)

SELECT * FROM Hotel;
DELETE FROM Hotel;


--- FEATURES

INSERT INTO Features(TV, Minibar, Jacuzzi, Internet, Fireplace ,Kitchen) 
VALUES				(1,		1,		1,		1,			1,			1);
INSERT INTO Features(TV, Minibar, Jacuzzi, Internet, Fireplace ,Kitchen) 
VALUES				(1,		1,		1,		1,			0,			1);
INSERT INTO Features(TV, Minibar, Jacuzzi, Internet, Fireplace ,Kitchen) 
VALUES				(1,		0,		0,		1,			1,			1);
INSERT INTO Features(TV, Minibar, Jacuzzi, Internet, Fireplace ,Kitchen) 
VALUES				(1,		0,		0,		1,			0,			1);
INSERT INTO Features(TV, Minibar, Jacuzzi, Internet, Fireplace ,Kitchen) 
VALUES				(0,		0,		0,		1,			0,			1);

SELECT * FROM Features;
DELETE FROM Features;


--- ROOM TYPE

INSERT INTO RoomType(TypeName, SingleBeds,	DoubleBeds, MaxNumberOfPeople)
 VALUES				('Single',		1,			0,			1);
INSERT INTO RoomType(TypeName, SingleBeds,	DoubleBeds, MaxNumberOfPeople)
 VALUES				('DoubleTogether',		0,			1,			2);
INSERT INTO RoomType(TypeName, SingleBeds,	DoubleBeds, MaxNumberOfPeople)
 VALUES				('DoubleSeparate',		2,			0,			2);
INSERT INTO RoomType(TypeName, SingleBeds,	DoubleBeds, MaxNumberOfPeople)
 VALUES				('Family',		2,			1,			4);
INSERT INTO RoomType(TypeName, SingleBeds,	DoubleBeds, MaxNumberOfPeople)
 VALUES				('FamilyBig',		4,			2,			8);
INSERT INTO RoomType(TypeName, SingleBeds,	DoubleBeds, MaxNumberOfPeople)
 VALUES				('Dorm',		8,			3,			15);

 
SELECT * FROM RoomType;
DELETE FROM RoomType;
---
---------------------------- ROOMS

DECLARE @Exclusive int  = (SELECT ID FROM Features WHERE FirePlace = 1 AND Jacuzzi = 1)
DECLARE @Basic int  = (SELECT ID FROM Features WHERE FirePlace = 0 AND Jacuzzi = 0 AND TV = 1)
DECLARE @Poor int  = (SELECT ID FROM Features WHERE FirePlace = 0 AND Jacuzzi = 0 AND TV = 0)

DECLARE @Single int = (SELECT ID FROM RoomType WHERE TypeName = 'Single')
DECLARE @DoubleT int = (SELECT ID FROM RoomType WHERE TypeName = 'DoubleTogether')
DECLARE @DoubleS int = (SELECT ID FROM RoomType WHERE TypeName = 'DoubleSeparate')
DECLARE @Family int = (SELECT ID FROM RoomType WHERE TypeName = 'Family')
DECLARE @FamilyBig int = (SELECT ID FROM RoomType WHERE TypeName = 'FamilyBig')
DECLARE @Dorm int = (SELECT ID FROM RoomType WHERE TypeName = 'Dorm')

INSERT INTO Room( Hotel, RoomNumber, FeaturesID, Type, Price, Locator)
VALUES			('Urnova Wroclaw', 101,	@Exclusive,		@Single,		349,	NULL 	)
INSERT INTO Room( Hotel, RoomNumber, FeaturesID, Type, Price, Locator)
VALUES			('Urnova Wroclaw', 102,	@Exclusive,		@Single,		349,	NULL 	)
INSERT INTO Room( Hotel, RoomNumber, FeaturesID, Type, Price, Locator)
VALUES			('Urnova Wroclaw', 103,	@Exclusive,		@DoubleS,		469,	NULL 	)
INSERT INTO Room( Hotel, RoomNumber, FeaturesID, Type, Price, Locator)
VALUES			('Urnova Wroclaw', 104,	@Exclusive,		@Family,		599,	NULL 	)
INSERT INTO Room( Hotel, RoomNumber, FeaturesID, Type, Price, Locator)
VALUES			('Urnova Wroclaw', 105,	@Exclusive,		@FamilyBig,		699,	NULL 	)
INSERT INTO Room( Hotel, RoomNumber, FeaturesID, Type, Price, Locator)
VALUES			('Urnova Warszawa', 201,	@Basic,		@Single,		149,	NULL 	)
INSERT INTO Room( Hotel, RoomNumber, FeaturesID, Type, Price, Locator)
VALUES			('Urnova Warszawa', 202,	@Basic,		@Single,		149,	NULL 	)
INSERT INTO Room( Hotel, RoomNumber, FeaturesID, Type, Price, Locator)
VALUES			('Urnova Warszawa', 203,	@Basic,		@DoubleS,		199,	NULL 	)
INSERT INTO Room( Hotel, RoomNumber, FeaturesID, Type, Price, Locator)
VALUES			('Urnova Warszawa', 204,	@Basic,		@DoubleS,		199,	NULL 	)
INSERT INTO Room( Hotel, RoomNumber, FeaturesID, Type, Price, Locator)
VALUES			('Urnova Warszawa', 205,	@Basic,		@DoubleS,		199,	NULL 	)
INSERT INTO Room( Hotel, RoomNumber, FeaturesID, Type, Price, Locator)
VALUES			('Urnova Katowice', 301,	@Poor,		@Dorm,		79,	NULL 	)
INSERT INTO Room( Hotel, RoomNumber, FeaturesID, Type, Price, Locator)
VALUES			('Urnova Katowice', 302,	@Poor,		@Dorm,		79,	NULL 	)
INSERT INTO Room( Hotel, RoomNumber, FeaturesID, Type, Price, Locator)
VALUES			('Urnova Katowice', 303,	@Poor,		@Dorm,		79,	NULL 	)
INSERT INTO Room( Hotel, RoomNumber, FeaturesID, Type, Price, Locator)
VALUES			('Urnova Katowice', 304,	@Poor,		@Dorm,		79,	NULL 	)
INSERT INTO Room( Hotel, RoomNumber, FeaturesID, Type, Price, Locator)
VALUES			('Urnova Katowice', 305,	@Poor,		@Dorm,		79,	NULL 	)

SELECT * FROM Room;
DELETE FROM Room;

---- CLIENTS


INSERT INTO Client(	Name, Surname, Phone, IDCardSeries,	Reservation, Account)
VALUES			  ('Kasia','Rych','802 152 900','ABC800900',NULL,NULL)
INSERT INTO Client(	Name, Surname, Phone, IDCardSeries,	Reservation, Account)
VALUES			  ('Antonina','Radziak','602 152 900','ABC802960',NULL,NULL)
INSERT INTO Client(	Name, Surname, Phone, IDCardSeries,	Reservation, Account)
VALUES			  ('Jan','Zura','662 152 900','ABC820901',NULL,NULL)
INSERT INTO Client(	Name, Surname, Phone, IDCardSeries,	Reservation, Account)
VALUES			  ('Marcelina','Antypa','823 152 900','ABC850970',NULL,NULL)
INSERT INTO Client(	Name, Surname, Phone, IDCardSeries,	Reservation, Account)
VALUES			  ('Tomek','Makarski','634 152 900','ABC811909',NULL,NULL)
INSERT INTO Client(	Name, Surname, Phone, IDCardSeries,	Reservation, Account)
VALUES			  ('Dawid','Turek','829 152 900','ABC239975',NULL,NULL)


----- Przykladowe konta

DECLARE @testID char(9) = (SELECT TOP 1 IDCardSeries FROM Client)
EXEC CreateAccount @Username='Test', @Password = 'Lubieplacki1', @IDCardSeries = @testID

EXEC CreateAccount @Username='Admin', @Password = 'NieLubiePlackow', @AccessLevel= 3



  -- Creates the login with password
CREATE LOGIN AppUser   
    WITH PASSWORD = 'application';  
CREATE USER AppUser
GO  


