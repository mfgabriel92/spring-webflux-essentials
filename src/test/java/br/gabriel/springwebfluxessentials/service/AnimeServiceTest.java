package br.gabriel.springwebfluxessentials.service;

import br.gabriel.springwebfluxessentials.domain.Anime;
import br.gabriel.springwebfluxessentials.repository.AnimeRepository;
import br.gabriel.springwebfluxessentials.util.AnimeCreator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {
    @InjectMocks
    private AnimeService service;
    
    @Mock
    private AnimeRepository repository;
    
    private final Anime anime = AnimeCreator.createAnime();
    
    @BeforeAll
    public static void setUpBlockHound() {
        BlockHound.install();
    }
    
    @BeforeEach
    public void setUp() {
        BDDMockito
            .when(repository.findAll())
            .thenReturn(Flux.just(anime));
    
        BDDMockito
            .when(repository.findById(ArgumentMatchers.anyInt()))
            .thenReturn(Mono.just(anime));
    }
    
    @Test
    public void blockHoundShouldBeWorking() {
        try {
            FutureTask<?> task = new FutureTask<>(() -> {
                Thread.sleep(0);
                return "";
            });
    
            Schedulers.parallel().schedule(task);
            task.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof BlockingOperationError);
        }
    }
    
    @Test
    @DisplayName("Should find all anime and return a Flux of Anime")
    public void shouldReturnFluxOfAnime_WhenSuccessful() {
        StepVerifier
            .create(service.findAll())
            .expectSubscription()
            .expectNext(anime)
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should find by ID and return a Mono of an Anime")
    public void shouldReturnMonoOfAnAnime_WhenSuccessful() {
        StepVerifier
            .create(service.findById(1))
            .expectSubscription()
            .expectNext(anime)
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should find for a non-existing ID and return a Mono error")
    public void shouldReturnMonoError_WhenNotSuccessful() {
        BDDMockito
            .when(repository.findById(ArgumentMatchers.anyInt()))
            .thenReturn(Mono.empty());
        
        StepVerifier
            .create(service.findById(10))
            .expectSubscription()
            .verifyError(ResponseStatusException.class);
    }
}
