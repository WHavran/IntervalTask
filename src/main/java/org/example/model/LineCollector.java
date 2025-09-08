package org.example.model;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.List;

public class LineCollector {

    private List<Line> lines;

    public LineCollector(List<Line> lines) {
        this.lines = lines;
    }

    public void add (Line line) {
        if (line == null) {
            throw new InvalidParameterException("Invalid parameter: Null not allowed");
        }
        lines.add(line);
    }

    public int getSize() {
        return lines.size();
    }

    public void sortData(){
        Collections.sort(lines);
    }

    public void clearData() {
        lines.clear();
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
}
