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

<body>

<div class="container">
    <div class="row">
        <div class="col-md-6 mx-auto text-center">
            <h2> User</h2>
            <div class="divider bg-primary mx-auto"></div>
        </div>
    </div>
    <div class="row mt-5">
        <div class="col-md-8 mx-auto">
            <form action="controller" method="post">
                <input name="command" type="hidden" value="updateCardUser">
                <input type="hidden" id="id" name="id"
                       value="${user.id}"/>
                <div class="form-group">
                    <label for="login"><fmt:message key="login"></fmt:message></label>
                    <input type="text" name="login" class="form-control" id="login" value = "${user.login}"/>
                </div>
                <div class="form-group">
                    <label for="login"><fmt:message key="name"></fmt:message></label>
                    <input type="text" name="name" class="form-control" id="name" value = "${user.name}"/> <br>
                </div>
                <div class="form-group">
                    <label for="login"><fmt:message key="email"></fmt:message></label>
                    <input type="text" name="email" class="form-control" id="email" value = "${user.email}"/> <br>
                </div>
                <div class="form-group">
                    <label class="control-label col-xs-4">Role:</label>
                    <userFieldRight:role id="${user.roleId}"/>
                </div>

                <div class="text-left mt-3">
                    <button type="submit" class="btn btn-default"><fmt:message key="update"></fmt:message></button>
                </div>
            </form>
        </div>
    </div>
</div>
<p></p>
<hr>
<h3></h3>
<p></p>

<jsp:include page="footerBlock.jsp" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

</body>
</html>
