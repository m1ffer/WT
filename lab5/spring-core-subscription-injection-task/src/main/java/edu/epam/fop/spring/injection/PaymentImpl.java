package edu.epam.fop.spring.injection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentImpl implements Payment{
    private long amount;
    private String type;
}
