# HotelDatabase

Student project showing very basic communication between database and java application.

Hotel Database is presented on diagram in /docs with some of its functionality described in other file.
To recreate database, you can execute queries in /src/sql in order:
1. CreateQuery,
2. DataFillQuery,
3. Create triggers and stored procedures from SystemQuery.

Java application present simple views from users of database.
You can register account and then login with your credentials to database system,
where you can check available rooms and reserve one if you want.
If you login as admin, you can look up registered users and delete them at will.
