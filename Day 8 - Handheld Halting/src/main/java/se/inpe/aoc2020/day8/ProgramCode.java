package se.inpe.aoc2020.day8;

public class ProgramCode {
    private String operation;
    private final int number;
    private int timesVisited;

    public static final String NOP = "nop";
    public static final String ACC = "acc";
    public static final String JMP = "jmp";

    public ProgramCode(String operation, int number) {
        this.operation = operation;
        this.number = number;
        this.timesVisited = 0;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
    public void resetTimesVisited() {
        timesVisited = 0;
    }
}
