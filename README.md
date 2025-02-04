# kickstart-jersey-webservices-template
Kickstarter enterprise web application template that hosts webservice endpoints set up for bearer token authentication.

This kickstart webservices application has everything you need to start using bearer token authentication, follow directions below for configuration settings.

**NOTE** Basic user roles configured are **ADMIN** and **USER**.

1. MySQL driver jar (add this to your server installation in a path like within a liberty server:  ${wlp.install.dir}\databasedriverswas9\mysql)
2. Datasource configuration for liberty server is below:
```
  <jdbcDriver id="mysqlJdbcDriver">
    <library>
      <fileset dir="${wlp.install.dir}\databasedriverswas9\mysql" includes="mysql-connector-java-5.1.48.jar"/>
    </library>
  </jdbcDriver>
  
  <dataSource id="webapi" jdbcDriverRef="mysqlJdbcDriver" jndiName="jdbc/WebAPI" type="javax.sql.ConnectionPoolDataSource">
    <properties URL="jdbc:mysql://computerXX:3306/webapi" password="itsd" useSSL="false" user="isu000is"/>
  </dataSource>
```
3. Build your webapi database using the provided script. Modify script to contain your active directory signons or whomever else you need in there
