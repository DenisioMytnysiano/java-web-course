<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">

    <meta name="generator" content="Hugo 0.80.0">
    <title>Denisio Provider</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.6/examples/blog/">
    <link href="/layouts/static/styles/bootstrap.min.css" rel="stylesheet">
    <link href="/layouts/static/styles/index.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Playfair&#43;Display:700,900" rel="stylesheet">
    <link href="/layouts/static/styles/blog.css" rel="stylesheet">
</head>
<body>

<div class="container">

    <%@ include file="layouts/static/templates/header.jsp" %>
    <c:choose>
        <c:when test="${sessionScope.message != null}">
        <p class="text-success" style="text-align: center"><c:out value="${sessionScope.message}"/></p>
            <c:remove var="message" scope="session"/>
        </c:when>
        <c:when test="${sessionScope.error != null}">
        <p class="text-warning" style="text-align: center"><c:out value="${sessionScope.error}"/></p>
            <c:remove var="error" scope="session"/>
        </c:when>
    </c:choose>

    <%--Modal--%>
    <div class="modal fade" id="adminModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title align-content-center" id="exampleModalLabel">Admin Panel</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="d-flex flex-column">
                        <a class="btn btn-sm mb-4 btn-outline-secondary" href="${contextPath}/catalog/add">Add new
                            publication</a>
                        <form action="#" method="get">
                            <div class="input-group mb-3">
                                <input type="text" class="form-control" placeholder="Email to block"
                                       aria-label="Recipient's username" aria-describedby="basic-addon2">
                                <div class="input-group-append">
                                    <button class="btn btn-outline-secondary" type="button">Block</button>
                                </div>
                            </div>
                        </form>
                        <form action="#" method="get">
                            <div class="input-group mb-3">
                                <input type="text" class="form-control" placeholder="Email to unblock"
                                       aria-label="Recipient's username" aria-describedby="basic-addon2">
                                <div class="input-group-append">
                                    <button class="btn btn-outline-secondary" type="button">Unblock</button>
                                </div>
                            </div>

                        </form>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>


    <%@ include file="layouts/static/templates/footer.html" %>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
            integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js"
            integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF"
            crossorigin="anonymous"></script>
</body>
</html>
