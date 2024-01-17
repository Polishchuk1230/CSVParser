package org.example.service;

import java.util.List;

public interface ActionDispatcher {

  List<String> dispatchAction(String action, String parameter);
}
