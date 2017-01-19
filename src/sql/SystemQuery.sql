-----Trigery i procedury

-- trig dodanie rezerwacji : check czy nie ma innej na ten pokoj w podanych ramach czasowych
--							 jezeli rezerwacja zaczyna sie dzisiaj, to uruchom procedure ADD LOCATOR z parametrem: ClientID, RoomID (inserted)
--								i ustaw klientowi tê rezerwacje
CREATE TRIGGER AddReservation ON Reservation
AFTER INSERT AS
BEGIN
	DECLARE @newBegDate date = (SELECT BegDate FROM inserted)
	DECLARE @newEndDate date = (SELECT EndDate FROM inserted)
	DECLARE @RoomID int = (SELECT RoomID FROM inserted)
	DECLARE @newReservationID int = (SELECT ID FROM inserted)
	print @RoomID
	print @newBegDate
	print @newEndDate
	IF( NOT EXISTS( (SELECT * FROM Reservation WHERE RoomID = @RoomID
									AND (
									(BegDate BETWEEN @newBegDate AND @newEndDate)	OR (EndDate BETWEEN @newBegDate AND @newEndDate))
					EXCEPT
					SELECT * FROM Reservation WHERE RoomID = @RoomID AND BegDate = @newBegDate AND EndDate = @newEndDate AND ID = @newReservationID 
				)  )
	  )
	BEGIN	-- nie ma rezerwacji w danym terminie
		print 'Rezerwacja zakonczona pomyslnie.'
		IF( @newBegDate = CAST(GETDATE() AS date)) -- dodaj lokatora jezeli goscie wprowadzaja sie dzisiaj
		BEGIN
			--print 'to dzisiaj!'
			DECLARE @ClientID int = (SELECT ClientID FROM inserted)
			INSERT INTO Locator(ClientID, RoomID) VALUES(@ClientID, @RoomID) -- uruchamia trigger updatujacy room
			-- ustawienie klientowi rezerwacji
			UPDATE Client SET Reservation = (SELECT ID FROM inserted)
			WHERE ID = @ClientID
		END
	END 
	ELSE BEGIN
		print 'Rezerwacja niemozliwa'
		ROLLBACK TRANSACTION
	END
END

DROP TRIGGER AddReservation

SELECT * FROM Room
-- testy
DECLARE @RoomIDToEnter int = (SELECT TOP 1 ID FROM Room WHERE Hotel = 'Urnova Wroclaw' AND Price = 349)
INSERT INTO Reservation(RoomID,BegDate,EndDate,Paid) VALUES(@RoomIDToEnter,'2016-01-01','2016-01-05',0) -- basic check
DECLARE @RoomIDToEnter2 int = (SELECT TOP 1 ID FROM Room WHERE Hotel = 'Urnova Wroclaw' AND Price = 469)
INSERT INTO Reservation(RoomID,BegDate,EndDate,Paid) VALUES(@RoomIDToEnter2,'2016-01-01','2016-01-05',0) -- basic check
DECLARE @RoomIDToEnter3 int = (SELECT TOP 1 ID FROM Room WHERE Hotel = 'Urnova Wroclaw' AND Price = 599)
INSERT INTO Reservation(RoomID,BegDate,EndDate,Paid) VALUES(@RoomIDToEnter3,'2016-01-01','2016-01-05',0) -- basic check

DECLARE @RoomIDToEnter4 int = (SELECT TOP 1 ID FROM Room WHERE Hotel = 'Urnova Wroclaw' AND Price = 599)
INSERT INTO Reservation(RoomID,BegDate,EndDate,Paid) VALUES(@RoomIDToEnter4, GETDATE(), DATEADD(day,2,GETDATE()), 0) -- check for adding locator
DELETE FROM Reservation
SELECT * FROM Reservation



	DECLARE @newDate date = DATEADD(day,1,'2016-01-01')
	print @newDate
	print GETDATE()

------------
-- trigger ADD LOCATOR  Dla pokoju o ID=RoomID ustaw id locatora 

