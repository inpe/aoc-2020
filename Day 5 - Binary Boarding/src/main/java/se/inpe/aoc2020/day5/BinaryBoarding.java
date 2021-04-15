package se.inpe.aoc2020.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BinaryBoarding {

    public static void main(String[] args) {
        new BinaryBoarding();
    }

    public BinaryBoarding() {
        try (Stream<String> stream = Files.lines(Paths.get("src/main/resources/day5-BinaryBoarding.txt"))) {
            List<Integer> seats = stream
                    .map(line -> Integer.parseInt(convertToBinaryString(line),2))
                    .sorted()
                    .collect(Collectors.toList());

            //Part 1, highest seatnumber.
            System.out.println("Highest seat ID: " + seats.get(seats.size()-1));

            //Part 2

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Front or Back (0 or 1), Left or Right (0 or 1) */
    private String convertToBinaryString(String line) {
        String binaryString;
        binaryString = line.replace('F', '0')
                .replace('B', '1')
                .replace('L', '0')
                .replace('R', '1');
        return binaryString;
    }
}
