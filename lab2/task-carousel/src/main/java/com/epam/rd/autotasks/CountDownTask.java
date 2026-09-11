package com.epam.rd.autotasks;

import lombok.Getter;

public class CountDownTask implements Task{
    @Getter
    private int value;

    public CountDownTask(int value) {
        if (value < 0)
            value = 0;
        this.value = value;
    }

    @Override
    public void execute() {
        if (value != 0)
            value--;
    }

    @Override
    public boolean isFinished() {
        return value == 0;
    }
}
