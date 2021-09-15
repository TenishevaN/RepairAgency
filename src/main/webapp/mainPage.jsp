
<%@ include file="/WEB-INF/include/head.jspf" %>
<%@ include file="mainPageNavBarBlock.jsp" %>

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
    <div class="row content">
        <div class="col-sm-8 text-left">
            <h1>Welcome</h1>
            <p></p>
            <hr>
            <h3></h3>
            <p></p>
        </div>
        <div class="col-sm-2 sidenav">
            <div class="well">
                <p>ADS</p>
            </div>
            <div class="well">
                <p>ADS</p>
            </div>
        </div>
    </div>
</div>

<%@ include file="footerBlock.jsp" %>


<!-- Modal login-->
<div class="modal fade" id="insertModalLoginPage" role="dialog">
    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <h1></h1>

                    <form style="width:300px" action="controller" method="get">
                        <input name="command" type="hidden" value="login">
                        <div class="form-group">
                            <label for="login"><fmt:message key="login"></fmt:message></label>
                            <input name="login" value="admin"  class="form-control" id="login"><br>
                        </div>
                        <div class="form-group">
                            <label for="password"><fmt:message key="password"></fmt:message></label>
                            <input name="password" type="password" class="form-control" id="password"  value="1" >
                        </div>
                        <button type="submit" class="btn btn-default"><fmt:message key="login"></fmt:message></button>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
            </div>
        </div>
    </div>
</div>

<!-- Modal registration-->
<div class="modal fade" id="insertModalRegistrationPage" role="dialog">
    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><fmt:message key="registration"></fmt:message></h4>
            </div>
            <div class="modal-body">
                <div class="container">

                    <form style="width:300px" action="controller" method="post">
                        <input name="command" type="hidden" value="insertUser">
                        <div class="form-group">
                            <label for="login"><fmt:message key="login"></fmt:message></label>
                            <input name="login"  class="form-control" id="login"><br>
                        </div>
                        <div class="form-group">
                            <label for="password"><fmt:message key="password"></fmt:message></label>
                            <input name="password" type="password" class="form-control" id="password">
                        </div>
                        <div class="form-group">
                            <label for="login"><fmt:message key="name"></fmt:message></label>
                            <input name="name"  class="form-control" id="name"><br>
                        </div>
                        <div class="form-group">
                            <label for="login"><fmt:message key="email"></fmt:message></label>
                            <input name="email"  class="form-control" id="email"><br>
                        </div>
                        <div class="form-group">
                            <label for="login"><fmt:message key="login"></fmt:message></label>
                            <input name="login"  class="form-control" id="login"><br>
                        </div>
                        <button type="submit" class="btn btn-default"><fmt:message key="register"></fmt:message></button>
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
