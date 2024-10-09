# Portfolio value calculator (maven version)

## Description
1. Get the positions from a mock CSV position file: 
   - You can set up the positions in the file `input.csv`, and there already have some data in it;
   - The implementation of code in `com.chasing.util.CsvUtils`;
2. Get the security definitions from an embedded database:
   - I use the H2 database to store the security definitions, and the spring data jpa for the data access layer;
   - There are one table only in the database, ref: `com.chasing.entity.Definition`;
   - You can set up your security definitions in the file `data.sql`, and there already have some data in it;
3. Implement a mock market data provider that publishes stock prices:
   - The implementation of code in `com.chasing.util.MarketDataProvider`;
   - I set the stock default initial price as 100, and the price will change randomly (discrete time geometric Brownian motion) every 2 second;
4. Calculate the real time option price with the underlying price:
   - The implementation of code in `com.chasing.util.OptionPricingUtils`, there are 2 public methods for calculating the (Call/Put)option price;
5. Publish & Print the position’s market value in real time:
   - The implementation of code in `com.chasing.util.PortfolioManager`;
   - Inside the `PortfolioManager`, I use a `ScheduledExecutorService` the `updateStockPrice` method will be called while the market has a new price;

## How to run
run the `Application.java`, it's a springboot application, the `DataInitRunner` will start everything in seconds, and you can see the output in the console.