package com.chasing.entity;

import java.util.Objects;

public class Position {
    private String symbol;
    private int positionSize;

    public Position() {

    }

    public Position(String symbol, int positionSize) {
        this.symbol = symbol;
        this.positionSize = positionSize;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getPositionSize() {
        return positionSize;
    }

    public void setPositionSize(int positionSize) {
        this.positionSize = positionSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return positionSize == position.positionSize && Objects.equals(symbol, position.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, positionSize);
    }
}
