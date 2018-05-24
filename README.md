# GW.Identification-BE
**Flyway instructions:**

Use in command line `mvn flyway:<command>`

_Commands list_:
1. migrate - Migrates the database
2. clean - Drops all objects in the configured schemas
3. info - Prints the details and status information about all the migrations
4. validate - Validates the applied migrations against the ones available on the classpath
5. baseline - Baselines an existing database, excluding all migrations up to and including baselineVersion
6. repair - Repairs the schema history table

Official instructions https://flywaydb.org/documentation/maven/