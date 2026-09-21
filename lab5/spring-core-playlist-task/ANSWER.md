# Spring Core Playlist Task

Создал класс [Singer.java](src/main/java/com/epam/rd/autotasks/Singer.java) с полем `name`. Использовал Lombok-аннотации `@AllArgsConstructor`, `@Getter`, `@Setter`.

Создал класс [Song.java](src/main/java/com/epam/rd/autotasks/Song.java) с полем `title`. Использовал Lombok-аннотации `@AllArgsConstructor`, `@Getter`, `@Setter`.

Создал вспомогательный класс [Generator.java](src/main/java/com/epam/rd/autotasks/Generator.java), пометил `@Component`. Он генерирует случайные строки длиной от 5 до 15 символов с помощью `SecureRandom` — используется для создания уникальных названий песен.

Создал конфигурационный класс [AppConfig.java](src/main/java/com/epam/rd/autotasks/config/AppConfig.java) с `@Configuration` и `@ComponentScan("com.epam.rd.autotasks")`. Внедрил `Generator` через constructor injection (Lombok `@RequiredArgsConstructor`). Определил два `@Bean` метода:
- `singer()` со скоупом `@Scope(SCOPE_SINGLETON)` — создаёт `Singer` с именем "Elton John".
- `song()` со скоупом `@Scope(SCOPE_PROTOTYPE)` — создаёт `Song` с рандомным названием через `generator.generateString()`. Каждый вызов возвращает новый экземпляр с уникальным названием.
