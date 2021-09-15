
<%@ include file="/WEB-INF/include/head.jspf" %>
<%@ taglib prefix="userFieldRight" uri="/WEB-INF/tlib/userFieldRight.tld" %>
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

<div class="custom-format" class="container">
    <div class="row">
        <div class="col-md-6 mx-auto text-center">
            <h2> Repair request</h2>
            <div class="divider bg-primary mx-auto"></div>
        </div>
    </div>
    <div class="row mt-5">
        <div class="col-md-8 mx-auto">
            <form action="controller" method="post">
                <input name="command" type="hidden" value="updateRepairRequest">
                <input type="hidden" id="idRepairRequest" name="idRepairRequest"
                       value="${repairRequest.id}"/>
                <input type="hidden" id="master_name" name="master_name"
                       value="${repairRequest.masterName}"/>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="control-label col-xs-4">Description:</label>
                            <userFieldRight:description descriptionText="${repairRequest.description}"
                                                        nameRole="${role}"/>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="control-label col-xs-4">Status:</label>
                            <userFieldRight:status idStatus="${repairRequest.statusId}" nameRole="${role}"  currentLocale = "${currentLocale}"/>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-xs-4">Master:</label>
                            <userFieldRight:master idMaster="${repairRequest.masterId}" nameRole="${role}"/>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-xs-4">Cost:</label>
                            <userFieldRight:cost costValue="${repairRequest.cost}" nameRole="${role}"/>
                        </div>
                    </div>
                </div>
                <div class="text-left mt-3">
                    <button type="submit" class="btn btn-default">Update</button>
                </div>
            </form>

            <c:set var="role" value="${fn:toLowerCase(role)}"></c:set>
            <c:if test="${role eq 'user'}">

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <form action="controller" method="post">
                                <input name="command" type="hidden" value="insertReview">
                                <input type="hidden" id="idRepairRequest" name="idRepairRequest"
                                       value="${repairRequest.id}"/>
                                <input type="hidden" id="role" name="role" value="${role}"/>
                                <label class="control-label col-xs-4">Write a review:</label>

                                <div class="form-group" value="Comment">
                                            <textarea rows="5" class="form-control" name="comment"
                                                      placeholder="What are you looking for?">  Write your comment.. </textarea>
                                </div>
                                <div class="text-left mt-3">
                                    <input type="hidden" id="idRepairRequest" name="idRepairRequest"
                                           value="${repairRequest.id}"/>
                                    <input type="hidden" id="role" name="role" value="${role}"/>
                                    <button type="submit" class="btn btn-default">Send</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="col-md-6">

                        <div class="reviews-list-item">
                            <userFieldRight:listReviews idRepairRequest="${repairRequest.id}"/>
                        </div>

                    </div>
                </div>

            </c:if>
            <c:if test="${role != 'user'}">
                <div class="row mt-5">
                    <div class="col-md-12 mx-auto">
                        <form _lpchecked="1">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <div class="reviews-list-item">
                                            <div class="reviews-list-item-question">
                                                <userFieldRight:listReviews
                                                        idRepairRequest="${repairRequest.id}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </c:if>
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
