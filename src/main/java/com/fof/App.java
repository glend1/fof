package com.fof;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class App extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        buildBot();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return; // Ignore messages from other bots
        if (event.getChannel().asTextChannel().getName().equals("test-env")) {
            if (event.getMessage().getContentRaw().equals("!hello"))
                event.getChannel().sendMessage("Hello, " + event.getAuthor().getAsMention() + "!")
                        .queue();
            if (event.getMessage().getContentRaw().equals("!bye"))
                event.getChannel().sendMessage("Bye, " + event.getAuthor().getAsMention() + "!")
                        .queue();
        }
    }

    public static void buildBot() {
        // Build the bot
        String discordBotToken = System.getenv("DISCORD_BOT_TOKEN");
        if (discordBotToken == null) {
            System.err.println("DISCORD_BOT_TOKEN environment variable not set");
            System.exit(1);
        }
        JDABuilder builder = JDABuilder.createDefault(discordBotToken);
        // Enable the necessary Gateway Intents
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        builder.addEventListeners(new App());
        builder.build();
    }
}
