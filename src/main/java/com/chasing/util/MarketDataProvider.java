package com.chasing.util;

import com.chasing.entity.Definition;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MarketDataProvider {

    private final Random random = new Random();
    private final PortfolioManager portfolioManager;
    private final Map<String, Double> stockPriceMap;
    private final double defaultInitialPrice = 100.0;

    public MarketDataProvider(List<Definition> definitions, PortfolioManager portfolioManager) {
        this.portfolioManager = portfolioManager;
        List<Definition> stockDefinitions = definitions.stream().filter(definition -> "Stock".equals(definition.getType())).distinct().collect(Collectors.toList());
        this.stockPriceMap = stockDefinitions.stream().collect(Collectors.toMap(
                Definition::getSymbol,
                definition -> defaultInitialPrice
        ));
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(stockDefinitions.size());
        for (Definition definition : definitions) {
            if (!"Stock".equals(definition.getType())) continue;
            MarketDataTask task = new MarketDataTask(definition);
            scheduledExecutor.scheduleWithFixedDelay(task, 2, 2, TimeUnit.SECONDS);
        }
    }

    class MarketDataTask implements Runnable {

        private final Definition definition;

        public MarketDataTask(Definition definition) {
            this.definition = definition;
        }

        @Override
        public void run() {
            double deltaT = 1.0 / (366 - LocalDate.now().getDayOfYear());
            double epsilon = random.nextGaussian();
            double expectedReturn = definition.getExpectedReturn();
            double volatility = definition.getVolatility();
            String stockSymbol = definition.getSymbol();
            double stockPrice = stockPriceMap.get(stockSymbol);
            double priceChangeFactor = Math.exp((expectedReturn - 0.5 * Math.pow(volatility, 2)) * deltaT + volatility * epsilon * Math.sqrt(deltaT));
            stockPrice = stockPrice * priceChangeFactor;
            if (stockPrice < 0) stockPrice = 0;
            portfolioManager.updateStockPrice(stockSymbol, stockPrice);
        }
    }
}