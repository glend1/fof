package com.fof;

// import java.net.URI;
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
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
            event.getChannel().sendMessage("<:fof:1221889413303369900>").queue();
        if (Math.random() <= .0001)
            event.getChannel().sendMessage("Piss").queue();
        if (Math.random() <= .0001)
            event.getChannel().sendMessage("Cum").queue();
        if (Math.random() <= .00001)
            event.getChannel().sendMessage("PissCum").queue();
        // if (event.getMessage().getContentRaw().toLowerCase().startsWith("!smashorpass")) {
        // String pokemonName = getRandomPokemonName();
        // String result = getRandomStringFromArray(new String[] {"Smash", "Pass"});
        // event.getChannel().sendMessage(pokemonName + " " + result).queue();
        // }
        if (event.getMessage().getContentRaw().toLowerCase().startsWith("!commandlist")) {
            String commandList =
                    "Command List:\n" + "!commandlist - Display the list of available commands\n";
            // + "!smashorpass - Get a random Pokemon name and whether to smash or pass\n";
            event.getChannel().sendMessage(commandList).queue();
        }
    }

    // private String getRandomStringFromArray(String[] array) {
    // int randomIndex = (int) (Math.random() * array.length);
    // return array[randomIndex];
    // }

    // private String getRandomPokemonName() {
    // try {
    // // Make a request to the Pokemon API
    // String apiUrl = "https://pokeapi.co/api/v2/pokemon/";
    // HttpClient client = HttpClient.newHttpClient();
    // HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();
    // HttpResponse<String> response =
    // client.send(request, HttpResponse.BodyHandlers.ofString());

    // // Parse the response to get a random Pokemon name
    // ObjectMapper mapper = new ObjectMapper();
    // JsonNode root = mapper.readTree(response.body());
    // int count = root.get("count").asInt();
    // int randomIndex = (int) (Math.random() * count);
    // String pokemonUrl = apiUrl + randomIndex;
    // request = HttpRequest.newBuilder().uri(URI.create(pokemonUrl)).build();
    // response = client.send(request, HttpResponse.BodyHandlers.ofString());
    // JsonNode pokemonData = mapper.readTree(response.body());
    // String pokemonName = pokemonData.get("name").asText();

    // return pokemonName;
    // } catch (Exception e) {
    // e.printStackTrace();
    // return null;
    // }
    // }

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
