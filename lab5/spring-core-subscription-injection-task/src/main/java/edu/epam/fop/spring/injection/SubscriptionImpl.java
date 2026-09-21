package edu.epam.fop.spring.injection;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

@Getter
public class SubscriptionImpl implements Subscription{
    @Autowired
    public SubscriptionImpl(Account user){
        this.user = user;
    }

    private final Account user;
    @Autowired
    private Payment payment;
    private Period period;

    @Autowired
    @Override
    public void setPeriod(Period period) {
        this.period = period;
    }
}
