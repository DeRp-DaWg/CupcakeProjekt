<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="title">
        Olsker Cupcakes - Ordre oversigt
    </jsp:attribute>

    <jsp:attribute name="footer">
        Ordre oversigt
    </jsp:attribute>

    <jsp:body>
        <br>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th scope="col">Order ID</th>
                    <th scope="col">Email</th>
                    <th scope="col">Topping</th>
                    <th scope="col">Bottom</th>
                    <th scope="col">Status</th>
                    <th scope="col">Date</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${requestScope.orders}">
                    <tr>
                        <th scope="row">${order.orderId}</th>
                        <td>${order.user.email}</td>
                        <td>${order.topping.name}</td>
                        <td>${order.bottom.name}</td>
                        <td>${order.status}</td>
                        <td>${order.date}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </jsp:body>
</t:pagetemplate>