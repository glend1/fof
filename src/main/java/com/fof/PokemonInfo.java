package com.fof;

public class PokemonInfo {
    private String name;
    private String image;

    public PokemonInfo(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    @Override 
    public String toString() { 
        return "PokemonInfo{" + "name='" + name + "', image='" + image + "'}"; 
    }
}
