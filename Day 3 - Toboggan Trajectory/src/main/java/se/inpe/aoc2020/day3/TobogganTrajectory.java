package se.inpe.aoc2020.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class TobogganTrajectory {

    public static void main(String[] args) {
        new TobogganTrajectory();
    }

    public TobogganTrajectory() {
        try (Stream<String> stream = Files.lines(Paths.get("src/main/resources/day3-TobogganTrajectory.txt"))) {
            List<char[]> map = new ArrayList<>();
            stream.forEach(line -> map.add(line.toCharArray()));

            //Part 1
            System.out.println("Number of trees is: " + findNumberOfTrees(map,3, 1));

            //Part 2
            System.out.println("Multiplied number of trees for given slopes=" +
                    findNumberOfTrees(map, 1,1) *
                    findNumberOfTrees(map, 3, 1) *
                    findNumberOfTrees(map, 5, 1) *
                    findNumberOfTrees(map, 7, 1) *
                    findNumberOfTrees(map, 1, 2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* The map consists of . and #. The # indicates a tree */
    private int findNumberOfTrees(List<char[]> map, int right, int down) {
        int numberOfTrees = 0;
        int rowNumber = 0;
        int charNumber = 0;
        int numberOfChars = map.get(0).length;

        while ((rowNumber += down) < map.size()) {
            charNumber += right;
            if (charNumber >= numberOfChars) {
                //The map repeats itself to the right,
                // but instead of adding things we continue from the beginning of row
                charNumber = charNumber - numberOfChars;
            }
            if (map.get(rowNumber)[charNumber] == '#') {
                numberOfTrees++;
            }
        }
        return numberOfTrees;
    }
}