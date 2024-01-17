package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.example.model.Action;
import org.example.service.impl.CommandServiceImpl;
import org.example.service.impl.CsvProcessorServiceImpl;
import org.example.service.ExceptionHandler;
import org.example.service.impl.ExceptionHandlerImpl;
import org.example.service.OutputService;
import org.example.service.impl.OutputServiceImpl;
import org.example.service.impl.UploadServiceImpl;

public class App {
    private static final String APP_NAME = "myparser";
    private static final String EXIT_COMMAND = "exit";
    private static final String USER_INPUT_VALUE_SEPARATOR = " "; // space
    private static final int COMMAND_PARTS_COUNT = 4;
    private static final int COMMAND_PART_FILE_NAME = 1;
    private static final int COMMAND_PART_ACTION = 2;
    private static final int COMMAND_PART_PARAMETER = 3;


    public static void main(String[] args) {
        OutputService output = new OutputServiceImpl();
        ExceptionHandler exceptionHandler = new ExceptionHandlerImpl();
        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine();
            if (!input.startsWith(APP_NAME)) {
                continue;
            }
            if (input.contains(EXIT_COMMAND)) {
                break;
            }

            String[] splitInput = input.split(USER_INPUT_VALUE_SEPARATOR);
            if (splitInput.length != COMMAND_PARTS_COUNT
                || Arrays.stream(Action.values()).noneMatch(
                    action -> action.toString().equalsIgnoreCase(splitInput[COMMAND_PART_ACTION])))
            {
                output.sendResponse(
                    List.of("Wrong format.\nRight format: myparser <file_name.csv> find_max|count <column_name>"));
                continue;
            }

            String fileName = splitInput[COMMAND_PART_FILE_NAME];
            String action = splitInput[COMMAND_PART_ACTION];
            String parameter = splitInput[COMMAND_PART_PARAMETER];

            List<String> processedData;
            try (CsvProcessorServiceImpl csvProcessorService = new CsvProcessorServiceImpl(
                    new UploadServiceImpl().uploadFile(fileName))) {
                CommandServiceImpl commandService = new CommandServiceImpl(csvProcessorService);
                processedData = commandService.processCommand(action, parameter);
            } catch(Exception e) {
                processedData = exceptionHandler.handleException(e);
            }
            output.sendResponse(processedData);
        }
    }
}
