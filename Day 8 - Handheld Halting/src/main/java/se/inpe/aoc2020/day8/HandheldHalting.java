package se.inpe.aoc2020.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class HandheldHalting {
    final Map<Integer, ProgramCode> programLines = new HashMap<>();
    int accumulator = 0;

    public static void main(String[] args) {
        new HandheldHalting();
    }

    public HandheldHalting() {
        try (Stream<String> stream = Files.lines(Paths.get("src/main/resources/day8-HandheldHalting.txt"))) {
            AtomicReference<Integer> i = new AtomicReference<>(1);
            stream.forEach(line -> addToProgramLines(i.getAndSet(i.get() + 1), line));
            runBootCode(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToProgramLines(Integer number, String fileLine) {
        String[] operationAndNumber = fileLine.split(" ");
        programLines.put(number, new ProgramCode(operationAndNumber[0],Integer.parseInt(operationAndNumber[1])));
    }

    private void runBootCode(int lineNumber) {
        ProgramCode programCode = programLines.get(lineNumber);

        if (programCode.getTimesVisited() == 1) {
            System.out.println("Second run on this line: " +lineNumber + " Accumulator is: " +accumulator);
            return;
        }
        programCode.increaseTimesVisited();

        switch (programCode.getOperation()) {
            case "nop" -> runBootCode(lineNumber+1);
            case "acc" -> {
                accumulator += programCode.getNumber();
                runBootCode(lineNumber+1);
            }
            case "jmp" -> runBootCode(lineNumber + programCode.getNumber());
            default -> throw new IllegalStateException("Unexpected value: " + programCode.getOperation());
        }
    }
}