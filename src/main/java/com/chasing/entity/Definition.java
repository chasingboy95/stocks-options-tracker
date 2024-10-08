package com.chasing.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Definition {
    @Id
    private String symbol;
    private String type;
    private Double strikePrice;
    private LocalDate maturityDate;
    private Double volatility;
    private Double expectedReturn;

    public Definition() {

    }

    public Definition(String symbol, String type, double strikePrice, LocalDate maturityDate, double volatility, double expectedReturn) {
        this.symbol = symbol;
        this.type = type;
        this.strikePrice = strikePrice;
        this.maturityDate = maturityDate;
        this.volatility = volatility;
        this.expectedReturn = expectedReturn;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getType() {
        return type;
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public double getVolatility() {
        return volatility;
    }

    public double getExpectedReturn() {
        return expectedReturn;
    }

    @Override
    public String toString() {
        return "Definition{" +
                "symbol='" + symbol + '\'' +
                ", type='" + type + '\'' +
                ", strikePrice=" + strikePrice +
                ", maturityDate=" + maturityDate +
                ", volatility=" + volatility +
                ", expectedReturn=" + expectedReturn +
                '}';
    }
}
