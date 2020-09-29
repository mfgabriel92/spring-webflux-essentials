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
            .switchIfEmpty(notFound(id));
    }
    
    public Mono<Anime> save(Anime anime) {
        return repository.save(anime);
    }
    
    public Mono<Void> save(Integer id, Anime anime) {
        return findById(id)
            .map(found -> anime.withId(id))
            .flatMap(repository::save)
            .then();
    }
    
    private <T> Mono<T> notFound(Integer id) {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Anime ID %s not found", id)));
    }
}
