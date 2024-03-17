package com.fof;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaHandler implements RequestHandler<Object, Object> {

    @Override
    public Object handleRequest(Object input, Context context) {
        LambdaLogger logger = context.getLogger();
        // Log messages at different levels
        logger.log("This is an informational message");
        logger.log("This is a warning message");
        logger.log("This is a severe message");

        // Example of logging variable values
        String name = "John";
        int age = 30;
        logger.log("Name: " + name + ", Age: " + age);

        // Your Discord bot initialization and logic here
        App.buildBot(); // Start the bot

        return null; // Adjust the return type as needed
    }
}
