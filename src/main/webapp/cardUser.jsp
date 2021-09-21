<%@ include file="/WEB-INF/include/head.jspf" %>
<%@ include file="mainPageNavBarBlock.jsp" %>
<%@ taglib prefix="userFieldRight" uri="/WEB-INF/tlib/userFieldRight.tld" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/dynamics.js"></script>


</head>

<body>

<div class="container">
    <div>
        <h2><fmt:message key="user"></fmt:message></h2>
        <div class="divider bg-primary mx-auto"></div>
    </div>
    <div class="row">
        <div class="col-md-5">
        </div>
        <div class="col-md-4">
            <div class="form-group">
                <div class="form-inline">
                    <label><fmt:message
                            key="account_balance"></fmt:message> ${total}</label>

                    <c:set var="role" value="${fn:toLowerCase(role)}"></c:set>
                    <c:set var="userInvoiceId" value="${user.invoiceId}"></c:set>
                    <c:if test="${(role == 'manager') and (userInvoiceId == -1)}">
                        <form action="controller" method="post">
                            <input name="command" type="hidden" value="insertInvoice">
                            <input type="hidden" name="userId" value="${user.id}"/>
                            <button type="submit" class="btn btn-default"><fmt:message
                                    key="add_invoice"></fmt:message></button>
                        </form>
                    </c:if>

                    <c:if test="${(role eq 'manager') and (userInvoiceId != -1)}">
                        <form action="controller" method="get">
                            <button type="button" class="btn btn-default" data-toggle="modal"
                                    data-target="#insertReplenishmentPage">
                                <fmt:message key="replenish"></fmt:message>
                            </button>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="col-md-8 mx-auto">
            <form name="userForm" id="userForm" action="controller" method="post">
                <input name="command" type="hidden" value="updateCardUser">
                <input type="hidden" id="id" name="id" value="${user.id}"/>
                <div class="form-group">
                    <label for="login"><fmt:message key="login"></fmt:message></label>
                    <input type="text" name="login" class="form-control" id="login" value="${user.login}"/>
                </div>
                <div class="form-group">
                    <label for="name"><fmt:message key="name"></fmt:message></label>
                    <input type="text" name="name" class="form-control" id="name" value="${user.name}"/> <br>
                </div>
                <div class="form-group">
                    <label for="email"><fmt:message key="email"></fmt:message></label>
                    <input type="email" class="form-control" id="email" name="email" value="${user.email}"/> <br>
                    <div style="color:red" id="errorEmail"></div>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-4">Role:</label>
                    <userFieldRight:role id="${user.roleId}"/>
                </div>
                <div class="text-left mt-3">
                    <button type="submit" onclick="return handleEmailChange()" class="btn btn-default"><fmt:message
                            key="update"></fmt:message></button>
                </div>
            </form>
        </div>
    </div>
</div>
<p></p>
<hr>
<h3></h3>
<p></p>

<%@ include file="footerBlock.jsp" %>

<!-- Modal replenishment-->
<div class="modal fade" id="insertReplenishmentPage" role="dialog">

    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><fmt:message key="replenishment"></fmt:message></h4>
            </div>
            <div class="modal-body">
                <div class="container">

                    <form style="width:300px" action="controller" method="post">
                        <input type="hidden" name="command" value="insertPayment">
                        <input type="hidden" id="idRepairRequest" name="idRepairRequest" value="-1"/>
                        <input type="hidden" id="idUser" name="idUser" value="${user.id}"/>
                        <input type="hidden" name="operation" value="replenishment">
                        <div class="form-group">
                            <label for="ammount"><fmt:message key="ammount"></fmt:message></label>
                            <input type="number" step="0.01" name="ammount" class="form-control" id="ammount"><br>
                        </div>
                        <button type="submit" class="btn btn-default"><fmt:message
                                key="replenish"></fmt:message></button>
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
