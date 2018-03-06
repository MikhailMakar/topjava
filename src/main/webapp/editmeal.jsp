<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h2>Meal</h2>

<c:set var="meal" value="${requestScope.mealInstance}" />
<%--<c:set var="formatter" value="${requestScope.formatter}" />--%>


<form action="meals" method="post">
    <input type="text" name="id" value="${meal.id}" hidden/>
    Дата/Время: <input type="text" name="date" value="${meal.dateTime}" ><br />
    Описание: <input type="text" name="description" value="${meal.description}" ><br />
    Калории: <input type="text" name="calories" value="${meal.calories}" ><br />

    <input type="submit" value="Отправить" />
</form>

</body>
</html>
