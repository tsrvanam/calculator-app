package com.calculator;

import com.calculator.model.CalculationResult;
import com.calculator.util.CalculatorEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CalculatorEngine.
 */
class CalculatorEngineTest {

    private CalculatorEngine engine;

    @BeforeEach
    void setUp() {
        engine = new CalculatorEngine();
    }

    @Test
    @DisplayName("Addition: 10 + 5 = 15")
    void testAddition() {
        CalculationResult result = engine.calculate(10, "+", 5);
        assertFalse(result.isError());
        assertEquals(15.0, result.getResult(), 0.001);
    }

    @Test
    @DisplayName("Subtraction: 10 - 3 = 7")
    void testSubtraction() {
        CalculationResult result = engine.calculate(10, "-", 3);
        assertFalse(result.isError());
        assertEquals(7.0, result.getResult(), 0.001);
    }

    @Test
    @DisplayName("Multiplication: 4 * 5 = 20")
    void testMultiplication() {
        CalculationResult result = engine.calculate(4, "*", 5);
        assertFalse(result.isError());
        assertEquals(20.0, result.getResult(), 0.001);
    }

    @Test
    @DisplayName("Division: 10 / 4 = 2.5")
    void testDivision() {
        CalculationResult result = engine.calculate(10, "/", 4);
        assertFalse(result.isError());
        assertEquals(2.5, result.getResult(), 0.001);
    }

    @Test
    @DisplayName("Division by zero returns error")
    void testDivisionByZero() {
        CalculationResult result = engine.calculate(10, "/", 0);
        assertTrue(result.isError());
        assertNotNull(result.getErrorMessage());
    }

    @Test
    @DisplayName("Modulo: 10 % 3 = 1")
    void testModulo() {
        CalculationResult result = engine.calculate(10, "%", 3);
        assertFalse(result.isError());
        assertEquals(1.0, result.getResult(), 0.001);
    }

    @Test
    @DisplayName("Power: 2 ^ 8 = 256")
    void testPower() {
        CalculationResult result = engine.calculate(2, "^", 8);
        assertFalse(result.isError());
        assertEquals(256.0, result.getResult(), 0.001);
    }

    @Test
    @DisplayName("Square Root: √16 = 4")
    void testSqrt() {
        CalculationResult result = engine.sqrt(16);
        assertFalse(result.isError());
        assertEquals(4.0, result.getResult(), 0.001);
    }

    @Test
    @DisplayName("Square Root of negative number returns error")
    void testSqrtNegative() {
        CalculationResult result = engine.sqrt(-9);
        assertTrue(result.isError());
    }

    @Test
    @DisplayName("Percentage: 50% = 0.5")
    void testPercentage() {
        CalculationResult result = engine.percentage(50);
        assertFalse(result.isError());
        assertEquals(0.5, result.getResult(), 0.001);
    }

    @Test
    @DisplayName("Unknown operator returns error")
    void testUnknownOperator() {
        CalculationResult result = engine.calculate(5, "?", 3);
        assertTrue(result.isError());
    }

    @Test
    @DisplayName("Decimal numbers: 1.5 + 2.5 = 4.0")
    void testDecimalAddition() {
        CalculationResult result = engine.calculate(1.5, "+", 2.5);
        assertFalse(result.isError());
        assertEquals(4.0, result.getResult(), 0.001);
    }

    @Test
    @DisplayName("Negative numbers: -5 + 3 = -2")
    void testNegativeNumbers() {
        CalculationResult result = engine.calculate(-5, "+", 3);
        assertFalse(result.isError());
        assertEquals(-2.0, result.getResult(), 0.001);
    }
}
