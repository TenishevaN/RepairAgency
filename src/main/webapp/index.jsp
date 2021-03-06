<%@ include file="/WEB-INF/include/head.jspf" %>


<html>
<head>
    <title><fmt:message key="repair_agency"></fmt:message></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="js/java_script_dynamic.js"></script>
</head>
<body>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#"><fmt:message key="home"></fmt:message></a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="custom-margin-1">
                    <a href="<c:url value="changeLocale.jsp">
                        <c:param name="action" value="post"/>
                        <c:param name="locale" value="uk"/>
                        <c:param name="page" value="index.jsp"/>
                        </c:url>">UA</a>
                </li>
                <li>
                    <a href="<c:url value="changeLocale.jsp">
                        <c:param name="action" value="post"/>
                        <c:param name="locale" value="ru"/>
                        <c:param name="page" value="index.jsp"/>
                        </c:url>">RU</a>
                </li>
                <li>
                    <a href="<c:url value="changeLocale.jsp">
                        <c:param name="action" value="post"/>
                        <c:param name="locale" value="en"/>
                        <c:param name="page" value="index.jsp"/>
                        </c:url>">EN</a>
                </li>
                <li><a href=#insertModalLoginPage" data-toggle="modal"
                       data-target="#insertModalRegistrationPage"><span
                        class="glyphicon glyphicon-registration-mark"></span> <fmt:message
                        key="registration"></fmt:message></a></li>
                <li><a href=#insertModalLoginPage" data-toggle="modal" data-target="#insertModalLoginPage"><span
                        class="glyphicon glyphicon-log-in"></span> <fmt:message key="sign_in"></fmt:message></a>
                </li>
            </ul>
        </div>
    </div>
</nav>

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
                        <input type="hidden" name="command" value="login">
                        <div class="form-group">
                            <label for="login"><fmt:message key="login"></fmt:message></label>
                            <input type="text" name="login" value="manager" class="form-control" id="login"><br>
                        </div>
                        <div class="form-group">
                            <label for="password"><fmt:message key="password"></fmt:message></label>
                            <input type="password" name="password" class="form-control" id="password" value="1">
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
                        <input type="hidden" name="command" value="insertUser">
                        <div class="form-group">
                            <label for="login"><fmt:message key="login"></fmt:message></label>
                            <input type="text" name="login" class="form-control" id="loginValue"><br>
                            <div style="color:red; visibility: hidden" id="errorLogin"><fmt:message
                                    key="errorLogin"></fmt:message></div>
                        </div>
                        <div class="form-group">
                            <label for="password"><fmt:message key="password"></fmt:message></label>
                            <input type="password" name="password" id="passcode" class="form-control">
                            <div style="color:red; visibility: hidden" id="errorPassword"><fmt:message
                                    key="errorPassword"></fmt:message></div>
                        </div>
                        <div class="form-group">
                            <label for="login"><fmt:message key="name"></fmt:message></label>
                            <input type="text" name="name" class="form-control" id="nameValue"><br>
                            <div style="color:red; visibility: hidden" id="errorName"><fmt:message
                                    key="errorName"></fmt:message></div>
                        </div>
                        <div class="form-group">
                            <label for="email"><fmt:message key="email"></fmt:message></label>
                            <input type="email" class="form-control" name="email" id="email" value = "servicemailtest2021@gmail.com"><br>
                            <div style="color:red; visibility: hidden" id="errorEmail" ><fmt:message
                                    key="errorEmail"></fmt:message></div>
                        </div>
                        <button type="submit" onclick="return handleSubmitRegistrationUserForm()"
                                class="btn btn-default"><fmt:message
                                key="register"></fmt:message></button>
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
