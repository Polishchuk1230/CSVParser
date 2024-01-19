package org.example;

import org.example.service.IInputHandler;
import org.example.service.IOutputHandler;
import org.example.service.impl.InputHandler;
import org.example.service.impl.OutputHandler;
import org.example.service.impl.UploadService;
import org.example.validator.InputValidator;

public class App {
    public static void main(String[] args) {
        IOutputHandler output = new OutputHandler();
        IInputHandler inputHandler = new InputHandler(new InputValidator(), new UploadService());

        output.sendResponse(
            inputHandler.processInput(args));
    }
}
