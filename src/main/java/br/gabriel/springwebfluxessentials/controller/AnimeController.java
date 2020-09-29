package br.gabriel.springwebfluxessentials.controller;

import br.gabriel.springwebfluxessentials.domain.Anime;
import br.gabriel.springwebfluxessentials.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/animes")
@RequiredArgsConstructor
public class AnimeController {
    private final AnimeService service;
    
    @GetMapping
    public Flux<Anime> findAll() {
        return service.findAll();
    }
}