CREATE TRIGGER addLocatorToRoom ON Locator
AFTER INSERT AS BEGIN
	DECLARE @RoomID int = (SELECT RoomID FROM inserted )
	DECLARE @LocatorID int = (SELECT ID FROM inserted)
	UPDATE Room
	SET Locator  = @LocatorID
	WHERE ID=@RoomID
END

DROP TRIGGER  addLocatorToRoom ;

SELECT * FROM Room
SELECT * FROM Locator

UPDATE Room
SET Locator = NULL;
DELETE FROM Locator;

------------
--procedura CHECK LOCATOR BEG () : sprawdz czy jakas rezerwacja nie zaczyna siê dzisiaj [CURSOR] 
--							( taki update na tabeli Room) -> ustawic klientowi te rezerwacje i locatora ustawic

CREATE PROCEDURE CheckLocatorBeg
AS
BEGIN
	DECLARE RoomEnumerator CURSOR
	FOR SELECT RoomID FROM Reservation
	WHERE BegDate = CAST(GETDATE() AS date)
	DECLARE ClientEnumerator CURSOR
	FOR SELECT ClientID FROM Reservation
	WHERE BegDate = CAST(GETDATE() AS date)
	OPEN RoomEnumerator
	OPEN ClientEnumerator
	DECLARE @RoomID int
	DECLARE @ClientID int
	FETCH NEXT FROM RoomEnumerator INTO @RoomID
	FETCH NEXT FROM ClientEnumerator INTO @ClientID
	WHILE @@FETCH_STATUS = 0
		BEGIN
			-- USTAWIENIE KLIENTOWI REZERWACJI
			UPDATE Client
			SET Reservation = (SELECT ID FROM Reservation WHERE  BegDate = CAST(GETDATE() AS date) AND ClientID = @ClientID)
			WHERE ID = @ClientID
			-- USTAWIENIE LOKATORA
			INSERT INTO Locator(ClientID, RoomID) VALUES(@ClientID, @RoomID) -- uruchamia trigger updatujacy room
			--print @RoomID
			--print @ClientID
			FETCH NEXT FROM RoomEnumerator INTO @RoomID
			FETCH NEXT FROM ClientEnumerator INTO @ClientID
		END -- end while

	CLOSE RoomEnumerator
	DEALLOCATE RoomEnumerator
	CLOSE ClientEnumerator
	DEALLOCATE ClientEnumerator
END			
						
DROP PROCEDURE CheckLocatorBeg

EXEC CheckLocatorBeg

SELECT * FROM Reservation
SELECT * FROM Reservation WHERE BegDate = CAST(GETDATE() AS date)
SELECT * FROM Client
SELECT * FROM Room
SELECT * FROM Locator

UPDATE Client SET Reservation = NULL
UPDATE Room SET Locator = NULL
DELETE FROM Locator


DECLARE @RoomIDToEnter5 int = (SELECT TOP 1 ID FROM Room WHERE Hotel = 'Urnova Wroclaw' AND Price = 599)
DECLARE @ClientIDToEnter1 int = (SELECT TOP 1 ID FROM Client WHERE Name = 'Dawid')
INSERT INTO Reservation(RoomID,BegDate,EndDate,Paid, ClientID) VALUES(@RoomIDToEnter5, GETDATE(), DATEADD(day,2,GETDATE()), 0, @ClientIDToEnter1) 
DECLARE @RoomIDToEnter6 int = (SELECT TOP 1 ID FROM Room WHERE Hotel = 'Urnova Wroclaw')
DECLARE @ClientIDToEnter2 int = (SELECT TOP 1 ID FROM Client WHERE Name = 'Kasia')
INSERT INTO Reservation(RoomID,BegDate,EndDate,Paid, ClientID) VALUES(@RoomIDToEnter6, GETDATE(), DATEADD(day,2,GETDATE()), 0, @ClientIDToEnter2)

DELETE FROM Reservation WHERE ClientID != NULL

