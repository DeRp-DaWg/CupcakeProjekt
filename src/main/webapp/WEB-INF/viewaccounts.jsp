<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<t:pagetemplate>
    <jsp:attribute name="title">
        Olsker Cupcakes - Konti oversigt
    </jsp:attribute>

    <jsp:attribute name="footer">
        Konti oversigt
    </jsp:attribute>

    <jsp:body>
        <br>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th scope="col">User ID</th>
                    <th scope="col">Email</th>
                    <th scope="col">Role</th>
                </tr>
            </thead>
            <tbody>
                <form method="post" action="${contextPath}/vieworders">
                <c:forEach var="user" items="${requestScope.users}">
                        <tr>
                            <th scope="row"><button type="submit" name="user id" value="${user.userId}" class="button-link">${user.userId}</button></th>
                            <td><button type="submit" name="user id" value="${user.userId}" class="button-link">${user.email}</button></td>
                            <td><button type="submit" name="user id" value="${user.userId}" class="button-link">${user.role}</button></td>
                        </tr>
                </c:forEach>
                </form>
            </tbody>
        </table>

    </jsp:body>
</t:pagetemplate>