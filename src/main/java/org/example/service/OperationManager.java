package org.example.service;

import org.example.model.Line;

public interface OperationManager {

    void findLinesForInterval();

    Line getFullIntervalFromGroup();

    Line verifyInput(String inputLine);

    void saveLine(Line line);

    void showResult();

    void clearAllData();

    boolean isInputEmpty();

}
