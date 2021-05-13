Repository contains: 
- class-diagram
- domain layer source code
- DB schema
of university project area.

The code is written by Alexander Goncharenko during his learning at foxminded JavaEE course.

To run the project you need to:
1. Download and install Tomcat, at the moment the latest is https://tomcat.apache.org/download-90.cgi
2. Add to <tomcat server folder>/servert.xml file, inside </GlobalNamingResources> tag the next text:
   ```
   <Resource name="jdbc/foxdb" 
      global="jdbc/foxdb" 
      auth="Container" 
      type="javax.sql.DataSource" 
      driverClassName="org.postgresql.Driver" 
      url="jdbc:postgresql://localhost/foxdb" 
      username="foxdb" 
      password="foxdb"       
      maxActive="100" 
      maxIdle="20" 
      minIdle="5" 
      maxWait="10000" />
   ```
3. Add to <tomcat server folder>/context.xml file inside <Context> tag the next text:
   ```
   <ResourceLink name="jdbc/foxdb"
                 global="jdbc/foxdb"
                 auth="Container"
                 type="javax.sql.DataSource" />
   ```
4. Dowlonad and install Postgresql, at the moment the latest is https://www.postgresql.org/ftp/source/v12.0/
5. To create test database switch to the "src/main/resources/db/manual_scripts" directory and execute command
   $ sudo -u postgres psql -f create_foxtestdb.sql
6. To create application database switch to the "src/main/resources/db/manual_scripts" directory and execute command
   $ sudo -u postgres psql -f create_foxdb.sql
7. Download and install maven at https://maven.apache.org/download.cgi
8. Build project with "mvn clean install" command in the console
9. To populate application database with some dummy data switch to the "src/main/resources/db/manual_scripts" directory and execute next commands:
   $ export PGPASSWORD='foxdb'
   $ psql -h localhost -U foxdb -d foxdb -f populate_tables.sql
10. Configure APT in your IDE. For Eclipse check the next tutorial https://wiki.eclipse.org/UserGuide/JPA/Using_the_Canonical_Model_Generator_(ELUG)

That's all!

To run the application, if you are using eclipse, you can simply click right button over the project in the project tree and click "Run as" -> "Run on server".

If you need, for some reason, to drop all the application tables, you could use "src/main/resources/db/manual_scripts/drop_all_tables.sql" script.
