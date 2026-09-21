# Spring Core Task Task

Создал класс [Employee.java](src/main/java/com/epam/rd/autotasks/Employee.java) с полями `name` и `position`. Использовал Lombok-аннотации `@AllArgsConstructor`, `@Getter`, `@Setter`.

Создал класс [Task.java](src/main/java/com/epam/rd/autotasks/Task.java) с полями `description`, `assignee` (тип `Employee`) и `reviewer` (тип `Employee`). Использовал Lombok-аннотации `@AllArgsConstructor`, `@Getter`, `@Setter`.

Создал конфигурационный класс [AppConfig.java](src/main/java/com/epam/rd/autotasks/config/AppConfig.java) с аннотацией `@Configuration`. Определил три `@Bean` метода:
- `assignee()` — создаёт `Employee` с именем "John Doe" и позицией "Junior Software Engineer".
- `reviewer()` — создаёт `Employee` с именем "Emily Brown" и позицией "Senior Software Engineer".
- `task(@Qualifier("assignee") Employee assignee, @Qualifier("reviewer") Employee reviewer)` — создаёт `Task` с описанием "New feature". Через `@Qualifier` разрешил неоднозначность между двумя бинами типа `Employee`, указав какой из них assignee, а какой reviewer.
