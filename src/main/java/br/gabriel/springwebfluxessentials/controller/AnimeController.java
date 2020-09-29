package br.gabriel.springwebfluxessentials.controller;

import br.gabriel.springwebfluxessentials.domain.Anime;
import br.gabriel.springwebfluxessentials.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/animes")
@RequiredArgsConstructor
public class AnimeController {
    private final AnimeService service;
    
    @GetMapping
    public Flux<Anime> findAll() {
        return service.findAll();
    }
    
    @GetMapping("{id}")
    public Mono<Anime> findById(@PathVariable Integer id) {
        return service.findById(id);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Anime> save(@Valid @RequestBody Anime anime) {
        return service.save(anime);
    }
    
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> save(@PathVariable Integer id, @Valid @RequestBody Anime anime) {
        return service.save(id, anime);
    }
}
