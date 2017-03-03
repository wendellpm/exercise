Should be able to insert ad.war into
a local web server, e.g., Tomcat, then
run from localhost:8080/ad

Alternatively, the code should be downloadable through
git to be place in Eclipse IDE

There is a default.properties file that contains
key/values to determine first, whether the application
should use a SQL database (in this case MySQL) or use 
an in-memory database. If the key/value for
"useMap" key is set to "no" as value, then the SQL database is
used. The other key/values indicated must be changed
to use with the local database.

The default property for "useMap" is set to "yes" meaning
that the in-memory database is used.

To use the MySQL database use the json.sql file from command line to create the database and table without data:

$ mysql -u<username> -p<password> json < json.sql

Then set the key/values in the default.properties:
databaseName=json	leave as is
useMap=no			change to no
dbUsername=<username>		enter username used
dbPassword=<password>		enter password used
mysqlUrl=jdbc:mysql://localhost:3306	leave as is
driverName=com.mysql.jdbc.Driver		leave as is
 