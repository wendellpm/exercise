Should be able to insert ad.war into a local web server, e.g., Tomcat, then a browser is run and the address bar of a browser is set to http://localhost:8080/ad

To run the application from Tomcat, there are two client user interfaces, within the com/wpmassociates/exercise/client package, one from the command line, one a graphical client. Each is run by clicking on the class name, then right clicking on the mouse to select Run As, then selecting "Java application" as the option. The command line client prompts for the user id, duration (in days) and the ad content from the command line. The graphical client generates a window where there are input boxes for partner id, duration and ad content, plus a Send button to send the data to the server.

To check for a given partner id a browser is opened, then the URL and the partner id are entered into the address box. Assuming localhost: http://localhost:8080/ad/<partnerId>. That will return either the JSON code for that partner id or some other message, if there is an error, as plain text in the browser window.

Alternatively, the code should be downloadable through git to be placed in an Eclipse IDE, then compiled and run through a server instance (e.g. Tomcat) in the IDE. A browser is then opened and pointed to the url listed above.

There is a default.properties file within the src/main/resources directory that contains key/values to determine whether the application should use a SQL database (in this case MySQL) or use an in-memory database. 

The property for this has the key "useMap" which is set by default to the value "yes" meaning that an in-memory database is used. 

If the value for the "useMap" key is set to "no", then the SQL database is used. The other key/values indicated below must be changed to use with the local database, specifically a username and password that has access to the database and relevant database table.

The database, named "json", needs to be created first from the mysql prompt as follows:

mysql>create database json;

To use the MySQL database use the enclosed json.sql file from command line. This creates a table, also named "json", in the data base "json", without data. The username and password values must be those that work on the local mysql server.

$ mysql -u <username> -p <password> json < json.sql

MySQL may require the <password> value to be set separately, thus:

$ mysql -u <username> -p json < json.sql <press enter key>
enter password:<password> <press enter key>

Then set the key/values in the default.properties, as follows, before running the application server:

databaseName=json	leave as is
useMap=no			value must be "no"
dbUsername=<username>		enter username value that works on local server
dbPassword=<password>		enter password value that works on local server
mysqlUrl=jdbc:mysql://localhost:3306	leave as is, default port for mysql
driverName=com.mysql.jdbc.Driver		leave as is

 