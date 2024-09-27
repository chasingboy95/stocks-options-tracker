package com.chasing.util;

import com.chasing.entity.Definition;
import com.chasing.entity.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class PortfolioManager {
    private static final Logger log = LoggerFactory.getLogger(PortfolioManager.class);
    private final Map<String, Double> stockPrices = new HashMap<>();
    private final List<Position> portfolio;
    private final Map<String, Definition> definitionMap;

    public PortfolioManager(List<Position> portfolio, List<Definition> definitions) {
        this.portfolio = portfolio;
        this.definitionMap = definitions.stream().collect(Collectors.toMap(
                Definition::getSymbol,
                definition -> definition
        ));
    }

    public void updateStockPrice(String symbol, double newPrice) {
        stockPrices.put(symbol, newPrice);
        printPortfolioValue();
    }

    private void printPortfolioValue() {
        StringBuffer logs = new StringBuffer();
        double totalValue = 0;
        logs.append("\n##  Market Data Update\n");
        HashMap<String, Double> positionPriceMap = new HashMap<>();
        for (Position position : portfolio) {
            Definition definition = definitionMap.get(position.getSymbol());
            if (Objects.isNull(definition)) {
                throw new RuntimeException("Definition not found for symbol: " + position.getSymbol());
            }
            double positionPrice;
            switch (definition.getType()) {
                case "Stock":
                    positionPrice = stockPrices.get(position.getSymbol());
                    logs.append(String.format("%-10s change to $%.2f%n", position.getSymbol(), positionPrice));
                    break;
                case "Call":
                    positionPrice = OptionPricingUtils.calculateCallPrice(getStockPriceByDefinition(definition), definition.getStrikePrice(), getYearsToMaturityDate(definition.getMaturityDate()), definition.getVolatility(), 0.02);
                    break;
                case "Put":
                    positionPrice = OptionPricingUtils.calculatePutPrice(getStockPriceByDefinition(definition), definition.getStrikePrice(), getYearsToMaturityDate(definition.getMaturityDate()), definition.getVolatility(), 0.02);
                    break;
                default:
                    throw new RuntimeException("Invalid definition type: " + definition.getType());
            }
            positionPriceMap.put(position.getSymbol(), positionPrice);
        }
        logs.append("\n## Portfolio\n");
        logs.append(String.format("%-30s %-15s %-15s %-15s%n", "symbol", "price", "qty", "value"));
        for (Position position : portfolio) {
            double positionPrice = positionPriceMap.get(position.getSymbol());
            double positionValue = positionPrice * position.getPositionSize();
            totalValue += positionValue;
            logs.append(String.format("%-30s %-15.2f %-15d %-15.2f%n", position.getSymbol(), positionPrice, position.getPositionSize(), positionValue));
        }
        logs.append(String.format("%n#Total portfolio %55.2f%n", totalValue));
        System.out.print(logs);
    }

    private double getYearsToMaturityDate(LocalDate maturityDate) {
        LocalDate currentDate = LocalDate.now();
        long daysToMaturity = ChronoUnit.DAYS.between(currentDate, maturityDate);
        return (double) daysToMaturity / 365.0;
    }

    private double getStockPriceByDefinition(Definition definition) {
        String stockName = definition.getSymbol();
        if (!"Stock".equals(definition.getSymbol())) {
            stockName = definition.getSymbol().split("-")[0];
        }
        Double price = stockPrices.get(stockName);
        if (Objects.isNull(price)) {
            throw new RuntimeException("Definition not found for symbol: " + definition.getSymbol());
        }
        return price;
    }
}