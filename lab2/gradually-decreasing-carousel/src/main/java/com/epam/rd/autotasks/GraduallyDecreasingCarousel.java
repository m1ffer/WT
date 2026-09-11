package com.epam.rd.autotasks;

public class GraduallyDecreasingCarousel extends DecrementingCarousel{
    private int i = 0, r = 1, l;
    public GraduallyDecreasingCarousel(final int capacity) {
        super(capacity);
    }

    @Override
    protected int next(){
        if (q.isEmpty())
            return -1;
        if (i == l){
            i = 0;
            l = q.size();
            r++;
        }
        int tmp = q.poll();
        if (tmp > r)
            q.add(tmp - r);
        i++;
        return tmp;
    }

    @Override
    protected CarouselRun run(){
        var res = super.run();
        if (res == null)
            return null;
        l = q.size();
        return res;
    }
}
