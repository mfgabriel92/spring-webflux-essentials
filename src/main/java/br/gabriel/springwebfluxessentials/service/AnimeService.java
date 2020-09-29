package br.gabriel.springwebfluxessentials.service;

import br.gabriel.springwebfluxessentials.domain.Anime;
import br.gabriel.springwebfluxessentials.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository repository;
    
    public Flux<Anime> findAll() {
        return repository.findAll();
    }
}
