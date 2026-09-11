package com.epam.rd.autotasks;

import java.util.ArrayDeque;
import java.util.Queue;

public class DecrementingCarousel {
    private final int capacity;
    protected final Queue<Integer> q;

    private CarouselRun runner= null;
    public DecrementingCarousel(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        this.capacity = capacity;
        q = new ArrayDeque<>(capacity);
    }
    public boolean addElement(int element){
        if (runner != null ||
                element <= 0 ||
                q.size() == capacity)
            return false;
        return q.add(element);
    }

    public CarouselRun run(){
        if (runner == null){
            runner = new CarouselRun(this);
            return runner;
        }
        return null;
    }

    protected int next(){
        if (q.isEmpty())
            return -1;
        int tmp = q.poll();
        if (tmp > 1)
            q.add(tmp - 1);
        return tmp;
    }

    boolean isEmpty(){
        return q.isEmpty();
    }
}
