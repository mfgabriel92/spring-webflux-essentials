package br.gabriel.springwebfluxessentials.util;

import br.gabriel.springwebfluxessentials.domain.Anime;

public class AnimeCreator {
    public static Anime createAnime() {
        return Anime
            .builder()
            .name("Nanatsu no Taizai")
            .build();
    }
}
