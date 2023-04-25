# Short version of a lottery application

## To run app on local environment: 
1. Run `docker compose up` in cmd from ./local_env directory
2. Connect to database in container using credentials from ./local_env/docker-compose.yml
3. Start the application with `gradlew bootRun --args='--spring.profiles.active=dev'` or using IDE
4. Run database script ./local_env/data.sql to create admin user
5. Now you can access the API on http://localhost/8080, swagger may be accessed on http://localhost/8080/swagger/index.html

## App functionality:
1. To start work with the app you need to create a lottery entity through POST /api/lotteries endpoint using the admin user(his credentials are in ./local_env/data.sql)
2. To create a player user use POST /api/auth/signup endpoint
3. To access the whole list of lotteries stored in the db call GET /api/lotteries
4. To register a player user to a specific lottery login under this user and call POST /api/ballots
5. To view all ballots of your current user with their statuses call GET /api/ballots
