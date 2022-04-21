<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<t:pagetemplate>
    <jsp:attribute name="title">
        Olsker Cupcakes - Konto oprettelse
    </jsp:attribute>

    <jsp:attribute name="footer">
        Konto oprettelse
    </jsp:attribute>

    <jsp:body>

        <h3>Her kan du lave en konto</h3>

        <form action="createaccount" method="post">
            <div class="mb-3">
                <label for="email" class="form-label">Email: </label>
                <input type="text" id="email" name="email" class="form-control"/>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password: </label>
                <input type="password" id="password" name="password" class="form-control"/>
            </div>
            <button type="submit" class="btn btn-primary">Opret konto</button>
        </form>
    </jsp:body>
</t:pagetemplate>