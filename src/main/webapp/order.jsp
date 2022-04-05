<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
        Order
    </jsp:attribute>

    <jsp:attribute name="footer">
        Order
    </jsp:attribute>

    <jsp:body>

        <h3>You can order your cupcake here</h3>

        <form action="">
            <label for="bottom"></label>
            <select id="bottom" name="bottom">
                <option value="${orderList.get(0)}"></option>
            </select>
            <input type="submit">
        </form>

    </jsp:body>
</t:pagetemplate>