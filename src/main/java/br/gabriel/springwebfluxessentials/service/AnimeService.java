package br.gabriel.springwebfluxessentials.service;

import br.gabriel.springwebfluxessentials.domain.Anime;
import br.gabriel.springwebfluxessentials.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository repository;
    
    public Flux<Anime> findAll() {
        return repository.findAll();
    }
    
    public Mono<Anime> findById(Integer id) {
        return repository
            .findById(id)
            .switchIfEmpty(notFound());
    }
    
    private <T> Mono<T> notFound() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
    }
}
