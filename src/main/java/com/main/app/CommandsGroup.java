package com.main.app;

import com.main.CommandData;

import java.util.HashMap;
import java.util.Map;

public class CommandsGroup {

    private Map<String, CommandData> commandsMap;

    public CommandsGroup() {
        this.commandsMap = new HashMap<>();
    }

    public void addCommand(String name, CommandData commandData) {
        this.commandsMap.put(name, commandData);
    }

    public CommandData getCommand(String key) {
        return commandsMap.get(key);
    }

    public boolean containsKey(String key) {
        return this.commandsMap.containsKey(key);
    }

    public Map<String, CommandData> getCommandsMap() {
        return commandsMap;
    }
}
