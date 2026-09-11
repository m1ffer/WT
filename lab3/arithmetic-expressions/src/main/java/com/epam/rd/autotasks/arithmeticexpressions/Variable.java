package com.epam.rd.autotasks.arithmeticexpressions;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class Variable implements Expression {
    private final String name;
    @Setter
    @NonNull private Integer value;

    @Override
    public int evaluate() {
        return value;
    }

    @Override
    public String toExpressionString() {
        return name;
    }
}
