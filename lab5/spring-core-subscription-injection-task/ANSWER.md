# Spring Core Subscription Injection Task

Реализовал три типа Spring Bean инъекций в рамках подписочного сервиса.

Создал интерфейс [Account.java](src/main/java/edu/epam/fop/spring/injection/Account.java) и его реализацию [AccountImpl.java](src/main/java/edu/epam/fop/spring/injection/AccountImpl.java) с полем `name`, пометил `@Component`.

Создал интерфейс [Payment.java](src/main/java/edu/epam/fop/spring/injection/Payment.java) и его реализацию [PaymentImpl.java](src/main/java/edu/epam/fop/spring/injection/PaymentImpl.java) с полями `amount` и `type`, пометил `@Component`.

Создал интерфейс [Period.java](src/main/java/edu/epam/fop/spring/injection/Period.java) и его реализацию [PeriodImpl.java](src/main/java/edu/epam/fop/spring/injection/PeriodImpl.java) с полями `Duration` и `LocalDate`, пометил `@Component`.

Создал интерфейс [Subscription.java](src/main/java/edu/epam/fop/spring/injection/Subscription.java) и его реализацию [SubscriptionImpl.java](src/main/java/edu/epam/fop/spring/injection/SubscriptionImpl.java), в которой применил три разных типа инъекции:
- **Constructor injection** (`@Autowired` на конструкторе) — для внедрения `Account`.
- **Field injection** (`@Autowired` на поле) — для внедрения `Payment`.
- **Setter injection** (`@Autowired` на сеттере) — для внедрения `Period`.

Создал конфигурационный класс [SubConfig.java](src/main/java/edu/epam/fop/spring/injection/SubConfig.java) с `@Configuration` и `@ComponentScan("edu.epam.fop.spring.injection")`.

В файле [Main.java](src/main/java/edu/epam/fop/spring/injection/Main.java) создал Spring Context через `AnnotationConfigApplicationContext(SubConfig.class)`, получил бин `Subscription` и вывел все его поля (`Payment`, `Period`, `User`) в `System.out`.
