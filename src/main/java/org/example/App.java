package org.example;

import java.util.Scanner;
import org.example.service.IExceptionHandler;
import org.example.service.IInputHandler;
import org.example.service.IOutputHandler;
import org.example.service.impl.ExceptionHandler;
import org.example.service.impl.InputHandler;
import org.example.service.impl.OutputHandler;
import org.example.service.impl.UploadService;
import org.example.validator.InputValidator;

public class App {

    public static final String APP_NAME = "myparser";
    public static final String EXIT_COMMAND = "exit";

    public static void main(String[] args) {
        IOutputHandler output = new OutputHandler();
        IExceptionHandler IExceptionHandler = new ExceptionHandler();
        IInputHandler inputHandler =
            new InputHandler(IExceptionHandler, new InputValidator(), new UploadService());

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
