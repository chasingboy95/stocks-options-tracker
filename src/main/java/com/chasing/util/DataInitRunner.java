package com.chasing.util;

import com.chasing.entity.Definition;
import com.chasing.entity.Position;
import com.chasing.repository.DefinitionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitRunner implements ApplicationRunner {

    @Autowired
    private DefinitionsRepository definitionsRepository;

    @Override
    public void run(ApplicationArguments args) {
        List<Position> portfolio = CsvUtils.readPositions("input.csv");
        List<Definition> definitions = definitionsRepository.findAll();
        PortfolioManager portfolioManager = new PortfolioManager(portfolio, definitions);
        new MarketDataProvider(definitions, portfolioManager);
    }
}