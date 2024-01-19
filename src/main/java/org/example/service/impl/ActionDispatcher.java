package org.example.service.impl;

import java.io.BufferedReader;
import java.util.List;
import org.example.dto.FileContentDto;
import org.example.model.Action;
import org.example.service.IActionDispatcher;
import org.example.service.action.commant.AActionCommand;
import org.example.service.action.commant.ActionCommandCount;
import org.example.service.action.commant.ActionCommandFindMax;

public class ActionDispatcher implements IActionDispatcher {

  private final FileContentDto<BufferedReader> fileContentDto;

  public ActionDispatcher(FileContentDto<BufferedReader> fileContentDto) {
    this.fileContentDto = fileContentDto;
  }

  @Override
  public List<String> dispatchAction(String action, String... parameters) {
    AActionCommand command = switch (Action.valueOf(action.toUpperCase())) {
      case COUNT -> new ActionCommandCount(fileContentDto);
      case FIND_MAX -> new ActionCommandFindMax(fileContentDto);
    };
    try (command) {
      return command.execute(parameters);
    }
  }
}