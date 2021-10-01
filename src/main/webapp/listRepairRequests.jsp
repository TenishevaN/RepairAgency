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

<div class="container" style="height:500px">
    <div class="container-fluid text-center">
        <h1><fmt:message key="requests"></fmt:message></h1>
    </div>
    <div class="container-fluid text-center">
        <c:set var="role" value="${fn:toLowerCase(role)}"></c:set>
        <c:if test="${role eq 'user'}">
            <div class="form-group">
                <form action="controller" method="get">
                    <button type="button" data-toggle="modal" data-target="#insertModalPage">
                        <fmt:message key="new_repair_request"></fmt:message>
                    </button>
                </form>
            </div>
        </c:if>
    </div>

    <c:set var="role" value="${fn:toLowerCase(role)}"></c:set>
    <c:if test="${role == 'manager'}">
        <ul class="nav navbar-nav">
            <li>
                <form action="controller" class="form-inline" method="get">
                    <input type="hidden" name="command" value="filtertListRequestsByStatus">
                    <label class=custom-margin-2> <fmt:message key="status"></fmt:message>:</label>
                    <div class="custom-margin-2">
                        <userFieldRight:status_filter currentLocale="${sessionScope.currentLocale}"
                                                      idStatus="${idStatus}"/>
                    </div>
                    <input type="submit" class="custom-margin-2" name="open" value=<fmt:message
                            key="status"></fmt:message>><br>
                </form>

            </li>
            <li>
                <form action="controller" class="form-inline" method="get">
                    <input type="hidden" name="command" value="filtertListRequestsByMaster">
                    <label class=custom-margin-2><fmt:message key="master"></fmt:message>:</label>
                    <div class="custom-margin-2">
                        <userFieldRight:master_filter currentLocale="${sessionScope.currentLocale}"
                                                      listMaster="${listMasters}"
                                                      idMaster="${idMaster}"/>
                    </div>
                    <input type="submit" class="custom-margin-2" name="open" value=<fmt:message
                            key="master"></fmt:message>><br>
                </form>
            </li>
        </ul>
    </c:if>
    <div style="height:400px" class="container">
        <table class="table">
            <tr>
                <th>&#8470;</th>
                <th><fmt:message key="date"></fmt:message></th>
                <th><fmt:message key="status"></fmt:message></th>
                <th><fmt:message key="master"></fmt:message></th>
                <th><fmt:message key="cost"></fmt:message></th>
                <th><fmt:message key="description"></fmt:message></th>
                <th><fmt:message key="open"></fmt:message></th>
                <th><fmt:message key="delete"></fmt:message></th>
            </tr>
            <c:forEach items="${repairRequests}" var="item">
                <tr>
                    <td>${item.id}</td>
                    <td><fmt:formatDate pattern="dd.MM.yyyy" value="${item.date}"/></td>
                    <td><userFieldRight:status idStatus="${item.statusId}" nameRole="${role}"
                                               currentLocale="${sessionScope.currentLocale}" area="list"/></td>
                    <td><userFieldRight:master idMaster="${item.masterId}" nameRole="${role}"
                                               listMaster="${listMasters}"
                                               currentLocale="${sessionScope.currentLocale}" area="list"/></td>
                    <td><tagfile:cost cost="${item.cost}"></tagfile:cost></td>
                    <td>${item.description}</td>
                    <td>
                        <form action="controller" method="get">
                            <input type="hidden" name="command" value="openCardRepairRequest">
                            <input type="hidden" name="id" id="id" class="form-control" value="${item.id}">
                            <input type="submit" name="open" value=<fmt:message key="open"></fmt:message>><br>
                        </form>
                    </td>
                    <td>
                        <form action="controller" method="get">
                            <input type="hidden" name="command" value="deleteCardRepairRequest">
                            <input type="hidden" name="id" id="id" class="form-control" value="${item.id}">
                            <input type="submit" name="delete" value=<fmt:message key="delete"></fmt:message>><br>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

    </div>
    <userFieldRight:pagination_list_requests idUser="${userId}" command="${command}" orderBy="${orderBy}"
                                             status_id="${status_id}" master_id="${master_id}"
                                             current_page="${page}"/>
</div>

<%@ include file="footerBlock.jsp" %>

<!-- Modal Create new Repair request-->
<div class="modal fade" id="insertModalPage" role="dialog">
    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><fmt:message key="new_request"></fmt:message></h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <h1></h1>

                    <form style="width:1000px" action="controller" method="post">
                        <input type="hidden" name="command" value="insertRepairRequest">
                        <input type="hidden" id="idRepairRequest" name="idRepairRequest" value="${repairRequest.id}"/>
                        <label class="control-label col-xs-4"><fmt:message key="description"></fmt:message>:</label>
                        <div class="form-group" style="width:500px" value="Comment">
                                            <textarea rows="10" class="form-control"
                                                      name=description id=descriptionValue></textarea>
                            <label style="color:red; visibility: hidden" id="errorDescription"><fmt:message
                                    key="error_description"></fmt:message></label>
                        </div>
                        <button type="submit" class="btn btn-default" onclick="return handleDescriptionChange()">
                            <fmt:message key="send"></fmt:message></button>
                        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message
                                key="close"></fmt:message></button>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
            </div>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>