<%@ include file="/WEB-INF/include/head.jspf" %>
<%@ include file="mainPageNavBarBlock.jsp" %>
<%@ taglib prefix="userFieldRight" uri="/WEB-INF/tlib/userFieldRight.tld" %>

<html>
<head>
    <title>Repair agency</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>

<body>

<div class="container-fluid text-center">
    <c:set var="role" value="${fn:toLowerCase(role)}"></c:set>
    <c:if test="${role eq 'user'}">
        <div class="form-group">
            <form action="controller" method="get">
                <button type="button"  data-toggle="modal" data-target="#insertModalPage">
                    <fmt:message key="new_repair_request"></fmt:message>
                </button>
            </form>
        </div>
    </c:if>

    <div class="container">
        <h1><fmt:message key="requests"></fmt:message></h1>
        <c:set var="role" value="${fn:toLowerCase(role)}"></c:set>
        <c:if test="${role == 'manager'}">
            <ul class="nav navbar-nav">
                <li>
                    <form action="controller" method="get">
                        <input name="command" type="hidden" value="filtertListRequestsByStatus">
                        <label class="control-label col-xs-4">Status:</label>
                        <userFieldRight:status_filter currentLocale = "${currentLocale}" idStatus = "${idStatus}"/>
                        <input type="submit" name="open" value=<fmt:message key="status"></fmt:message>><br>
                    </form>
                </li>
                <li>
                    <form action="controller" method="get">
                        <input name="command" type="hidden" value="filtertListRequestsByMaster">
                        <label class="control-label col-xs-4">Master:</label>
                        <userFieldRight:master_filter currentLocale = "${currentLocale}" idMaster = "${idMaster}"/>
                        <input type="submit" name="open" value=<fmt:message key="master"></fmt:message>><br>
                    </form>
                </li>
            </ul>
        </c:if>

        <table class="table">
            <tr>
                <th>&#8470;</th>
                <th>Date</th>
                <th>Status</th>
                <th>Master</th>
                <th>Cost</th>
                <th>Description</th>
                <th>Open</th>
                <th>Delete</th>
            </tr>
            <c:forEach items="${repairRequests}" var="item">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.date}</td>
                    <td>${item.statusName}</td>
                    <td>${item.masterName}</td>
                    <td>${item.cost}</td>
                    <td>${item.description}</td>
                    <td>
                        <form action="controller" method="get">
                            <input name="command" type="hidden" value="openCardRepairRequest">
                            <input type="hidden" name="id" id="id" class="form-control" value="${item.id}">
                            <input type="submit" name="open" value="Open"><br>
                        </form>
                    </td>
                    <td>
                        <form action="controller" method="get">
                            <input name="command" type="hidden" value="deleteCardRepairRequest">
                            <input type="hidden" name="id" id="id" class="form-control" value="${item.id}">
                            <input type="submit" name="delete" value="Delete"><br>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <userFieldRight:pagination_list_requests idUser="${userId}" command="${command}" orderBy="${orderBy}"
                                                 status_id="${status_id}" master_id="${master_id}"/>
    </div>
</div>

<%@ include file="footerBlock.jsp" %>

<!-- Modal Create new Repair request-->
<div class="modal fade" id="insertModalPage" role="dialog">
    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Repair request</h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <h1></h1>

                    <form style="width:300px" action="controller" method="post">
                        <input name="command" type="hidden" value="insertRepairRequest">
                        <input type="hidden" id="idRepairRequest" name="idRepairRequest" value="${repairRequest.id}"/>
                        <label class="control-label col-xs-4">Description:</label>
                        <div class="form-group" value="Comment">
                                            <textarea rows="5" class="form-control"
                                                      name=description>  Write your description.. </textarea>
                        </div>
                        <button type="submit" class="btn btn-default"><fmt:message key="send"></fmt:message></button>
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