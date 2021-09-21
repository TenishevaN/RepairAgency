<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li class="active"><a href="controller?command=openCardUser&id="${user.id}"><fmt:message
                        key="profile"></fmt:message></a></li>
                <li><a href="controller?command=listRequests"><fmt:message key="repair_requests"></fmt:message></a></li>
                <c:set var="role" value="${fn:toLowerCase(role)}"></c:set>
                <c:if test="${role != 'user'}">
                    <li><a href="controller?command=listUsers""><fmt:message key="users"></fmt:message></a></li>
                    <li><a href="controller?command=reports"><fmt:message key="reports"></fmt:message></a></li>
                    <li><a href="#"><fmt:message key="service"></fmt:message></a></li>
                </c:if>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="<c:url value="changeLocale.jsp">
                        <c:param name="action" value="post"/>
                        <c:param name="locale" value="uk"/>
                        <c:param name="page" value="index.jsp"/>
                        </c:url>">UK</a>
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

                <li><a href="index.jsp"><span class="glyphicon glyphicon-log-out"></span><fmt:message
                        key="log_out"></fmt:message></a></li>
            </ul>
        </div>
    </div>
</nav>


