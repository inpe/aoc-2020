package se.inpe.aoc2020.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class HandheldHalting {
    final Map<Integer, ProgramCode> programLines = new HashMap<>();
    final ArrayList<Integer> lineNumbersWithNopOrJmp = new ArrayList<>();
    int accumulator = 0;

    public static void main(String[] args) {
        new HandheldHalting();
    }

    public HandheldHalting() {
        try (Stream<String> stream = Files.lines(Paths.get("src/main/resources/day8-HandheldHalting.txt"))) {
            AtomicReference<Integer> i = new AtomicReference<>(1);
            stream.forEach(line -> addToProgramLines(i.getAndSet(i.get() + 1), line));
            programLines.forEach((number, code) -> addToLineNumbersWithNopOrJmp(number, code.getOperation()) );

            //Part 1
            runBootCode(1, true);

            //Part 2
            boolean exitedNormally = false;
            while (!exitedNormally && !lineNumbersWithNopOrJmp.isEmpty()) {
                accumulator = 0;
                Integer lineWithNopOrJmp = lineNumbersWithNopOrJmp.remove(0);
                programLines.forEach((number, code) -> code.resetTimesVisited());
                switchNopAndJmp(lineWithNopOrJmp);
                exitedNormally = runBootCode(1, false);
                switchNopAndJmp(lineWithNopOrJmp);
            }
            System.out.println("SUCCESS!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToProgramLines(Integer lineNumber, String fileLine) {
        String[] operationAndNumber = fileLine.split(" ");
        programLines.put(lineNumber, new ProgramCode(operationAndNumber[0], Integer.parseInt(operationAndNumber[1])));
    }

    private void addToLineNumbersWithNopOrJmp(Integer number, String operation) {
        if (operation.equalsIgnoreCase(ProgramCode.JMP) || operation.equalsIgnoreCase(ProgramCode.NOP)) {
            lineNumbersWithNopOrJmp.add(number);
        }
    }

    private void switchNopAndJmp(Integer lineNumber) {
        ProgramCode programCode = programLines.get(lineNumber);

        if (programCode.getOperation().equalsIgnoreCase(ProgramCode.NOP)) {
            programCode.setOperation(ProgramCode.JMP);
        } else if (programCode.getOperation().equalsIgnoreCase(ProgramCode.JMP)){
            programCode.setOperation(ProgramCode.NOP);
        }
    }

    private boolean runBootCode(int lineNumber, boolean checkHaltingLine) {
        //Part 2
        if (lineNumber > programLines.size() + 1) {
            System.out.println("Program did NOT exit normally, lineNumber is: " + lineNumber);
            return false;
        }
        else if (lineNumber == programLines.size() + 1) {
            System.out.println("Program exited normally, accumulator is: " + accumulator);
            return true;
        }
        else {
            ProgramCode programCode = programLines.get(lineNumber);

            if (programCode.getTimesVisited() > 1) {
                if (checkHaltingLine) {
                    System.out.println("Second round on this line: "+lineNumber+" program is halting");
                }
                return false;
            }
            else {
                programCode.increaseTimesVisited();
                switch (programCode.getOperation()) {
                    case "nop" -> {
                        return runBootCode(lineNumber + 1, checkHaltingLine);
                    }
                    case "acc" -> {
                        accumulator += programCode.getNumber();
                        return runBootCode(lineNumber + 1, checkHaltingLine);
                    }
                    case "jmp" -> {
                        return runBootCode(lineNumber + programCode.getNumber(), checkHaltingLine);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + programCode.getOperation());
                }
            }
        }
    }
}