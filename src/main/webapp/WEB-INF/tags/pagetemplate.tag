<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@attribute name="title" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="beforeBody" fragment="true" %>
<c:set var="servletPath" value="${pageContext.request.servletPath}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="da">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>
        <jsp:invoke fragment="title"/>
    </title>
    <link rel="stylesheet" href="${contextPath}/css/style.css">
    <!-- Bootstrap CSS -->
    <link href="${contextPath}/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <header>
        <nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #3c1460">
            <div class="container">
                <a class="navbar-brand" href="index.jsp">
                    <img src="${contextPath}/images/olsker%20cupcakes%20banner.png" width="400px;" class="img-fluid"/>
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarNavAltMarkup"
                        aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-end" id="navbarNavAltMarkup">
                    <div class="navbar-nav">
                        <c:if test="${sessionScope.user != null}">
                            <span class="nav-item" style="color: lightgrey; padding: 0.5rem 2rem">${sessionScope.user.email}</span>
                            <span class="nav-item" style="color: lightgrey; padding: 0.5rem 2rem">Balance: ${sessionScope.user.balance} kr.</span>
                        </c:if>
                        <a class="nav-item nav-link" style="color: white" href="${contextPath}/index">Forside</a>
                        <a class="nav-item nav-link" style="color: white" href="${contextPath}/order">Ordre</a>
                        <c:if test="${sessionScope.user.role.equals('ADMIN')}">
                            <a class="nav-item nav-link" style="color: #FFE300" href="${contextPath}/viewaccounts">Accounts</a>
                        </c:if>
                        <c:if test="${sessionScope.user.role.equals('ADMIN')}">
                            <a class="nav-item nav-link" style="color: #FFE300"
                               href="${contextPath}/vieworders">Orders</a>
                        </c:if>
                        <c:if test="${sessionScope.user == null }">
                            <a class="nav-item nav-link" style="color: white" href="${contextPath}/login">Log ind</a>
                        </c:if>
                        <c:if test="${sessionScope.user != null }">
                            <a class="nav-item nav-link" style="color: white" href="${contextPath}/logout">Log ud</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </nav>
    </header>
    <jsp:invoke fragment="beforeBody"/>
    <div id="body" class="container content-wrap">
    <!--div id="body" class="container mt-4 content-wrap" style="min-height: 400px;"-->
        <jsp:doBody/>
    </div>

    <!-- Footer -->
    <!--
<div class="container mt-3">
    <hr/>
    <div class="row mt-4">
        <div class="col">
            Nørgaardsvej 30<br/>
            2800 Lyngby
        </div>
        <div class="col">
            <jsp:invoke fragment="footer"/><br/>
            <p>&copy; 2022 Olsker Cupcakes</p>
        </div>
        <div class="col">
            Datamatikeruddannelsen<br/>
            2. semester forår 2022
        </div>
    </div>

</div>

</div>
-->
    <footer class="footer">
        <div class="container">
            <div class="row mt-4">
                <ul class="col list-unstyled">
                    <li>Nørgaardsvej 30</li>
                    <li>2800 Lyngby</li>
                </ul>
                <ul class="col list-unstyled">
                    <li><jsp:invoke fragment="footer"/></li>
                    <li><p>&copy; 2022 Olsker Cupcakes</p></li>
                </ul>
                <ul class="col list-unstyled">
                    <li>Datamatikeruddannelsen</li>
                    <li>2. semester forår 2022</li>
                </ul>
            </div>
        </div>
    </footer>
    <!-- Bootstrap Bundle with Popper -->
    <script src="${contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>