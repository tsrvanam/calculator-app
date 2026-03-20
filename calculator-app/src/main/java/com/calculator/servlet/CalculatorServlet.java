package com.calculator.servlet;

import com.calculator.model.CalculationResult;
import com.calculator.util.CalculatorEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main servlet handling calculator form submissions (standard web flow).
 */
@WebServlet(name = "CalculatorServlet", urlPatterns = {"/calculate"})
public class CalculatorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final CalculatorEngine engine = new CalculatorEngine();
    private static final int MAX_HISTORY = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Clear history if requested
        if ("clear".equals(request.getParameter("action"))) {
            HttpSession session = request.getSession();
            session.removeAttribute("history");
        }
        request.getRequestDispatcher("/WEB-INF/views/calculator.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String operand1Str = request.getParameter("operand1");
        String operand2Str = request.getParameter("operand2");
        String operator    = request.getParameter("operator");
        String action      = request.getParameter("action");

        CalculationResult result = null;

        try {
            // Handle single-operand operations
            if ("sqrt".equals(action)) {
                double op1 = parseDouble(operand1Str);
                result = engine.sqrt(op1);
            } else if ("percent".equals(action)) {
                double op1 = parseDouble(operand1Str);
                result = engine.percentage(op1);
            } else {
                // Standard binary operation
                if (operand1Str == null || operand1Str.isEmpty() ||
                    operand2Str == null || operand2Str.isEmpty()) {
                    result = new CalculationResult("Please enter both operands.");
                } else if (operator == null || operator.isEmpty()) {
                    result = new CalculationResult("Please select an operator.");
                } else {
                    double op1 = parseDouble(operand1Str);
                    double op2 = parseDouble(operand2Str);
                    result = engine.calculate(op1, operator, op2);
                }
            }
        } catch (NumberFormatException e) {
            result = new CalculationResult("Invalid number format. Please enter valid numbers.");
        }

        // Persist history in session
        if (result != null && !result.isError()) {
            addToHistory(request.getSession(true), result);
        }

        request.setAttribute("result", result);
        request.setAttribute("operand1", operand1Str);
        request.setAttribute("operand2", operand2Str);
        request.setAttribute("operator", operator);

        request.getRequestDispatcher("/WEB-INF/views/calculator.jsp").forward(request, response);
    }

    @SuppressWarnings("unchecked")
    private void addToHistory(HttpSession session, CalculationResult result) {
        List<CalculationResult> history =
            (List<CalculationResult>) session.getAttribute("history");
        if (history == null) {
            history = new ArrayList<>();
        }
        history.add(0, result); // newest first
        if (history.size() > MAX_HISTORY) {
            history = history.subList(0, MAX_HISTORY);
        }
        session.setAttribute("history", history);
    }

    private double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new NumberFormatException("Empty value");
        }
        return Double.parseDouble(value.trim());
    }
}
