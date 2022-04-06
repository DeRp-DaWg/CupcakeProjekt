<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<t:pagetemplate>
    <jsp:attribute name="header">
        Order
    </jsp:attribute>

    <jsp:attribute name="footer">
        Order
    </jsp:attribute>

    <jsp:body>

        <h3>You can order your cupcake here</h3>

        <form action="" method="post">
            <div class="container">
                <c:forEach var="order" items="${sessionScope.orders}">
                    <input type="hidden" id="order id" name="order id" value="${order.orderId}">
                    <div class="row">
                        <div class="col mb-3">
                            <label for="bottom" class="form-label">Bottom:</label>
                            <select id="bottom" name="bottom" class="form-select">
                                <c:forEach var="bottom" items="${requestScope.bottoms}">
                                    <option value="${bottom.id}" <c:if test="${bottom.id == order.bottom.id}">selected</c:if>>${bottom.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col mb-3">
                            <label for="topping" class="form-label">Topping:</label>
                            <select id="topping" name="topping" class="form-select">
                                <c:forEach var="topping" items="${requestScope.toppings}">
                                    <option value="${topping.id}" <c:if test="${topping.id == order.topping.id}">selected</c:if>>${topping.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col mb-3">
                            <br>
                            <button name="button" type="submit" value="remove-${order.orderId}" class="btn btn-primary">Fjern</button>
                        </div>
                    </div>
                </c:forEach>
                <div class="row">
                    <div class="col mb-3">
                        <button name="button" type="submit" value="new order" class="btn btn-primary">Ny ordre</button>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <button name="button" type="submit" value="save" class="btn btn-primary">Gem ordre</button>
                    </div>
                    <div class="col">
                        <button name="button" type="submit" value="submit" class="btn btn-primary">Bestil</button>
                    </div>
                </div>
            </div>
        </form>

    </jsp:body>
</t:pagetemplate>