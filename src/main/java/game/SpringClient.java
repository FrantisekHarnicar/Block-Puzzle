package game;

import game.blockPuzzle.console.Console;
import game.blockPuzzle.core.Field;
import game.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration

public class SpringClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class, args);
    }

    @Bean
    public CommandLineRunner runner(Console ui) {
        return args -> ui.play();
    }

    @Bean
    public Console console(Field field) {
        return new Console(field);
    }

    @Bean
    public Field field() {
        return new Field(1);
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }
}
