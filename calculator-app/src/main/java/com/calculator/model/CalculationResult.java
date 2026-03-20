package com.calculator.model;

/**
 * Model class representing the result of a calculation.
 */
public class CalculationResult {

    private double operand1;
    private double operand2;
    private String operator;
    private double result;
    private boolean error;
    private String errorMessage;
    private String expression;

    public CalculationResult() {}

    public CalculationResult(double operand1, String operator, double operand2, double result) {
        this.operand1 = operand1;
        this.operator = operator;
        this.operand2 = operand2;
        this.result = result;
        this.error = false;
        this.expression = formatExpression(operand1, operator, operand2, result);
    }

    public CalculationResult(String errorMessage) {
        this.error = true;
        this.errorMessage = errorMessage;
    }

    private String formatExpression(double op1, String operator, double op2, double res) {
        String op1Str = (op1 == (long) op1) ? String.valueOf((long) op1) : String.valueOf(op1);
        String op2Str = (op2 == (long) op2) ? String.valueOf((long) op2) : String.valueOf(op2);
        String resStr = (res == (long) res) ? String.valueOf((long) res) : String.valueOf(res);
        return op1Str + " " + operator + " " + op2Str + " = " + resStr;
    }

    // Getters and Setters
    public double getOperand1() { return operand1; }
    public void setOperand1(double operand1) { this.operand1 = operand1; }

    public double getOperand2() { return operand2; }
    public void setOperand2(double operand2) { this.operand2 = operand2; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public double getResult() { return result; }
    public void setResult(double result) { this.result = result; }

    public boolean isError() { return error; }
    public void setError(boolean error) { this.error = error; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public String getExpression() { return expression; }
    public void setExpression(String expression) { this.expression = expression; }

    @Override
    public String toString() {
        if (error) return "Error: " + errorMessage;
        return expression;
    }
}
