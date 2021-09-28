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
    <script type="text/javascript" src="js/java_script_dynamic.js"></script>
</head>

<body>

<div class="container">
    <h2><fmt:message key="user"></fmt:message></h2>
    <div class="row">
        <div class="col-md-5">
        </div>

        <c:if test="${(showAccount eq 'true')}">
            <div class="col-md-4">
                <div class="form-group">
                    <div class="form-inline">
                        <label style="margin-right: 20px; font-size: 15px"><fmt:message
                                key="account_balance"></fmt:message> ${total}</label>
                        <c:if test="${(role eq 'manager')}">
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
        </c:if>
        <div class="col-md-8 mx-auto">
            <form name="userForm" id="userForm" action="controller" method="post">
                <input name="command" type="hidden" value="updateCardUser">
                <input type="hidden" id="id" name="id" value="${user.id}"/>
                <div>
                    <label class="control-label col-xs-1"><fmt:message key="login"></fmt:message></label>
                    <input type="text" name="login" class="form-control" id="loginValue" value="${user.login}"/>
                    <label style="color:red; visibility: hidden" id="errorLogin"><fmt:message
                            key="errorLogin"></fmt:message></label>
                </div>
                <label class="control-label col-xs-1"><fmt:message key="name"></fmt:message></label>
                <input type="text" name="name" class="form-control" id="nameValue" value="${user.name}"/> <br>
                <label style="color:red; visibility: hidden" id="errorName"><fmt:message
                        key="errorName"></fmt:message></label>

                <div>
                    <label class="control-label col-xs-1"><fmt:message key="email"></fmt:message></label>
                    <input type="email" class="form-control" id="email" name="email" value="${user.email}"/> <br>
                    <label style="color:red; visibility: hidden" id="errorEmail"><fmt:message
                            key="errorEmail"></fmt:message></label>
                </div>
                <label class="control-label col-xs-1"><fmt:message key="role"></fmt:message></label>
                <userFieldRight:role id="${user.roleId}"/>

                </br>
                <div>
                    <label class="control-label col-xs-4"><fmt:message key="name"></fmt:message> EN</label>
                    <input type="text" name="nameEN" class="form-control" id="nameEN" value="${nameEN}"/> <br>
                </div>
                <div>
                    <label class="control-label col-xs-4"><fmt:message key="name"></fmt:message> UK</label>
                    <input type="text" name="nameUK" class="form-control" id="nameUK" value="${nameUK}"/> <br>
                </div>
                <div>
                    <label class="control-label col-xs-4"><fmt:message key="name"></fmt:message> RU</label>
                    <input type="text" name="nameRU" class="form-control" id="nameRU" value="${nameRU}"/> <br>
                </div>
                <div class="text-left mt-3">
                    <button type="submit" onclick="return handleUserCardChange()" class="btn btn-default"><fmt:message
                            key="update"></fmt:message></button>
                </div>
            </form>
        </div>
    </div>
</div>

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
                        <input type="hidden" name="command" value="insertInvoiceBalance">
                        <input type="hidden" id="idRepairRequest" name="idRepairRequest" value="-1"/>
                        <input type="hidden" id="idUser" name="idUser" value="${user.id}"/>
                        <input type="hidden" name="operation" value="replenishment">
                        <div class="form-group">
                            <label for="ammountValue"><fmt:message key="ammount"></fmt:message></label>
                            <input type="number" step="0.01" name="ammount" class="form-control" id="ammountValue"><br>
                            <label style="color:red; visibility: hidden" id="errorIndicateAmmount"><fmt:message
                                    key="indicate_ammount"></fmt:message></label>
                        </div>
                        <button type="submit" onclick="return checkReplenishment()" class="btn btn-default"><fmt:message
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
