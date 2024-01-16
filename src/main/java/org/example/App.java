package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.example.model.Action;
import org.example.service.CommandService;
import org.example.service.CommandServiceImpl;
import org.example.service.CsvServiceImpl;
import org.example.service.OutputService;
import org.example.service.OutputServiceImpl;
import org.example.service.UploadServiceImpl;

public class App {
    private static final String APP_NAME = "myparser";
    private static final String USER_INPUT_VALUE_SEPARATOR = " "; // space
    private static final int COMMAND_PARTS_COUNT = 4;
    private static final int COMMAND_PART_FILE_NAME = 1;
    private static final int COMMAND_PART_ACTION = 2;
    private static final int COMMAND_PART_PARAMETER = 3;


    public static void main(String[] args) {
        OutputService output = new OutputServiceImpl();
        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine();
            if (input.contains("exit")) {
                break;
            }
            if (!input.startsWith(APP_NAME)) {
                continue;
            }

            String[] split = input.split(USER_INPUT_VALUE_SEPARATOR);
            if (split.length != COMMAND_PARTS_COUNT
                || Arrays.stream(Action.values()).noneMatch(
                    action -> action.toString().equalsIgnoreCase(split[COMMAND_PART_ACTION])))
            {
                output.sendResponse(
                    List.of("Wrong format.\nRight format: myparser <file_name.csv> find_max|count <column_name>"));
                continue;
            }

            String fileName = split[COMMAND_PART_FILE_NAME];
            String action = split[COMMAND_PART_ACTION];
            String parameter = split[COMMAND_PART_PARAMETER];

            CommandService commandService = new CommandServiceImpl(
                new CsvServiceImpl(
                    new UploadServiceImpl().uploadFile(fileName)));
            List<String> processedData = commandService.processCommand(action, parameter);

            output.sendResponse(processedData);
        }
    }
}
