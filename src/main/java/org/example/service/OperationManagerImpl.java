package org.example.service;

import org.example.model.Line;
import org.example.model.LineCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class OperationManagerImpl implements OperationManager {

    private final List<Line> resultGroupLine;
    private final LineCollector lineCollector;

    public OperationManagerImpl() {
        this.lineCollector = new LineCollector(new ArrayList<>());
        this.resultGroupLine = new ArrayList<>();
    }

    public OperationManagerImpl(LineCollector lineCollector) {
        this.lineCollector = lineCollector;
        this.resultGroupLine = new ArrayList<>();
    }

    @Override
    public void findLinesForInterval() {
        lineCollector.sortData();
        List<Line> lines = lineCollector.getLines();
        resultGroupLine.add(lines.getFirst());
        int max = lines.getFirst().end();
        int i = 1;

        while (i < lines.size()) {
            if (lines.get(i).end() <= max) {
                i++;
                continue;
            }
            int resIndex = -1;
            int highestEnd = max;

            while (i < lines.size() && lines.get(i).start() <= max) {
                if (lines.get(i).end() > highestEnd) {
                    highestEnd = lines.get(i).end();
                    resIndex = i;
                }
                i++;
            }
            if (resIndex != -1) {
                resultGroupLine.add(lines.get(resIndex));
                max = highestEnd;
            } else if (i < lines.size()) {
                resultGroupLine.add(lines.get(i));
                max = lines.get(i).end();
                i++;
            }
        }
    }

    @Override
    public Line getFullIntervalFromGroup() {
        return new Line(
                resultGroupLine.getFirst().start(),
                resultGroupLine.getLast().end());
    }

    @Override
    public Line verifyInput(String inputLine) {
        inputLine=inputLine.trim();
        Pattern pattern = Pattern.compile("^-?\\d+ -?\\d+$");

        if (!pattern.matcher(inputLine).matches()){
            throw new IllegalArgumentException("Invalid format, allowed only: startNumber endNumber ");

        } else {
            String[] coords = inputLine.split(" ");
            if (Integer.parseInt(coords[0]) > Integer.parseInt(coords[1])) {
                throw new IllegalArgumentException("Invalid input: start cannot be greater than end");
            }

            return new Line(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
        }
    }

    @Override
    public void saveLine(Line line) {
        lineCollector.add(line);
    }

    @Override
    public void showResult() {
        System.out.printf("Complete resulting interval (%d,%d)%n",
                getFullIntervalFromGroup().start(),
                getFullIntervalFromGroup().end());
        System.out.println("Resulting lines:");
        resultGroupLine.forEach(System.out::println);
    }

    @Override
    public void clearAllData() {
        resultGroupLine.clear();
        lineCollector.clearData();
    }

    @Override
    public boolean isInputEmpty() {
        return lineCollector.getSize() < 1;
    }

    public List<Line> getResultGroupLine() {
        return resultGroupLine;
    }
}
