package se.inpe.aoc2020.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class AdapterArray {

    public static void main(String[] args) {
        new AdapterArray();
    }

    public AdapterArray() {

        try (Stream<String> stream = Files.lines(Paths.get("src/main/resources/day10-AdapterArray.txt"))) {
            List<Integer> orderedList = getOrderedList(stream);

            //Starting with charging outlet which is 0
            int currentJolt = 0;
            int oneJoltDiff = 0;
            int threeJoltDiff =  0;

            for (Integer next : orderedList) {
                int difference = next - currentJolt;

                if (difference == 1) {
                    oneJoltDiff++;
                }
                else if (difference == 3) {
                    threeJoltDiff++;
                }
               currentJolt = next;
            }
            //We need to count the difference from highest jolt to device built-in adapter which is 3
            threeJoltDiff = threeJoltDiff + 1;

            System.out.printf("Number of 1-jolt differences: %d, number of 3-jolt diffences: %d%n", oneJoltDiff, threeJoltDiff);
            System.out.println("Sooo, multiplied means: " + oneJoltDiff * threeJoltDiff );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> getOrderedList(Stream<String> stream) {
        return stream
                .map(Integer::parseInt)
                .sorted()
                .collect(toList());
    }
}