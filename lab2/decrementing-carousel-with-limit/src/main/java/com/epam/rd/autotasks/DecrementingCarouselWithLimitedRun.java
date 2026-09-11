package com.epam.rd.autotasks;

public class DecrementingCarouselWithLimitedRun extends DecrementingCarousel{
    private final int limit;
    private int i = 0;
    public DecrementingCarouselWithLimitedRun(final int capacity, final int actionLimit) {
        super(capacity);
        limit = actionLimit;
    }

    @Override
    protected int next(){
        var res = super.next();
        if (res != -1){
            i++;
            if (i == limit)
                q.clear();
        }
        return res;
    }
}
