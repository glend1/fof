package com.fof;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.EmbedBuilder;
import ch.qos.logback.classic.Logger;

public class App extends ListenerAdapter {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(App.class);

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
        if (event.getMessage().getContentRaw().toLowerCase().contains("fof")
                || event.getMessage().getContentRaw().toLowerCase().contains("f'of"))
            event.getChannel().sendMessage("<:fof:1221889413303369900>").queue();
        if (event.getMessage().getContentRaw().toLowerCase().startsWith("!cointoss")) {
            String result = getRandomStringFromArray(new String[] {"Heads", "Tails"});
            event.getChannel().sendMessage(result).queue();
        }
        if (event.getMessage().getContentRaw().toLowerCase().startsWith("!roll")) {
            String[] args = event.getMessage().getContentRaw().split(" ");
            if (args.length != 2) {
                event.getChannel().sendMessage("Usage: !roll <dice>").queue();
                return;
            }
            try {
                String dice = args[1].toLowerCase();
                int result = 0;
                if (dice.equals("d4")) {
                    result = (int) (Math.random() * 4) + 1;
                } else if (dice.equals("d6")) {
                    result = (int) (Math.random() * 6) + 1;
                } else if (dice.equals("d8")) {
                    result = (int) (Math.random() * 8) + 1;
                } else if (dice.equals("d10")) {
                    result = (int) (Math.random() * 10) + 1;
                } else if (dice.equals("d12")) {
                    result = (int) (Math.random() * 12) + 1;
                } else if (dice.equals("d20")) {
                    result = (int) (Math.random() * 20) + 1;
                } else {
                    event.getChannel()
                            .sendMessage("Invalid dice. Available dice: d4, d6, d8, d10, d12, d20")
                            .queue();
                    return;
                }
                event.getChannel().sendMessage("You rolled a " + result + " on a " + dice).queue();
            } catch (NumberFormatException e) {
                event.getChannel().sendMessage("Invalid dice").queue();
            }
        }
        if (Math.random() <= .0001)
            event.getChannel().sendMessage("Piss").queue();
        if (Math.random() <= .0001)
            event.getChannel().sendMessage("Cum").queue();
        if (Math.random() <= .00001)
            event.getChannel().sendMessage("PissCum").queue();
        if (event.getMessage().getContentRaw().toLowerCase().startsWith("!smashorpass")) {
            PokemonInfo pokemon = getRandomPokemonName();
            String result = getRandomStringFromArray(new String[] {"Smash", "Pass"});
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(pokemon.getName() + " (#" + pokemon.getPokedexNumber() + ")");
            embedBuilder.setImage(pokemon.getImage());
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " would " + result
                    + " " + pokemon.getName() + "!").addEmbeds(embedBuilder.build()).queue();
        }
        if (event.getMessage().getContentRaw().toLowerCase().startsWith("!fight")) {
            String[] args = event.getMessage().getContentRaw().split(" ");
            if (args.length != 2) {
                event.getChannel().sendMessage("Usage: !fight <user>").queue();
                return;
            }
            boolean winner = Math.random() < 0.5;
            String message =
                    winner ? args[1] + " wins!" : event.getAuthor().getAsMention() + " wins!";
            event.getChannel().sendMessage(message).queue();
        }

        if (event.getMessage().getContentRaw().toLowerCase().startsWith("!commandlist")) {
            String commandList = "Command List:\n"
                    + "!commandlist - Display the list of available commands\n"
                    + "!smashorpass - Get a random Pokemon name and whether to smash or pass\n"
                    + "!fight <user> - Fight against another user\n" + "!cointoss - Flip a coin\n"
                    + "!roll <dice> - Roll a dice (available dice: d4, d6, d8, d10, d12, d20)\n";
            event.getChannel().sendMessage(commandList).queue();
        }
    }

    private String getRandomStringFromArray(String[] array) {
        int randomIndex = (int) (Math.random() * array.length);
        return array[randomIndex];
    }

    private PokemonInfo getRandomPokemonName() {
        try {
            // set up the HTTP client and request
            ObjectMapper mapper = new ObjectMapper();
            String pokemonUrl = "https://pokeapi.co/api/v2/pokemon/";
            String pokedexUrl = "https://pokeapi.co/api/v2/pokemon-species/";
            HttpClient client = HttpClient.newHttpClient();

            logger.info("Fetching Pokedex data from " + pokedexUrl);
            HttpRequest pokedexRequest =
                    HttpRequest.newBuilder().uri(URI.create(pokedexUrl)).build();
            HttpResponse<String> pokedexResponse =
                    client.send(pokedexRequest, HttpResponse.BodyHandlers.ofString());
            JsonNode pokedexData = mapper.readTree(pokedexResponse.body());

            int count = pokedexData.get("count").asInt();
            int randomPokemon = (int) (Math.random() * count);
            String imageUrl = pokemonUrl + randomPokemon;
            String dataUrl = pokedexUrl + randomPokemon;

            logger.info("Fetching Pokemon data from " + dataUrl);
            HttpRequest dataRequest = HttpRequest.newBuilder().uri(URI.create(dataUrl)).build();
            HttpResponse<String> dataResponse =
                    client.send(dataRequest, HttpResponse.BodyHandlers.ofString());
            JsonNode PokemonData = mapper.readTree(dataResponse.body());
            String pokemonName = PokemonData.get("name").asText();
            pokemonName = pokemonName.substring(0, 1).toUpperCase() + pokemonName.substring(1);
            int pokedexNumber = PokemonData.get("id").asInt();

            logger.info("Fetching Pokemon image from " + imageUrl);
            HttpRequest pokemonRequest = HttpRequest.newBuilder().uri(URI.create(imageUrl)).build();
            HttpResponse<String> pokemonResponse =
                    client.send(pokemonRequest, HttpResponse.BodyHandlers.ofString());
            JsonNode pokemonImage = mapper.readTree(pokemonResponse.body());
            String spriteUrl = pokemonImage.get("sprites").get("front_default").asText();

            PokemonInfo pokemon = new PokemonInfo(pokemonName, spriteUrl, pokedexNumber);
            logger.info(pokemon.toString());

            return pokemon;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
