package com.epam.rd.autotasks.sprintplanning.tickets;

public class Bug extends Ticket {
    private final UserStory userStory;
    public static Bug createBug(int id, String name, int estimate, UserStory userStory) {
        return userStory == null || !userStory.isCompleted() ? null : new Bug(id, name, estimate, userStory);
    }

    private Bug(int id, String name, int estimate, UserStory userStory) {
        super(id, name, estimate);
        this.userStory = userStory;
    }

    @Override
    public String toString() {
        return String.format("[Bug %d] %s: %s",
                getId(), userStory.getName(), getName());
    }

    @Override
    public Bug copy(){
        Bug res = new Bug(getId(), getName(), getEstimate(), userStory.copy());
        if (isCompleted())
            res.complete();
        return res;
    }
}
