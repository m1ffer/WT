package com.epam.rd.autotasks.sprintplanning.tickets;


public class UserStory extends Ticket {
    private final UserStory[] dependencies;
    public UserStory(int id, String name, int estimate, UserStory... dependsOn) {
        super(id, name, estimate);
        dependencies = dependsOn;
    }

    @Override
    public void complete() {
        if (!isCompleted()) {
            for (UserStory i : getDependencies())
                if (!i.isCompleted())
                    return;
            super.complete();
        }
    }

    public UserStory[] getDependencies(){
        UserStory[] res = new UserStory[dependencies.length];
        System.arraycopy(dependencies, 0, res, 0, dependencies.length);
        return res;
    }

    @Override
    public String toString() {
        return String.format("[US %d] %s", getId(), getName());
    }

    @Override
    public UserStory copy() {
        UserStory res = new UserStory(getId(), getName(), getEstimate(), dependencies);
        if (isCompleted())
            res.complete();
        return res;
    }
}
