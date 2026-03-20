package com.calculator.servlet;

import com.calculator.model.CalculationResult;
import com.calculator.util.CalculatorEngine;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * REST API endpoint for the calculator (returns JSON).
 * GET  /api/calculate?operand1=10&operator=%2B&operand2=5
 * POST /api/calculate  { "operand1": 10, "operator": "+", "operand2": 5 }
 */
@WebServlet(name = "CalculatorApiServlet", urlPatterns = {"/api/calculate"})
public class CalculatorApiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final CalculatorEngine engine = new CalculatorEngine();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String operand1Str = request.getParameter("operand1");
        String operand2Str = request.getParameter("operand2");
        String operator    = request.getParameter("operator");
        String action      = request.getParameter("action");

        CalculationResult result = processRequest(operand1Str, operand2Str, operator, action);
        sendJson(response, result);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<?, ?> body;
        try {
            body = objectMapper.readValue(request.getInputStream(), Map.class);
        } catch (Exception e) {
            sendErrorJson(response, "Invalid JSON body: " + e.getMessage(),
                    HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String operand1Str = getStr(body, "operand1");
        String operand2Str = getStr(body, "operand2");
        String operator    = getStr(body, "operator");
        String action      = getStr(body, "action");

        CalculationResult result = processRequest(operand1Str, operand2Str, operator, action);
        sendJson(response, result);
    }

    private CalculationResult processRequest(String op1Str, String op2Str,
                                              String operator, String action) {
        try {
            if ("sqrt".equals(action)) {
                return engine.sqrt(Double.parseDouble(op1Str));
            }
            if ("percent".equals(action)) {
                return engine.percentage(Double.parseDouble(op1Str));
            }
            if (op1Str == null || op2Str == null || operator == null) {
                return new CalculationResult("Missing parameters: operand1, operand2, operator required.");
            }
            return engine.calculate(Double.parseDouble(op1Str), operator, Double.parseDouble(op2Str));

        } catch (NumberFormatException e) {
            return new CalculationResult("Invalid number: " + e.getMessage());
        }
    }

    private void sendJson(HttpServletResponse response, CalculationResult result) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");

        Map<String, Object> json = new HashMap<>();
        json.put("success", !result.isError());
        if (result.isError()) {
            json.put("error", result.getErrorMessage());
        } else {
            json.put("result", result.getResult());
            json.put("expression", result.getExpression());
        }

        PrintWriter out = response.getWriter();
        out.print(objectMapper.writeValueAsString(json));
        out.flush();
    }

    private void sendErrorJson(HttpServletResponse response, String message, int statusCode)
            throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        Map<String, Object> json = new HashMap<>();
        json.put("success", false);
        json.put("error", message);
        response.getWriter().print(objectMapper.writeValueAsString(json));
    }

    private String getStr(Map<?, ?> map, String key) {
        Object val = map.get(key);
        return val != null ? val.toString() : null;
    }
}
