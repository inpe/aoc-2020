package se.inpe.aoc2020.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class HandyHaversacks {
    final Map<String, Map<String, Integer>> rules = new HashMap<>();

    public static void main(String[] args) {
        new HandyHaversacks();
    }

    public HandyHaversacks() {
        String myBagColor= "shiny gold";

        try (Stream<String> stream = Files.lines(Paths.get("src/main/resources/day7-HandyHaversacks.txt"))) {
            stream.forEach(this::addToRules);
            System.out.printf("Number of bags that contain %s bag: %s%n", myBagColor, getBagsContainingColor(myBagColor).size());
            System.out.printf("Number of bags inside %s bag: %s%n", myBagColor, getNumberOfBagsInside(myBagColor));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToRules(String line) {
        String[] bagColorAndContents = trimRule(line).split(" contain ");
        String bagColor = bagColorAndContents[0];
        String bagContent = bagColorAndContents[1];
        Map<String, Integer> contents = new HashMap<>();

        if (!bagContent.equalsIgnoreCase("no other")) {
            for (String part : bagContent.split(", ")) {
                Integer number = Integer.valueOf(part.substring(0, part.indexOf(" ")));
                String color = part.substring(part.indexOf(" ") + 1);
                contents.put(color, number);
            }
        }
        rules.putIfAbsent(bagColor, contents);
    }

    private String trimRule(String line) {
        return line.replace(" bags", "")
                .replace(" bag", "")
                .replace(".", "");
    }

    private int containsTargetBag(Map<String, Integer> content, String color) {
        int count = 0;

        if (content.isEmpty()) {
            return 0;
        }

        for (String childColor : content.keySet()) {
            if (childColor.equalsIgnoreCase(color)) {
                count += 1;
            } else {
                count += containsTargetBag(rules.get(childColor), color);
            }
        }
        return count;
    }

    private ArrayList<String> getBagsContainingColor(String color) {
        ArrayList<String> bagColors = new ArrayList<>();

        for (String bagColor : rules.keySet()) {
            if (containsTargetBag(rules.get(bagColor), color) > 0) {
                bagColors.add(bagColor);
            }
        }
        return bagColors;
    }

    private int getNumberOfBagsInside(String color) {
        return countBagsInside(rules.get(color));
    }

    private int countBagsInside(Map<String, Integer> content) {
        AtomicInteger count = new AtomicInteger();

        if (content.isEmpty()) {
            return 0;
        }
        content.forEach((color, numberOfBags) -> count.addAndGet(numberOfBags + numberOfBags * countBagsInside(rules.get(color))));
        return count.get();
    }
}