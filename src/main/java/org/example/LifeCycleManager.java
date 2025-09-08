package org.example;

import org.example.model.Line;
import org.example.service.OperationManager;
import org.example.service.OperationManagerImpl;

import java.util.Scanner;

public class LifeCycleManager {

    private static final LifeCycleManager INSTANCE = new LifeCycleManager();
    private final Scanner scanner;
    private final OperationManager operationManager;

    private LifeCycleManager() {
        this.scanner = new Scanner(System.in);
        this.operationManager = new OperationManagerImpl();

    }

    public static LifeCycleManager getInstance() {
        return INSTANCE;
    }

    public void doTheLifeCycle() {
        showControls();
        while (true) {
            String input = scanner.nextLine();
            if (input.isBlank()) {
                if (operationManager.isInputEmpty()) {
                    System.out.println("Please enter a valid input");
                    continue;
                }
                operationManager.findLinesForInterval();
                operationManager.showResult();
                operationManager.clearAllData();
                System.out.println("Pres one more Enter\nFor new calculation start insert values: ");
                input = scanner.nextLine();
                if (input.isBlank()) {
                    break;
                }
            }
            try {
                Line lineForSave = operationManager.verifyInput(input);
                operationManager.saveLine(lineForSave);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Please re-enter this value.");
            }
        }
        scanner.close();
    }

    private void showControls(){
        System.out.print("""
                Insert values, format: <start_Line> <end_Line>
                For inserting another value press Enter. For end of entering press Enter twice
                Set values:
                """);
    }

}
