package com.epam.rd.autotasks.sprintplanning;

import com.epam.rd.autotasks.sprintplanning.tickets.Bug;
import com.epam.rd.autotasks.sprintplanning.tickets.Ticket;
import com.epam.rd.autotasks.sprintplanning.tickets.UserStory;
import lombok.Getter;

public class Sprint {
    private final int capacity, ticketsLimit;
    private final Ticket[] tickets;

    public Sprint(int capacity, int ticketsLimit){
        this.capacity = capacity;
        this.ticketsLimit = ticketsLimit;
        tickets = new Ticket[ticketsLimit];
    }

    @Getter
    private int totalEstimate = 0;
    private int currI = 0;

    private boolean isInTickets(Ticket ticket){
        for (Ticket i : tickets)
            if (i == ticket)
                return true;
        return false;
    }

    private boolean isNotAcceptable(Ticket ticket){
        return ticket == null ||
                ticket.isCompleted() ||
                ticket.getEstimate() + totalEstimate > capacity ||
                currI == ticketsLimit;
    }

    private void addTicket(Ticket ticket){
        tickets[currI++] = ticket;
        totalEstimate += ticket.getEstimate();
    }

    public boolean addUserStory(UserStory userStory) {
        if (isNotAcceptable(userStory))
            return false;
        for (UserStory i : userStory.getDependencies())
            if (!i.isCompleted() && !isInTickets(i))
                return false;
        addTicket(userStory);
        return true;
    }

    public boolean addBug(Bug bugReport) {
        if (isNotAcceptable(bugReport))
            return false;
        addTicket(bugReport);
        return true;
    }

    public Ticket[] getTickets() {
        Ticket[] res = new Ticket[currI];
        for (int i = 0; i < currI; i++)
            res[i] = tickets[i].copy();
        return res;
    }
}
