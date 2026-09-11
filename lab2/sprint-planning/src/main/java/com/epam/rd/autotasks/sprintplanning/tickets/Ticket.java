package com.epam.rd.autotasks.sprintplanning.tickets;

public class Ticket {
    private final int id;
    private final String name;
    private final int estimate;
    private boolean isCompleted = false;

    public Ticket(int id, String name, int estimate) {
        this.id = id;
        this.name = name;
        this.estimate = estimate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getEstimate() {
        return estimate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void complete() {
        isCompleted = true;
    }

    public Ticket copy() {
        Ticket res = new Ticket(id, name, estimate);
        if (isCompleted()) {
            res.complete();
        }
        return res;
    }
}