<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error - Calculator App</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-wrapper">
    <header class="app-header">
        <h1>&#x1F9EE; Java Calculator</h1>
    </header>
    <div class="error-page">
        <h2>&#x26A0; Oops! Something went wrong.</h2>
        <p>Error Code: <strong><%= request.getAttribute("javax.servlet.error.status_code") %></strong></p>
        <p>Message: <strong><%= request.getAttribute("javax.servlet.error.message") %></strong></p>
        <a href="${pageContext.request.contextPath}/calculate" class="btn btn-primary">&#x2190; Back to Calculator</a>
    </div>
</div>
</body>
</html>
