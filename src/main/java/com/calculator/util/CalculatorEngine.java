package com.calculator.util;

import com.calculator.model.CalculationResult;

/**
 * Core calculator engine that performs arithmetic operations.
 */
public class CalculatorEngine {

    /**
     * Performs a basic arithmetic operation.
     *
     * @param operand1 First number
     * @param operator Operator: +, -, *, /
     * @param operand2 Second number
     * @return CalculationResult containing the result or error details
     */
    public CalculationResult calculate(double operand1, String operator, double operand2) {
        try {
            double result;

            switch (operator) {
                case "+":
                    result = add(operand1, operand2);
                    break;
                case "-":
                    result = subtract(operand1, operand2);
                    break;
                case "*":
                case "x":
                    result = multiply(operand1, operand2);
                    break;
                case "/":
                    if (operand2 == 0) {
                        return new CalculationResult("Division by zero is not allowed.");
                    }
                    result = divide(operand1, operand2);
                    break;
                case "%":
                    if (operand2 == 0) {
                        return new CalculationResult("Modulo by zero is not allowed.");
                    }
                    result = modulo(operand1, operand2);
                    break;
                case "^":
                    result = power(operand1, operand2);
                    break;
                default:
                    return new CalculationResult("Unknown operator: " + operator);
            }

            if (Double.isInfinite(result) || Double.isNaN(result)) {
                return new CalculationResult("Result is undefined (Infinity or NaN).");
            }

            return new CalculationResult(operand1, operator, operand2, result);

        } catch (Exception e) {
            return new CalculationResult("Calculation error: " + e.getMessage());
        }
    }

    /**
     * Performs square root of a single number.
     */
    public CalculationResult sqrt(double operand) {
        if (operand < 0) {
            return new CalculationResult("Cannot compute square root of a negative number.");
        }
        double result = Math.sqrt(operand);
        CalculationResult cr = new CalculationResult();
        cr.setOperand1(operand);
        cr.setOperator("√");
        cr.setResult(result);
        cr.setError(false);
        String res = (result == (long) result) ? String.valueOf((long) result) : String.valueOf(result);
        cr.setExpression("√" + (long) operand + " = " + res);
        return cr;
    }

    /**
     * Computes the percentage of a number.
     */
    public CalculationResult percentage(double operand) {
        double result = operand / 100.0;
        CalculationResult cr = new CalculationResult();
        cr.setOperand1(operand);
        cr.setOperator("%");
        cr.setResult(result);
        cr.setError(false);
        cr.setExpression(operand + "% = " + result);
        return cr;
    }

    // Private arithmetic helpers
    private double add(double a, double b)      { return a + b; }
    private double subtract(double a, double b) { return a - b; }
    private double multiply(double a, double b) { return a * b; }
    private double divide(double a, double b)   { return a / b; }
    private double modulo(double a, double b)   { return a % b; }
    private double power(double a, double b)    { return Math.pow(a, b); }
}
