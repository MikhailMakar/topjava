<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>

<table border=2 width="400" bgcolor="f0ffff">
    <thead>
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    </thead>
    <tbody>
    <c:set var="meals" value="${requestScope.meals}" />
    <c:set var="formatter" value="${requestScope.formatter}" />
    <c:set var="color" value="#004d00" />
    <c:forEach items="${meals}" var="meal">
        <tr style="color: ${meal.exceed ? '#d63104' : '#00ab14'}">
            <td align="center"><c:out value="${meal.dateTime.format(formatter)}" /></td>
            <td align="center"><c:out value="${meal.description}" /></td>
            <td align="center"><c:out value="${meal.calories}" /></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
