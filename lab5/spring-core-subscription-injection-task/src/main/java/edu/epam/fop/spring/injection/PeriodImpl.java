package edu.epam.fop.spring.injection;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;

@Component

@AllArgsConstructor
@NoArgsConstructor
public class PeriodImpl implements Period{
    private Duration pd;
    private LocalDate ed;
    @Override
    public Duration paymentPeriod() {
        return pd;
    }

    @Override
    public LocalDate endDate() {
        return ed;
    }
}
