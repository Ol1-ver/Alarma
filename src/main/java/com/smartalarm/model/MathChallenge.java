package com.smartalarm.model;

import java.util.Random;

public class MathChallenge {
    private final int operandA;
    private final int operandB;
    private final Operator operator;
    private final int expectedAnswer;

    public enum Operator {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("*");

        private final String symbol;

        Operator(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    public MathChallenge(int operandA, int operandB, Operator operator) {
        this.operandA = operandA;
        this.operandB = operandB;
        this.operator = operator;
        this.expectedAnswer = switch (operator) {
            case ADD -> operandA + operandB;
            case SUBTRACT -> operandA - operandB;
            case MULTIPLY -> operandA * operandB;
        };
    }

    public static MathChallenge generate(long seed) {
        Random random = new Random(seed);
        int a = 10 + random.nextInt(20);
        int b = 5 + random.nextInt(15);
        Operator operator = Operator.values()[random.nextInt(Operator.values().length)];
        return new MathChallenge(a, b, operator);
    }

    public String getQuestion() {
        return String.format("%d %s %d", operandA, operator.getSymbol(), operandB);
    }

    public boolean validateAnswer(int answer) {
        return answer == expectedAnswer;
    }

    public int getExpectedAnswer() {
        return expectedAnswer;
    }
}
