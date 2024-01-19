package org.example;

import org.example.service.IExceptionHandler;
import org.example.service.IInputHandler;
import org.example.service.IOutputHandler;
import org.example.service.impl.ExceptionHandler;
import org.example.service.impl.InputHandler;
import org.example.service.impl.OutputHandler;
import org.example.service.impl.UploadService;
import org.example.validator.InputValidator;

public class App {
    public static void main(String[] args) {
        IOutputHandler output = new OutputHandler();
        IExceptionHandler IExceptionHandler = new ExceptionHandler();
        IInputHandler inputHandler =
            new InputHandler(IExceptionHandler, new InputValidator(), new UploadService());

        output.sendResponse(
            inputHandler.processInput(args));
    }
}
