package com.chasing.util;

import com.chasing.entity.Position;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvUtils {
    public static List<Position> readPositions(String csvPath) {
        ArrayList<Position> positions = new ArrayList<>();
        String lineStr;
        try (BufferedReader br = new BufferedReader(new FileReader(CsvUtils.class.getClassLoader().getResource("").getPath() + "input.csv"))) {
            br.readLine();
            while ((lineStr = br.readLine()) != null) {
                String[] line = lineStr.split(",");
                if (line.length != 2) {
                    throw new RuntimeException("Invalid csv format");
                }
                Position position = new Position(line[0], Integer.parseInt(line[1]));
                positions.add(position);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("CSV file not found");
        } catch (IOException e) {
            throw new RuntimeException("Read csv file error, please check csv format");
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid value of position size");
        }
        return positions;
    }
}
