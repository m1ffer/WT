package edu.epam.fop.spring.injection;

import lombok.*;
import org.springframework.stereotype.Component;

@Component

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountImpl implements Account{
    private String name;
}
