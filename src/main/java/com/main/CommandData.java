package com.main;

import com.main.app.Command;
import com.main.app.Console;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.NumberFormat;
import java.text.ParseException;

public class CommandData {

    private static final int DESCRIPTION_OFFSET = 16;

    private Method method;
    private int paramsCount;

    public CommandData(Method method) {
        this.method = method;
        this.paramsCount = method.getParameterCount();
    }

    public boolean execute(Console console, String... args) {
        if (this.method.getParameterCount() < args.length) {
            try {
                this.method.invoke(console, (Object[]) this.getArguments(args));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public String getName() {
        return this.method.getName() + new String(new char[DESCRIPTION_OFFSET - this.method.getName().length()]);
    }

    public int getParamsCount() {
        return paramsCount;
    }

    public String getArgumentsDescription() {
        return "expecting '" + this.method.getName() + " " + this.method.getAnnotation(Command.class).arguments() + "'";
    }

    public String getDescription() {
        return this.getName() + " - " + this.method.getAnnotation(Command.class).arguments() + " " + this.method.getAnnotation(Command.class).description();
    }

    private String[] getArguments(String... args) {
        String[] arguments = new String[this.paramsCount];
        for (int i = 0; i < this.paramsCount; i++)
            if ((i + 1) < args.length)
                arguments[i] = args[i + 1];
        return arguments;
    }

}
