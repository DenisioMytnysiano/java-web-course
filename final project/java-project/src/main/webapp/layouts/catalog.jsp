<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Catalog</title>
    <link rel="canonical" href="https://getbootstrap.com/docs/4.6/examples/blog/">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Playfair&#43;Display:700,900" rel="stylesheet">
    <link href="/layouts/static/styles/blog.css" rel="stylesheet">
    <link href="/layouts/static/styles/new_catalog.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@dashboardcode/bsmultiselect@1.0.0/dist/css/BsMultiSelect.bs4.min.css">

</head>
<body>
<div class="container">
    <%@ include file="static/templates/header.jsp" %>

    <div class="container d-flex flex-column justify-content-center mt-50 mb-50">
        <form action="/tariff-plans" method="get" class="mb-0">
            <div class="form-row">
                <div class="col-sm-3 mb-3">
                    <label for="sortBy">Sort By:</label>
                    <select class="form-control" id="sortBy" name="sortBy">
                        <option value="price">Price</option>
                        <option value="date">Tariff Plan Name</option>>
                    </select>
                </div>
                <div class="col-sm-1 mb-3">
                    <label for="direction">Direction: </label>
                    <select class="form-control" id="direction" name="direction">
                        <option value="ASC" selected>ASC</option>
                        <option value="DESC">DESC</option>
                    </select>
                </div>
                <div class="col-sm-1 mb-3 align-text-bottom">
                    <label for="btnFilterSubmit">Apply Filter</label>
                    <input type="submit" id="btnFilterSubmit" class="btn btn-outline-dark">
                </div>
            </div>
        </form>

        <div class="row">
            <div class="col-md-12">
                <c:forEach var="item" items="${requestScope.publications}">
                    <div class="card card-body mt-3">
                        <div class="media align-items-center align-items-lg-start text-center text-lg-left flex-column flex-lg-row">
                            <div class="media-body">
                                <h6 class="media-title font-weight-semibold">
                                    <p>${item.tariffPlanName}</p>
                                    <p>${item.service.serviceName}</p>
                                </h6>
                                <p class="mb-3 description">${item.description}</p>
                            </div>
                            <div class="mt-3 mt-lg-0 ml-lg-3 text-center">
                                <h3 class="mb-0 font-weight-semibold">Price: ${item.price}$</h3>
                                <h3 class="mb-0 font-weight-semibold">For: ${item.duration} days</h3>
                                <c:if test="${sessionScope.user != null}">
                                    <a type="button" class="btn btn-dark mt-4 text-white" href="/tariff-plans/purchase?id=${item.id}">buy</a>
                                </c:if>
                                <a type="button" class="btn btn-dark mt-4 text-white" href="/tariff-plans/download?id=${item.id}">download</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<%@ include file="static/templates/footer.html" %>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
<script src="/layouts/static/js/BsMultiSelect.js"></script>
<script src="/layouts/static/js/catalog.js"></script>
</body>
</html>
