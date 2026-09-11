package com.epam.rd.autotasks;

import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;
import java.util.Queue;

@RequiredArgsConstructor
public class TaskCarousel {
    private final int capacity;
    private final Queue<Task> q = new ArrayDeque<>();

    private boolean isNotAcceptable(Task task){
        return task == null ||
                task.isFinished() ||
                isFull();
    }

    public boolean addTask(Task task) {
        if (isNotAcceptable(task))
            return false;
        return q.add(task);
    }

    public boolean execute() {
        if (q.isEmpty())
            return false;
        Task first = q.poll();
        first.execute();
        if (!first.isFinished())
            q.add(first);
        return true;
    }

    public boolean isFull() {
        return q.size() == capacity;
    }

    public boolean isEmpty() {
        return q.isEmpty();
    }

}
