# Spring Core Song Task

Создал класс [Song.java](src/main/java/com/epam/rd/autotasks/Song.java) с полями `title`, `artist`, `year`. Пометил его аннотацией `@Component` и `@PropertySource("/application.properties")`. В конструкторе через аннотацию `@Value` внедрил значения свойств из файла конфигурации (`${Title}`, `${Artist}`, `${Year}`). Геттеры и сеттеры сгенерированы через Lombok (`@Getter`, `@Setter`).

Заполнил файл [application.properties](src/main/resources/application.properties) значениями: Title=Only the young, Artist=Taylor Swift, Year=2020.

Создал конфигурационный класс [SongConfig.java](src/main/java/com/epam/rd/autotasks/config/SongConfig.java) с аннотациями `@Configuration` и `@ComponentScan("com.epam.rd.autotasks")` для автоматического обнаружения компонента `Song`.
