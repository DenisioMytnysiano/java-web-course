<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="generator" content="Hugo 0.80.0">
    <title>Login</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.6/examples/sign-in/">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <link href="/layouts/static/styles/bootstrap.min.css" rel="stylesheet">
    <link href="/layouts/static/styles/signin.css" rel="stylesheet">
    <link href="/layouts/static/styles/login.css" rel="stylesheet">
</head>
<body>
<div class="login-form">
    <form action="<c:url value="/login"/>" method="post">
        <h4 class="modal-title">Login to Your Account</h4>
        <c:if test="${requestScope.errMsg != null}">
            <p class="mb-2 text-danger text-center font-italic">${requestScope.errMsg}</p>
        </c:if>
        <div class="form-group">
            <input type="text" class="form-control" name="email" placeholder="Email" required="required">
        </div>
        <div class="form-group">
            <input type="password" class="form-control" name="pwd" placeholder="Password" required="required">
        </div>
        <input type="submit" class="btn btn-primary btn-block btn-lg" value="Login">
    </form>
    <div class="text-center"><a style="color: dimgrey" href="<c:url value="/"/>">Main Page</a></div>
</div>
</body>
</html>
