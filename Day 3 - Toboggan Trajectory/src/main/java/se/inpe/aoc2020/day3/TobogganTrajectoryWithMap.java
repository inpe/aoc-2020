package se.inpe.aoc2020.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class TobogganTrajectoryWithMap {
    Map<Integer, Map<Integer, Character>> map = new HashMap<>();

    public static void main(String[] args) {
        new TobogganTrajectory();
    }

    /* File input:
        . = no tree
        # = tree
    */
    public TobogganTrajectoryWithMap() {
        try (Stream<String> stream = Files.lines(Paths.get("src/main/resources/day3-TobogganTrajectory.txt"))) {
            AtomicReference<Integer> i = new AtomicReference<>(1);
            stream.forEach(line -> addLineToMap(i.getAndSet(i.get() + 1), line));

            //Part 1
            System.out.println("Number of trees is: " + findNumberOfTrees(3, 1));

            //Part 2
            System.out.println("Multiplied number of trees for above slopes=" +
                    findNumberOfTrees(1,1) *
                            findNumberOfTrees(3, 1) *
                            findNumberOfTrees(5, 1) *
                            findNumberOfTrees(7, 1) *
                            findNumberOfTrees(1, 2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int findNumberOfTrees(int right, int down) {
        int rowNumber = 1;
        int charNumber = 1;
        int numberOfChars = map.get(1).size();
        int numberOfTrees = 0;

        //om raden tar slut, tänk en till likadan map på höger sida och fortsätt
        while ((rowNumber += down) <= map.size()) {
            charNumber += right;
            if (charNumber > numberOfChars) {
                charNumber = charNumber - numberOfChars;
            }
            if (map.get(rowNumber).get(charNumber) == '#') {
                numberOfTrees++;
                System.out.println("Träd funnet! Rad:" + rowNumber + " kolumn:" + charNumber);
            }
        }
        return numberOfTrees;
    }

    private void addLineToMap(Integer lineNumber, String fileLine) {
        Map<Integer, Character> line = new HashMap<>();

        for (int i = 0; i < fileLine.length(); i++) {
            line.put(i+1, fileLine.charAt(i) );
        }
        map.put(lineNumber, line);
    }
}

