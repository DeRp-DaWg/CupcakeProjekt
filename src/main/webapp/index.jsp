<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<t:pagetemplate>
    <jsp:attribute name="title">
        Olsker Cupcakes
    </jsp:attribute>
    <jsp:attribute name="beforeBody">
        <div>
            <img src="${contextPath}/images/1.jpg" class="img-frontpage">
        </div>
    </jsp:attribute>
    <jsp:body>

        <!--
        <p>Startcode for 2nd semester </p>

        <c:if test="${sessionScope.user != null}">
            <p>You are logged in with the role of "${sessionScope.user.role}".</p>
        </c:if>

        <c:if test="${sessionScope.user == null}">
            <p>You are not logged in yet. You can do it here: <a
                    href="login.jsp">Login</a></p>
        </c:if>
        -->

    </jsp:body>

</t:pagetemplate>