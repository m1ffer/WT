package com.epam.rd.autotasks.arithmeticexpressions;

import java.util.Arrays;
import java.util.StringJoiner;

public class Expressions {

    public static Variable var(String name, int value) {
        return new Variable(name, value);
    }

    public static Expression val(int value) {
        return new Expression() {
            @Override
            public int evaluate() {
                return value;
            }

            @Override
            public String toExpressionString() {
                return value < 0 ? "(" + value + ")" : String.valueOf(value);
            }
        };
    }

    private static Expression newExpression(int value, String s){
        return new Expression() {
            @Override
            public int evaluate() {
                return value;
            }

            @Override
            public String toExpressionString() {
                return s;
            }
        };
    }

    public static Expression sum(Expression... members){
        return new Expression() {
            @Override
            public int evaluate() {
                return Arrays.stream(members)
                        .mapToInt(Expression::evaluate)
                        .sum();
            }

            @Override
            public String toExpressionString() {
                StringJoiner sj = new StringJoiner(" + ", "(", ")");
                for (Expression i : members)
                    sj.add(i.toExpressionString());
                return sj.toString();
            }
        };
    }

    public static Expression product(Expression... members){
        return new Expression() {
            @Override
            public int evaluate() {
                return Arrays.stream(members)
                        .mapToInt(Expression::evaluate)
                        .reduce(1, (a, b) -> a * b);
            }

            @Override
            public String toExpressionString() {
                StringJoiner sj = new StringJoiner(" * ", "(", ")");
                for (Expression i : members)
                    sj.add(i.toExpressionString());
                return sj.toString();
            }
        };
    }

    public static Expression difference(Expression minuend, Expression subtrahend){
        return new Expression() {
            @Override
            public int evaluate() {
                return minuend.evaluate() - subtrahend.evaluate();
            }

            @Override
            public String toExpressionString() {
                return String.format("(%s - %s)", minuend.toExpressionString(),
                        subtrahend.toExpressionString());
            }
        };
    }

    public static Expression fraction(Expression dividend, Expression divisor){
        return new Expression() {
            @Override
            public int evaluate() {
                return dividend.evaluate() / divisor.evaluate();
            }

            @Override
            public String toExpressionString() {
                return String.format("(%s / %s)", dividend.toExpressionString(),
                        divisor.toExpressionString());
            }
        };
    }
}
