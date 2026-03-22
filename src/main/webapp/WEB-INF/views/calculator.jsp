<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Java Calculator</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="page-wrapper">
    <header class="app-header">
        <h1>&#x1F9EE; Java Calculator</h1>
        <p class="subtitle">Powered by Apache Tomcat &amp; Maven</p>
    </header>

    <main class="calculator-layout">

        <!-- ========== Left: Calculator Panel ========== -->
        <section class="calculator-card">
            <h2>Calculator</h2>

            <!-- Display result or error -->
            <c:if test="${not empty result}">
                <div class="result-box ${result.error ? 'result-error' : 'result-success'}">
                    <c:choose>
                        <c:when test="${result.error}">
                            <span class="result-icon">&#x26A0;</span>
                            <span>${result.errorMessage}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="result-label">Result</span>
                            <span class="result-expression">${result.expression}</span>
                            <span class="result-value">
                                <fmt:formatNumber value="${result.result}" maxFractionDigits="10"/>
                            </span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>

            <!-- Main calculator form -->
            <form action="${pageContext.request.contextPath}/calculate" method="post" id="calcForm">
                <div class="form-group">
                    <label for="operand1">First Number</label>
                    <input type="number" id="operand1" name="operand1" step="any"
                           value="${operand1}" placeholder="e.g. 10" required>
                </div>

                <div class="form-group operator-group">
                    <label>Operator</label>
                    <div class="operator-buttons">
                        <c:forEach items="${['+', '-', '*', '/', '%', '^']}" var="op">
                            <label class="op-label">
                                <input type="radio" name="operator" value="${op}"
                                    ${operator == op ? 'checked' : ''}>
                                <span class="op-btn">${op}</span>
                            </label>
                        </c:forEach>
                    </div>
                </div>

                <div class="form-group">
                    <label for="operand2">Second Number</label>
                    <input type="number" id="operand2" name="operand2" step="any"
                           value="${operand2}" placeholder="e.g. 5">
                </div>

                <div class="btn-row">
                    <button type="submit" class="btn btn-primary">= Calculate</button>
                    <button type="button" class="btn btn-secondary" onclick="clearForm()">Clear</button>
                </div>

                <!-- Special Operations -->
                <div class="special-ops">
                    <p class="special-label">Single-operand Operations (uses First Number)</p>
                    <div class="btn-row">
                        <button type="submit" name="action" value="sqrt" class="btn btn-special">&#x221A; Square Root</button>
                        <button type="submit" name="action" value="percent" class="btn btn-special">% Percentage</button>
                    </div>
                </div>
            </form>
        </section>

        <!-- ========== Right: History Panel ========== -->
        <aside class="history-card">
            <div class="history-header">
                <h2>History</h2>
                <a href="${pageContext.request.contextPath}/calculate?action=clear"
                   class="btn-clear-history">Clear All</a>
            </div>

            <c:choose>
                <c:when test="${empty sessionScope.history}">
                    <div class="history-empty">
                        <span>&#x1F4C3;</span>
                        <p>No calculations yet.<br>Results will appear here.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <ul class="history-list">
                        <c:forEach items="${sessionScope.history}" var="h">
                            <li class="history-item">
                                <span class="h-expr">${h.expression}</span>
                            </li>
                        </c:forEach>
                    </ul>
                </c:otherwise>
            </c:choose>
        </aside>

    </main>

    <!-- ========== API Reference ========== -->
    <section class="api-card">
        <h2>&#x1F527; REST API Reference</h2>
        <p>The calculator also exposes a JSON API endpoint:</p>
        <div class="api-examples">
            <div class="api-example">
                <span class="method get">GET</span>
                <code>/calculator/api/calculate?operand1=10&amp;operator=%2B&amp;operand2=5</code>
            </div>
            <div class="api-example">
                <span class="method post">POST</span>
                <code>/calculator/api/calculate</code>
                <pre>{ "operand1": 10, "operator": "+", "operand2": 5 }</pre>
            </div>
            <div class="api-example">
                <span class="method get">GET</span>
                <code>/calculator/api/calculate?action=sqrt&amp;operand1=16</code>
            </div>
        </div>

        <div id="apiResult" class="api-try">
            <p><strong>Try it live:</strong></p>
            <div class="api-inputs">
                <input type="number" id="apiOp1" placeholder="10" step="any">
                <select id="apiOper">
                    <option value="+">+</option>
                    <option value="-">-</option>
                    <option value="*">*</option>
                    <option value="/">/</option>
                    <option value="%">%</option>
                    <option value="^">^</option>
                </select>
                <input type="number" id="apiOp2" placeholder="5" step="any">
                <button onclick="callApi()" class="btn btn-primary">Send</button>
            </div>
            <pre id="apiOutput" class="api-output">// Response will appear here</pre>
        </div>
    </section>

    <footer class="app-footer">
        <p>Java Calculator &bull; Maven WAR &bull; Apache Tomcat</p>
    </footer>
</div>

<script src="${pageContext.request.contextPath}/js/calculator.js"></script>
<script>
function clearForm() {
    document.getElementById('operand1').value = '';
    document.getElementById('operand2').value = '';
    document.querySelectorAll('input[name="operator"]').forEach(r => r.checked = false);
}

async function callApi() {
    const op1 = document.getElementById('apiOp1').value;
    const op2 = document.getElementById('apiOp2').value;
    const oper = document.getElementById('apiOper').value;
    const out  = document.getElementById('apiOutput');
    out.textContent = '// Loading...';
    try {
        const encodedOper = encodeURIComponent(oper);
        const url = `${pageContext.request.contextPath}/api/calculate?operand1=${op1}&operator=${encodedOper}&operand2=${op2}`;
        const res  = await fetch(url);
        const data = await res.json();
        out.textContent = JSON.stringify(data, null, 2);
    } catch(e) {
        out.textContent = '// Error: ' + e.message;
    }
}
</script>
</body>
</html>
