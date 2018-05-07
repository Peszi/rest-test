package com.main;

import com.main.app.Command;
import com.main.app.Console;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommandData {

    private Method method;

    public CommandData(Method method) {
        this.method = method;
    }

    public void execute(Console console, Object... args) {
        try {
            this.method.invoke(console, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return this.method.getAnnotation(Command.class).name();
    }

    public int getArgsLen() {
        return this.method.getParameterCount();
    }

    public String getArgsLenMessage() {
        final int paramsCount = this.method.getParameterCount();
        if (paramsCount > 0)
            return "(" + this.getArgsLen() + " args) ";
        else if (paramsCount == 1)
            return "(one arg) ";
        return "";
    }

    public String getDescription() {
        return this.getName() + " " + this.getArgsLenMessage() + "- " + this.method.getAnnotation(Command.class).description();
    }

}
