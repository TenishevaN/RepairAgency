<%@ include file="/WEB-INF/include/head.jspf" %>
<%@ include file="mainPageNavBarBlock.jsp" %>
<%@ taglib prefix="userFieldRight" uri="/WEB-INF/tlib/userFieldRight.tld" %>
<%@ taglib prefix="tagfile" tagdir="/WEB-INF/tags" %>


<html>
<head>
    <title><fmt:message key="repair_agency"></fmt:message></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/java_script_dynamic.js"></script>
</head>

<body>

<div class="container-fluid text-center">

    <div class="container">
        <h1><fmt:message key="report"></fmt:message></h1>

        <ul class="nav navbar-nav">
            <li>
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="sortListRequestsByDate">
                    <input type="hidden" name="changeOrder" class="form-control" value="true">
                    <input type="hidden" name="orderBy" class="form-control" value="${orderBy}">
                    <input type="submit" class="custom-margin-2" name="open" value=<fmt:message
                            key="date"></fmt:message>><br>
                </form>
            </li>
            <li>
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="sortListRequestsByStatus">
                    <input type="hidden" name="changeOrder" class="form-control" value="true">
                    <input type="hidden" name="currentLocale" class="form-control" value="true">
                    <input type="hidden" name="orderBy" class="form-control" value="${orderBy}">
                    <input type="hidden" name="orderBy" class="form-control" value="${currentLocale}">
                    <input type="submit" name="open" class="custom-margin-2" value=<fmt:message
                            key="status"></fmt:message>><br>
                </form>
            </li>
            <li>
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="sortListRequestsByCost">
                    <input type="hidden" name="changeOrder" class="form-control" value="true">
                    <input type="hidden" name="orderBy" class="form-control" value="${orderBy}">
                    <input type="submit" name="open" class="custom-margin-2" value=<fmt:message
                            key="cost"></fmt:message>><br>
                </form>
            </li>
            <li>
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="formReportPDF">
                    <input type="submit" name="open" class="custom-margin-2" value=<fmt:message
                            key="report_pdf"></fmt:message>><br>
                </form>
            </li>
        </ul>
        <div style="height:300px" class="container">
            <table class="table table-bordered">
                <tr>
                    <th>&#8470;</th>
                    <th><fmt:message key="date"></fmt:message></th>
                    <th><fmt:message key="status"></fmt:message></th>
                    <th><fmt:message key="user"></fmt:message></th>
                    <th><fmt:message key="master"></fmt:message></th>
                    <th><fmt:message key="cost"></fmt:message></th>
                    <th><fmt:message key="description"></fmt:message></th>
                </tr>
                <c:forEach items="${repairRequests}" var="item">
                    <tr>
                        <td>${item.id}</td>
                        <td><fmt:formatDate pattern="dd MM yyyy" value="${item.date}"/></td>
                        <td><userFieldRight:status idStatus="${item.statusId}" nameRole="${role}"
                                                   currentLocale="${currentLocale}" area="list"/></td>
                        <td>${item.userName}</td>
                        <td><userFieldRight:master idMaster="${item.masterId}" nameRole="${role}"
                                                   currentLocale="${currentLocale}" area="list"/></td>
                        <td><tagfile:cost cost="${item.cost}"></tagfile:cost></td>
                        <td>${item.description}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <userFieldRight:pagination_list_requests idUser="${userId}" command="${command}" orderBy="${orderBy}"
                                                 current_page="${page}"/>
    </div>
</div>

<%@ include file="footerBlock.jsp" %>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>