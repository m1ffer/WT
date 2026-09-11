package com.epam.rd.autotasks;

public class CompleteByRequestTask implements Task {
    private boolean isCompleted, isExecuted;

    @Override
    public void execute() {
        if (isCompleted)
            isExecuted = true;
    }

    @Override
    public boolean isFinished() {
        return isCompleted && isExecuted;
    }

    public void complete() {
        isCompleted = true;
    }
}