------------
--procedura CHECK LOCATOR BEG () : sprawdz czy jakas rezerwacja  nie skonczyla sie ( wtedy locator = null 
--									&& uruchom procedure Archive (locatorID -> RoomID,ClientID; Reservation(RoomID,Dzisiaj) -> begDate)


CREATE PROCEDURE CheckLocatorEnd
AS
BEGIN
	-- kursor pokoi
	DECLARE RoomEnumerator CURSOR FOR SELECT RoomID FROM Reservation WHERE EndDate <= CAST(GETDATE() AS date)
	-- kursor klientow
	DECLARE ClientEnumerator CURSOR  FOR SELECT ClientID FROM Reservation WHERE EndDate <= CAST(GETDATE() AS date) 
	-- kursor poczatkowych dat
	DECLARE BegDateEnumerator CURSOR FOR SELECT BegDate FROM Reservation WHERE EndDate <= CAST(GETDATE() AS date)
	-- kursor koncowych dat
	DECLARE EndDateEnumerator CURSOR FOR SELECT EndDate FROM Reservation WHERE EndDate <= CAST(GETDATE() AS date)
	OPEN RoomEnumerator
	OPEN ClientEnumerator
	OPEN BegDateEnumerator
	OPEN EndDateEnumerator
	DECLARE @RoomID int
	DECLARE @ClientID int
	DECLARE @BegDate date
	DECLARE @EndDate date
	FETCH NEXT FROM RoomEnumerator INTO @RoomID
	FETCH NEXT FROM ClientEnumerator INTO @ClientID
	FETCH NEXT FROM BegDateEnumerator INTO @BegDate
	FETCH NEXT FROM EndDateEnumerator INTO @EndDate
	WHILE @@FETCH_STATUS = 0
		BEGIN
			--print @RoomID
			--print @ClientID
			-- USUNIECIE LOKATORA Z POKOJU
			UPDATE Room SET Locator = NULL WHERE ID = @RoomID
			-- USUNIECIE LOKATORA
			DELETE FROM Locator WHERE RoomID = @RoomID AND ClientID = @ClientID
			-- ARCHIWIZACJA REZERWACJI
			INSERT INTO StayHistory(ClientID, RoomID, BegDate, EndDate) 
			VALUES				(@ClientID, @RoomID, @BegDate, @EndDate)
			FETCH NEXT FROM RoomEnumerator INTO @RoomID
			FETCH NEXT FROM ClientEnumerator INTO @ClientID
			FETCH NEXT FROM BegDateEnumerator INTO @BegDate
			FETCH NEXT FROM EndDateEnumerator INTO @EndDate
		END -- end while
	CLOSE RoomEnumerator
	DEALLOCATE RoomEnumerator
	CLOSE ClientEnumerator
	DEALLOCATE ClientEnumerator
END			
						
DROP PROCEDURE CheckLocatorEnd

EXEC CheckLocatorEnd

SELECT * FROM Reservation
SELECT * FROM Reservation WHERE EndDate <= CAST(GETDATE() AS date)
SELECT * FROM Client
UPDATE Client SET Reservation = NULL
SELECT * FROM Room
SELECT * FROM Locator
DELETE FROM Locator
UPDATE Room SET Locator = NULL
SELECT * FROM StayHistory


DECLARE @RoomIDToEnter5 int = (SELECT TOP 1 ID FROM Room WHERE Hotel = 'Urnova Wroclaw' AND Price = 599)
DECLARE @ClientIDToEnter1 int = (SELECT TOP 1 ID FROM Client WHERE Name = 'Dawid')
INSERT INTO Reservation(RoomID,BegDate,EndDate,Paid, ClientID) VALUES(@RoomIDToEnter5, DATEADD(day,-2,GETDATE()), GETDATE(), 0, @ClientIDToEnter1) 
DECLARE @RoomIDToEnter6 int = (SELECT TOP 1 ID FROM Room WHERE Hotel = 'Urnova Wroclaw')
DECLARE @ClientIDToEnter2 int = (SELECT TOP 1 ID FROM Client WHERE Name = 'Kasia')
INSERT INTO Reservation(RoomID,BegDate,EndDate,Paid, ClientID) VALUES(@RoomIDToEnter6, DATEADD(day,-5,GETDATE()), DATEADD(day,-2,GETDATE()), 0, @ClientIDToEnter2)

DELETE FROM Reservation WHERE ClientID != NULL

------------
-- procedura do tworzenia konta uzytkownika aplikacji
--
--
--

CREATE PROCEDURE CreateAccount(
	@Username varchar(30),
	@Password varchar(30),
	@AccessLevel int = 1,
	@IDCardSeries char(9) = NULL,
    @responseMessage NVARCHAR(250)='ok' OUTPUT)
AS
BEGIN
	IF(NOT EXISTS(SELECT * FROM AppUser WHERE Login = @Username))
	BEGIN			
			
		IF( @IDCardSeries IS NOT NULL)
		BEGIN
			DECLARE @newID int = (SELECT ID FROM AppUser WHERE Login = @Username)
			UPDATE Client
			SET Account = @newID
			WHERE IDCardSeries = @IDCardSeries -- przypisz konto do uzytkownika
		
			DECLARE @salt UNIQUEIDENTIFIER=NEWID()
			DECLARE @cryptedPassword VARCHAR(70) =  HASHBYTES('SHA2_512', @Password+CAST(@salt AS NVARCHAR(36)))
			INSERT INTO AppUser (Login,		Password,		Salt,						AccessLevel) 
			VALUES				(@Username, @cryptedPassword, CAST(@salt AS NVARCHAR(36)), @AccessLevel)
		END
		ELSE SET @responseMessage = 'Username in use'
	END
	ELSE SET @responseMessage = 'Username in use.'
END

DROP PROCEDURE CreateAccount

DELETE FROM AppUser WHERE Login != 'Admin' AND Login != 'Test'
SELECT * FROM AppUser 
SELECT * FROM Client

DECLARE @testID char(9) = (SELECT TOP 1 IDCardSeries FROM Client)
EXEC CreateAccount @Username='Test', @Password = 'Lubieplacki1', @IDCardSeries = @testID


EXEC CreateAccount @Username='Admin', @Password = 'NieLubiePlackow', @AccessLevel= 3

SELECT * FROM AppUser

SELECT * FROM Client


---------- procedura sprawdzajaca dane logowania

CREATE PROCEDURE LoginAttempt(
	@Username varchar(30),
	@Password varchar(30),
    @responseMessage NVARCHAR(250)='' OUTPUT,
	@AccessLevel int=0 OUTPUT
)
AS
BEGIN
	IF EXISTS( SELECT TOP 1 ID FROM AppUser WHERE Login = @Username)
	BEGIN
		DECLARE @salt varchar(36) = (SELECT TOP 1 Salt FROM AppUser WHERE Login = @Username)
		DECLARE @userID int = (SELECT TOP 1 ID FROM AppUser WHERE Login = @Username
							AND Password = HASHBYTES('SHA2_512', @Password+CAST(@salt AS NVARCHAR(36)))  )
		IF (@userID IS NULL)
			SET @responseMessage = 'Invalid password'
		ELSE
		BEGIN
			SET @responseMessage = 'Login Success'
			SET @AccessLevel = (SELECT TOP 1 AccessLevel FROM AppUser WHERE Login = @Username)
		END
	END
	ELSE
		SET @responseMessage = 'Invalid username'
END

DROP PROCEDURE LoginAttempt

SELECT * FROM AppUser

DECLARE	@response_Message nvarchar(250)
DECLARE	@access_level int
EXEC LoginAttempt 	@Username = 'Te1st',	@Password = 'Lubieplacki1', @responseMessage=@response_Message OUTPUT, @AccessLevel = @access_level OUTPUT
print @response_Message
print @access_level 


DECLARE	@response_Message nvarchar(250)
DECLARE	@access_level int
EXEC LoginAttempt 	@Username = 'Admin',	@Password = 'NieLubiePlackow', @responseMessage=@response_Message OUTPUT, @AccessLevel = @access_level OUTPUT
print @response_Message
print @access_level 



---
SELECT @@SERVERNAME;
SELECT  
   CONNECTIONPROPERTY('net_transport') AS net_transport,
   CONNECTIONPROPERTY('protocol_type') AS protocol_type,
   CONNECTIONPROPERTY('auth_scheme') AS auth_scheme,
   CONNECTIONPROPERTY('local_net_address') AS local_net_address,
   CONNECTIONPROPERTY('local_tcp_port') AS local_tcp_port,
   CONNECTIONPROPERTY('client_net_address') AS client_net_address 


   --


