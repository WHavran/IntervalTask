package org.example.model;

public record Line(int start, int end) implements Comparable<Line> {

    @Override
    public String toString() {
        return "(%d, %d)".formatted(start, end);
    }

    @Override
    public int compareTo(Line o) {
        int cmp = Integer.compare(this.start, o.start);
        if (cmp == 0) {
            cmp = Integer.compare(o.end, this.end);
        }
        return cmp;
    }
}
