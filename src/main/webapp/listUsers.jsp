<%@ include file="/WEB-INF/include/head.jspf" %>
<%@ include file="mainPageNavBarBlock.jsp" %>

<html>
<head>
    <title><fmt:message key="repair_agency"></fmt:message></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>

<body>

<div class="container-fluid text-center">
    <div class="container">
        <h1><fmt:message key="list_users"></fmt:message></h1>

        <table class="table">
            <tr>
                <td><fmt:message key="name"></fmt:message></td>
                <td><fmt:message key="login"></fmt:message></td>
                <td><fmt:message key="email"></fmt:message></td>
                <th><fmt:message key="open"></fmt:message></th>
                <th><fmt:message key="delete"></fmt:message></th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.name}</td>
                    <td>${user.login}</td>
                    <td>${user.email}</td>
                    <td>
                        <form action="controller" method="get">
                            <input type="hidden" name="command" value="openCardUser">
                            <input type="hidden" name="id" id="id" class="form-control" value="${user.id}">
                            <input type="submit" name="open" value=<fmt:message key="open"></fmt:message>><br>
                        </form>
                    </td>
                    <td>
                        <form action="controller" method="get">
                            <input type="hidden" name="command" value="deleteUser">
                            <input type="hidden" name="id" id="id" class="form-control" value="${user.id}">
                            <input type="submit" name="delete" value=<fmt:message key="delete"></fmt:message>><br>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<%@ include file="footerBlock.jsp" %>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>

