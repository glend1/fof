package com.fof;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaHandler implements RequestHandler<Object, Object> {

    @Override
    public Object handleRequest(Object arg0, Context arg1) {
        // Your Discord bot initialization and logic here
        App.buildBot(); // Start the bot
        return null; // Adjust the return type as needed
    }
}
