package br.gabriel.springwebfluxessentials.service;

import br.gabriel.springwebfluxessentials.repository.AnimeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {
    @InjectMocks
    private AnimeService service;
    
    @Mock
    private AnimeRepository repository;
    
    @BeforeAll
    public static void setUpBlockHound() {
        BlockHound.install();
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
}
