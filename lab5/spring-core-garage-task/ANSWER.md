# Spring Core Garage Task

Создал класс [Car.java](src/main/java/com/epam/rd/autotasks/Car.java) с полями `model`, `year`, `owner`. Использовал Lombok-аннотации `@AllArgsConstructor`, `@Getter`, `@Setter`. Зависимость `Owner` передаётся через конструктор (constructor injection).

Создал класс [Owner.java](src/main/java/com/epam/rd/autotasks/Owner.java) с полями `name` и `taxNumber`. Также с Lombok-аннотациями `@AllArgsConstructor`, `@Getter`, `@Setter`.

Создал конфигурационный класс [OwnerConfig.java](src/main/java/com/epam/rd/autotasks/config/OwnerConfig.java) с аннотацией `@Configuration`. Определил `@Bean` метод `owner()`, создающий владельца с именем "John Doe" и налоговым номером "19671223-0000".

Создал конфигурационный класс [CarConfig.java](src/main/java/com/epam/rd/autotasks/config/CarConfig.java) с аннотациями `@Configuration` и `@Import(OwnerConfig.class)`. Определил `@Bean` метод `car(Owner owner)`, создающий автомобиль модели "Tesla Model X", года "2022", с внедрённым бином `Owner` через параметр метода (constructor injection).
