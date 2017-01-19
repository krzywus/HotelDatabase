CREATE DATABASE HotelDatabase;



------------------------------------------------------


CREATE TABLE Hotel(
	Name				varchar(100) PRIMARY KEY not null,
	Town				varchar(50) not null,
	Adress				varchar(100) not null,
	ReceptionPhone		varchar(15),
	Email				varchar(40) not null,
	Stars				tinyint,
)


CREATE TABLE Features(
	ID			int IDENTITY(1,1) PRIMARY KEY not null,
	TV			bit not null,
	Minibar		bit not null,
	Jacuzzi		bit not null,
	Internet	bit not null,
	Fireplace	bit not null,
	Kitchen		bit not null,
)

CREATE TABLE RoomType(
	ID					int IDENTITY(1,1) PRIMARY KEY not null,
	TypeName			varchar(30) unique not null,
	SingleBeds			int not null,
	DoubleBeds			int not null,
	MaxNumberOfPeople	int not null,
)

CREATE TABLE Client(
	ID				int IDENTITY(1,1) PRIMARY KEY not null,
	Name			varchar(30) not null,
	Surname			varchar(30) not null,
	Phone			varchar(15),
	IDCardSeries	char(9) not null unique,
	Reservation		int,
	Account			int,
)

CREATE TABLE Reservation(
	ID			int IDENTITY(1,1) PRIMARY KEY not null,
	BegDate		date not null,
	EndDate		date not null,
	Paid		bit not null,
	RoomID		int,
	ClientID	int,
)


CREATE TABLE Locator(
	ID			int IDENTITY(1,1) PRIMARY KEY not null,
	RoomID		int,
	ClientID	int,
)


CREATE TABLE Room(
	ID				int IDENTITY(1,1) PRIMARY KEY not null,
	Hotel			varchar(100) not null,
	RoomNumber		int not null,
	FeaturesID		int not null,
	Type			int not null,
	Price			money,
	Locator			int,
)

CREATE TABLE StayHistory(
	ID			int IDENTITY(1,1) PRIMARY KEY not null,
	ClientID	int,
	RoomID		int,
	BegDate		date not null,
	EndDate		date not null,
)

CREATE TABLE AppUser(
	ID			int IDENTITY(1,1) PRIMARY KEY not null,
	Login		varchar(30) not null,
	Password	varchar(70) not null,
	Salt		varchar(36) not null,
	AccessLevel int not null,
)

------------------- CONSTRAINTS

ALTER TABLE Room
ADD FOREIGN KEY (Hotel) REFERENCES Hotel(Name);

ALTER TABLE Room
ADD FOREIGN KEY (FeaturesID) REFERENCES Features(ID);

ALTER TABLE Room
ADD FOREIGN KEY (Type) REFERENCES RoomType(ID);

ALTER TABLE Room
ADD FOREIGN KEY (Locator) REFERENCES Locator(ID);

--

ALTER TABLE Locator
ADD FOREIGN KEY (RoomID) REFERENCES Room(ID);

ALTER TABLE Locator
ADD FOREIGN KEY (ClientID) REFERENCES Client(ID);

---

ALTER TABLE Client
ADD FOREIGN KEY (Account) REFERENCES AppUser(ID);

ALTER TABLE Client
ADD FOREIGN KEY (Reservation) REFERENCES Reservation(ID);

--

ALTER TABLE Reservation
ADD FOREIGN KEY (RoomID) REFERENCES Room(ID);

ALTER TABLE Reservation
ADD FOREIGN KEY (ClientID) REFERENCES Client(ID);

--


ALTER TABLE StayHistory
ADD FOREIGN KEY (RoomID) REFERENCES Room(ID);

ALTER TABLE StayHistory
ADD FOREIGN KEY (ClientID) REFERENCES Client(ID);





--- to drop constraint?? not tested enough

DECLARE @ConstraintName VARCHAR(256)
SET @ConstraintName = (
     SELECT             obj.name
     FROM               sys.columns col 

     LEFT OUTER JOIN    sys.objects obj 
     ON                 obj.object_id = col.default_object_id 
     AND                obj.type = 'F' 

     WHERE              col.object_id = OBJECT_ID('Client') 
     AND                obj.name IS NOT NULL
     AND                col.name = 'Account'
)   

IF(@ConstraintName IS NOT NULL)
BEGIN
	print @ConstraintName
    EXEC ('ALTER TABLE Client DROP CONSTRAINT '+@ConstraintName)
END
