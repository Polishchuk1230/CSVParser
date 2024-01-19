package org.example;

import java.util.Scanner;
import org.example.service.ExceptionHandler;
import org.example.service.OutputHandler;
import org.example.service.impl.ExceptionHandlerImpl;
import org.example.service.impl.InputHandlerImpl;
import org.example.service.impl.OutputHandlerImpl;
import org.example.service.impl.UploadServiceImpl;
import org.example.validator.InputValidator;

public class App {

    public static final String APP_NAME = "myparser";
    public static final String EXIT_COMMAND = "exit";

    public static void main(String[] args) {
        OutputHandler output = new OutputHandlerImpl();
        ExceptionHandler exceptionHandler = new ExceptionHandlerImpl();
        InputHandlerImpl inputHandler =
            new InputHandlerImpl(exceptionHandler, new InputValidator(), new UploadServiceImpl());

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                String input = sc.nextLine();
                if (input.contains(EXIT_COMMAND)) {
                    break;
                }

                output.sendResponse(
                    inputHandler.processInput(input));
            }
        }
    }
}
