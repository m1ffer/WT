# Spring Core Music Service Task

Создал класс [MusicService.java](src/main/java/com/epam/rd/autotasks/MusicService.java) с полем `List<Song> songs`. Пометил аннотацией `@Service`. В методе с `@PostConstruct` добавил 3 песни в список. В методе с `@PreDestroy` вывел сообщение "Shutting down" в консоль. Геттер сгенерирован через Lombok (`@Getter`).

Класс [Song.java](src/main/java/com/epam/rd/autotasks/Song.java) — POJO с полями `title`, `year`, `artist`. Использованы Lombok-аннотации `@AllArgsConstructor`, `@NoArgsConstructor`, `@Getter`, `@Setter`.

Создал конфигурационный класс [MusicServiceConfig.java](src/main/java/com/epam/rd/autotasks/config/MusicServiceConfig.java) с `@Configuration` и `@ComponentScan("com.epam.rd.autotasks")` для автоматического обнаружения `@Service` бина `MusicService`.
