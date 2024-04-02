package com.fof;

public class PokemonInfo {
    private String name;
    private String image;
    private int pokedexNumber;

    public PokemonInfo(String name, String image, int pokedexNumber) {
        this.name = name;
        this.image = image;
        this.pokedexNumber = pokedexNumber;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPokedexNumber() {
        return pokedexNumber;
    }

    public String toString() {
        return "PokemonInfo{" + "name='" + name + '\'' + ", image='" + image + '\'' + ", id="
                + pokedexNumber + "\'}";
    }
}
