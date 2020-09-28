package br.gabriel.springwebfluxessentials.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Table("animes")
public class Anime {
    @Id
    private Integer id;
    
    @NotNull(message = "The name must be filled")
    private String name;
}
