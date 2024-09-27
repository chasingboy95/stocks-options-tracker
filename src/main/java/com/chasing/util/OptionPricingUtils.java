package com.chasing.util;

/**
 * Utility class for option pricing calculations.
 */
public class OptionPricingUtils {

    public static double calculateCallPrice(double stockPrice, double strikePrice, double timeToMaturity, double volatility, double riskFreeRate) {
        double d1 = (Math.log(stockPrice / strikePrice) + (riskFreeRate + 0.5 * Math.pow(volatility, 2)) * timeToMaturity) / (volatility * Math.sqrt(timeToMaturity));
        double d2 = d1 - volatility * Math.sqrt(timeToMaturity);
        return stockPrice * normalDistribution(d1) - strikePrice * Math.exp(-riskFreeRate * timeToMaturity) * normalDistribution(d2);
    }

    public static double calculatePutPrice(double stockPrice, double strikePrice, double timeToMaturity, double volatility, double riskFreeRate) {
        double d1 = (Math.log(stockPrice / strikePrice) + (riskFreeRate + 0.5 * Math.pow(volatility, 2)) * timeToMaturity) / (volatility * Math.sqrt(timeToMaturity));
        double d2 = d1 - volatility * Math.sqrt(timeToMaturity);
        return strikePrice * Math.exp(-riskFreeRate * timeToMaturity) * normalDistribution(-d2) - stockPrice * normalDistribution(-d1);
    }

    private static double normalDistribution(double x) {
        return 0.5 * (1 + erf(x / Math.sqrt(2)));
    }

    public static double erf(double x) {
        int n = 100;
        double h = x / n;

        double result = 0.0;
        for (int i = 0; i < n; i++) {
            double xi = i * h;
            double xi1 = (i + 1) * h;
            result += (Math.exp(-xi * xi) + Math.exp(-xi1 * xi1)) / 2.0;
        }
        result *= h / Math.sqrt(Math.PI);

        return 2.0 / Math.sqrt(Math.PI) * result;
    }

}