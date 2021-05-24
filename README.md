# Spotitube

## Instructions to run
1. Make sure to run the DDL script and have a working MySQL database server running.
2. Manually create a customer with a username, password, a first name and last name, you can set the token value to NULL 
3. Also create a few tracks in the Tracks table. 
4. Change the connectionString in ``src/main/resources/database.properties`` to match your MySQL credentials
5. Have a TomEE local server configured to deploy the ``spotitube-backend:war``. 
6. Run the back-end and go to ``ci.icaprojecten.nl/spotitube``
7. Login in with the server URL http://localhost:8080/spotitube and the customer username and password you created in step 2. 
8. You should now be able to use the client to create, edit and delete playlists and tracks.

To use mongoDB, populate a mongo instance with the data in ``mongoCollections.txt``. "//" denotes a collection name, followed by the data in mongo-JSON format.

### Changing the database
To change the database the application uses, all you have to do is change the database type from MYSQL to SQLITE or MONGODB in ``src/main/resources/database.properties``, no recompiling necessary!
NOTE: The SQLite and MongoDB are populated with only one customer (username: sebas, password: password) and three tracks.

