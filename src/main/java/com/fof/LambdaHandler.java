package com.fof;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.logging.LogLevel;

public class LambdaHandler implements RequestHandler<Object, Object> {

    @Override
    public Object handleRequest(Object input, Context context) {
        LambdaLogger logger = context.getLogger();
        // Log messages at different levels
        logger.log("This is an informational message", LogLevel.INFO);
        logger.log("This is a warning message", LogLevel.WARN);
        logger.log("This is a severe message", LogLevel.ERROR);

        // Example of logging variable values
        String name = "John";
        int age = 30;
        logger.log("Name: " + name + ", Age: " + age, LogLevel.INFO);

        // Your Discord bot initialization and logic here
        App.buildBot(); // Start the bot

        return null; // Adjust the return type as needed
    }
}
