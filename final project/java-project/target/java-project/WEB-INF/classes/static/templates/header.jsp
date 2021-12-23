<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ page import="com.denisio.app.model.entity.UserRole" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html>
<head>
    <title></title>
    <style>
        .blog-header {
            line-height: 1;
            border-bottom: 1px solid #e5e5e5;
        }

        .blog-header-logo {
            font-family: "Playfair Display", Georgia, "Times New Roman", serif;
            font-size: 2.25rem;
        }

        .blog-header-logo:hover {
            text-decoration: none;
        }

        h1, h2, h3, h4, h5, h6 {
            font-family: "Playfair Display", Georgia, "Times New Roman", serif;
        }

        .nav-scroller {
            position: relative;
            z-index: 2;
            height: 2.75rem;
            overflow-y: hidden;
        }

        .nav-scroller .nav {
            display: -ms-flexbox;
            display: flex;
            -ms-flex-wrap: nowrap;
            flex-wrap: nowrap;
            padding-bottom: 1rem;
            margin-top: -1px;
            overflow-x: auto;
            text-align: center;
            white-space: nowrap;
            -webkit-overflow-scrolling: touch;
        }

        .nav-scroller {
            padding-top: .75rem;
            padding-bottom: .75rem;
            font-size: .875rem;
        }
    </style>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.5.0/css/flag-icon.min.css" rel="stylesheet" />

</head>
<body>
<%--    Header--%>
<header class="blog-header py-3">
    <div class="row flex-nowrap justify-content-between align-items-center">
        <div class="col-4 pt-1 d-flex flex-column justify-content-start">
            <a class="btn btn-outline-dark col-4" href="${contextPath}/tariff-plans">Tariff Plans</a>
        </div>
        <div class="col-4 text-center">
            <a class="blog-header-logo text-dark" href="${contextPath}/">Denisio Provider</a>
        </div>
        <div class="col-4 d-flex flex-column justify-content-end align-items-end">
            <c:if test="${sessionScope.user == null}">
                <a class="btn btn-sm btn-outline-secondary mt-1" href="${contextPath}/login">Sign In</a>
            </c:if>
            <c:if test="${sessionScope.user != null}">

                <h5 class="font-italic text-dark">
                    <c:out value="${sessionScope.user.clientName} ${sessionScope.user.surname}"/>
                </h5>
                <div class="d-flex justify-content-center">
                    <c:if test="${sessionScope.user.role.equals(UserRole.ADMIN)}">
                        <button type="button" class="btn btn-sm btn-outline-secondary" data-toggle="modal"
                                data-target="#adminModal">Admin</button>
                    </c:if>
                    <a class="btn btn-sm ml-1 btn-outline-secondary" href="/account">Account</a>
                    <a class="btn btn-sm ml-1 btn-outline-secondary" href="/logout">Logout</a>
                </div>
            </c:if>

        </div>
    </div>
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
</header>

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
                    <a class="btn btn-sm mb-4 btn-outline-secondary" href="${contextPath}/catalog/add">Add</a>
                    <form action="${contextPath}/periods/add" method="post" >
                        <div class="input-group mb-3">
                            <input type="hidden" class="form-control addPeriodForm" placeholder="Name"
                                   name="name">
                            <input type="hidden" class="form-control addPeriodForm" placeholder=""
                                   name="description">
                            <div class="input-group-append">
                                <input type="hidden" class="btn btn-outline-secondary" id="submitPeriodBtn" value="Submit">
                            </div>
                        </div>
                    </form>

                    <form action="${contextPath}/genres/add" method="post" >
                        <div class="input-group mb-3">
                            <input type="hidden" class="form-control addGenreForm" placeholder=""
                                   name="name">
                            <input type="hidden" class="form-control addGenreForm" placeholder=""
                                   name="description">
                            <div class="input-group-append">
                                <input type="hidden" class="btn btn-outline-secondary" id="submitGenreBtn" value="Submit">
                            </div>
                        </div>
                    </form>

                    <form action="/clients/block" method="post">
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" name="email" placeholder="Email Block" aria-label="Recipient's username" aria-describedby="basic-addon2">
                            <div class="input-group-append">
                                <button class="btn btn-outline-secondary" type="submit">Block</button>
                            </div>
                        </div>
                    </form>
                    <form action="/clients/unblock" method="post">
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" name="email" placeholder="Email Unblock" aria-label="Recipient's username" aria-describedby="basic-addon2">
                            <div class="input-group-append">
                                <button class="btn btn-outline-secondary" type="submit">Unblock</button>
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

<script>

    $('.addPeriodBtn').on('click', addPeriod);
    $('.addGenreBtn').on('click', addGenre);

    function addPeriod() {
        $('.addPeriodForm').attr("type", "text");
        $('#submitPeriodBtn').attr("type", "submit");
    }

    function addGenre() {
        $('.addGenreForm').attr("type", "text");
        $('#submitGenreBtn').attr("type", "submit");
    }



</script>

</body>
</html>
