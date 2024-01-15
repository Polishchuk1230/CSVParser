package org.example;

import java.util.List;
import java.util.Scanner;
import org.example.service.CommandService;

public class App {
    private static final String APP_NAME = "myparser";
    private static final String PATH_TO_CSV = "example_file.csv";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine();
            if (!input.startsWith("myparser")) {
                continue;
            }
            if (input.contains("exit")) {
                break;
            }

            String[] split = input.split(" ");
            if (split.length != 4) {
                System.out.println("Wrong format.\nRight format: myparser <file_name.csv> find_max|count <column_name>");
                continue;
            }

            String fileName = split[1];
            String action = split[2];
            String columnName = split[3];

            CommandService commandService = new CommandService(fileName);
            List<String> processedData = commandService.processCommand(action, columnName);

            processedData.forEach(System.out::println);
        }
    }
}
