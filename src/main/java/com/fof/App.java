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
        if (event.getMessage().getContentRaw().toLowerCase().contains("good morning"))
            event.getChannel().sendMessage("Good morning " + event.getAuthor().getAsMention() + "!")
                    .queue();
        if (event.getMessage().getContentRaw().toLowerCase().contains("good afternoon"))
            event.getChannel()
                    .sendMessage("Good afternoon " + event.getAuthor().getAsMention() + "!")
                    .queue();
        if (event.getMessage().getContentRaw().toLowerCase().contains("fof"))
            event.getChannel().sendMessage(":fof:").queue();
        if (Math.random() <= .0001)
            event.getChannel().sendMessage("Piss").queue();
        if (Math.random() <= .0001)
            event.getChannel().sendMessage("Cum").queue();
        if (Math.random() <= .00001)
            event.getChannel().sendMessage("PissCum").queue();
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
