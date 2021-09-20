# Felipe Teles dos Santos

![board](docs/imgs/board.png)

## Run application:
```
./mvnw package
./mvnw spring-boot:run
```
## API

The API uses:

- Java version: 11
- Spring boot  2.5.4
- H2 Database Engine

Basic API documentation swagger file: [swagger.yml](docs/api/swagger.yml).

## UI

The UI is developed in:
- React 17.0.2

To run it isolated from the java project it is necessary:
- node 14.17.6
- yarn 1.22.5

### Running it isolated from java:

The config is located in the ui/env.sample, to change it:
- copy `.env.sample` to `.env.local`
- replace the value of the `REACT_APP_SERVER_URL` property with the API base URL
- run `yarn` to download dependencies
- run `yarn start` to start the react app