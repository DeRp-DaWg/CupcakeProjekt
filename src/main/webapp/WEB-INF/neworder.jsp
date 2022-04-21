<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<t:pagetemplate>
    <jsp:attribute name="title">
        Olsker Cupcakes - Ordre
    </jsp:attribute>

    <jsp:attribute name="footer">
        Ordre
    </jsp:attribute>

    <jsp:body>
        <br>
        <form action="" method="post">
            <div class="container">
                <div class="row row-cols-1 row-cols-md-4 g-4">
                    <c:forEach var="order" items="${sessionScope.ordersMap.keySet()}">
                        <c:set var="hiddenOrders" value="${sessionScope.ordersMap.get(order)}"/>
                        <c:set var="amount" value="${hiddenOrders.size()+1}"/>
                        <input type="hidden" id="order id" name="order id"
                               value="${order.orderId}<c:forEach var="hiddenOrder" items="${hiddenOrders}">-${hiddenOrder.orderId}</c:forEach>">
                        <div class="col">
                            <div class="card">
                                <!-- Lav en knap til at fjerne en ordre, og en til at tilføje en, både for at lave en kopi og en helt ny. -->
                                <img src="${contextPath}/images/cupcakes/${order.topping.name}-${order.bottom.name}.png"
                                     class="card-img-top" width="100%" height="140" style="background-color: grey">
                                <div class="card-body">
                                    <div class="mb-3">
                                        <h5 class="card-title">Pris: ${order.price * amount} kr.</h5>
                                        <label for="topping-${order.orderId}" class="form-label">Topping</label>
                                        <select id="topping-${order.orderId}" name="topping" class="form-select">
                                            <c:forEach var="topping" items="${requestScope.toppings}">
                                                <option value="${topping.id}"
                                                        <c:if test="${topping.id == order.topping.id}">selected</c:if>>${topping.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="mb-3">
                                        <label for="bottom-${order.orderId}" class="form-label">Bottom</label>
                                        <select id="bottom-${order.orderId}" name="bottom" class="form-select">
                                            <c:forEach var="bottom" items="${requestScope.bottoms}">
                                                <option value="${bottom.id}"
                                                        <c:if test="${bottom.id == order.bottom.id}">selected</c:if>>${bottom.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="mb-3">
                                        <label for="amount-${order.orderId}" class="form-label">Antal</label>
                                        <div class="input-group">
                                            <!--
                                            <input id="amount-${order.orderId}" name="amount" class="form-control"
                                                   value="${amount}">
                                            -->
                                            <input class="form-control" type="text" value="${amount}" readonly>
                                            <button name="button" type="submit" value="remove from order-${order.orderId}" class="btn btn-outline-secondary">-</button>
                                            <button name="button" type="submit" value="add to order-${order.orderId}" class="btn btn-outline-secondary">+</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <div class="col" style="min-height: 464px">
                        <button name="button" type="submit" value="new order" class="btn btn-outline-secondary" style="width: 100%; height: 100%">Ny ordre</button>
                    </div>
                </div>
                <br>
                <div class="row justify-content-between">
                    <div class="col-auto">
                        <button name="button" type="submit" value="save" class="btn btn-primary">Gem ordre</button>
                    </div>
                    <div class="col-auto">
                        <button name="button" type="submit" value="submit" class="btn btn-primary">Bestil</button>
                    </div>
                </div>
            </div>
        </form>

    </jsp:body>
</t:pagetemplate>