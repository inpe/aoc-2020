package se.inpe.aoc2020.day8;

public class ProgramCode {
    private final String operation;
    private final int number;
    private int timesVisited;

    public ProgramCode(String operation, int number) {
        this.operation = operation;
        this.number = number;
        this.timesVisited = 0;
    }

    public String getOperation() {
        return operation;
    }

    public int getNumber() {
        return number;
    }

    public int getTimesVisited() {
        return timesVisited;
    }
    public void increaseTimesVisited() {
        timesVisited += 1;
    }
}
