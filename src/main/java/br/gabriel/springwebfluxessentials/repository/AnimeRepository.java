package br.gabriel.springwebfluxessentials.repository;

import br.gabriel.springwebfluxessentials.domain.Anime;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AnimeRepository extends ReactiveCrudRepository<Anime, Integer> {
    Mono<Anime> findById(Integer id);
}
