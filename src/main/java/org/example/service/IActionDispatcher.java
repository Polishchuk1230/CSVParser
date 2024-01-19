package org.example.service;

import java.util.List;

public interface IActionDispatcher {

  List<String> dispatchAction(String action, String... parameter);
}
