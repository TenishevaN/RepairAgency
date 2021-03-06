<%@ include file="/WEB-INF/include/head.jspf" %>
<%@ taglib prefix="userFieldRight" uri="/WEB-INF/tlib/userFieldRight.tld" %>
<%@ include file="mainPageNavBarBlock.jsp" %>
<%@ taglib prefix="tagfile" tagdir="/WEB-INF/tags" %>


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

<div class="custom-format" style="height:800px; margin-left: 140px; padding-top: 20px; padding-left: 100px"
     class="container">
    <div class="row">
        <div class="col-md-6 mx-auto text-center">
            <h2><fmt:message key="repair_request"></fmt:message></h2>
            <div style="height:20px"></div>
        </div>
    </div>

    <div>
        <div class="col-md-20 mx-auto">
            <form action="controller" method="post">
                <input type="hidden" name="command" value="updateRepairRequest">
                <input type="hidden" id="idRepairRequest" name="idRepairRequest"
                       value="${repairRequest.id}"/>
                <input type="hidden" id="master_name" name="master_name"
                       value="${repairRequest.masterName}"/>

                <div class="row">
                    <div class="col-md-6">
                        <div style="width:500px; height:250px" class="form-group">
                            <label class="control-label col-xs-4"><fmt:message key="description"></fmt:message>:</label>
                            <userFieldRight:description descriptionText="${repairRequest.description}"
                                                        nameRole="${role}"/>
                            <label style="color:red; visibility: hidden" id="errorDescription"><fmt:message
                                    key="error_description"></fmt:message></label>
                        </div>
                        <div>
                            <button type="submit" class="btn btn-default" onclick="return handleDescriptionChange()"><fmt:message
                                    key="update"></fmt:message></button>
                        </div>
                    </div>
                    <div class="col-md-6">

                        <tagfile:balanceOwed balance_owed="${balance_owed}"
                                             role="${fn:toLowerCase(role)}"></tagfile:balanceOwed>

                        <div>
                            <label><fmt:message key="status"></fmt:message>:</label>
                            <userFieldRight:status idStatus="${repairRequest.statusId}" nameRole="${role}"
                                                   currentLocale="${currentLocale}" area="card"/>
                        </div>
                        <div style="height:20px"></div>
                        <div>
                            <label><fmt:message key="master"></fmt:message>:</label>
                            <userFieldRight:master idMaster="${repairRequest.masterId}" nameRole="${role}" listMaster = "${listMasters}"
                                                   currentLocale="${currentLocale}" area="card"/>
                        </div>
                        <div style="height:20px"></div>
                        <div>
                            <label><fmt:message key="cost"></fmt:message>:</label>
                            <userFieldRight:cost costValue="${cost}" nameRole="${role}"/>
                        </div>
                    </div>
                </div>
            </form>
            </br>
            <c:set var="role" value="${fn:toLowerCase(role)}"></c:set>
            <c:set var="statusId" value="${repairRequest.statusId}"></c:set>
            <c:if test="${(role eq 'user') && (statusId == 5)}">

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="insertReview">
                                <input type="hidden" id="idRepairRequest" name="idRepairRequest"
                                       value="${repairRequest.id}"/>
                                <input type="hidden" id="role" name="role" value="${role}"/>
                                <label><fmt:message key="write_review"></fmt:message>:</label>

                                <div style="width:500px" class="form-group" value="Comment">
                                            <textarea rows="5" class="form-control" name="comment" id="commentValue">  </textarea>
                                    <label style="color:red; visibility: hidden" id="errorComment"><fmt:message
                                            key="error_description"></fmt:message></label>
                                </div>
                                <div class="text-left mt-3">
                                    <input type="hidden" id="idRepairRequest" name="idRepairRequest"
                                           value="${repairRequest.id}"/>
                                    <input type="hidden" id="role" name="role" value="${role}"/>
                                    <button type="submit" class="btn btn-default" onclick="return handleCommentChange()"><fmt:message
                                            key="send"></fmt:message></button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="reviews-list-item">
                            <userFieldRight:listReviews idRepairRequest="${repairRequest.id}" currentLocale="${currentLocale}"/>
                        </div>
                    </div>
                </div>

            </c:if>
            <c:if test="${role != 'user'}">
                <div class="row mt-5">
                    <div class="col-md-10 mx-auto">
                        <form _lpchecked="1">
                            <div class="row">
                                <div class="col-md-10">
                                    <div class="form-group">
                                        <div class="reviews-list-item">
                                            <div class="reviews-list-item-question">
                                                <userFieldRight:listReviews
                                                        idRepairRequest="${repairRequest.id}"  currentLocale="${currentLocale}"/>
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


<%@ include file="footerBlock.jsp" %>

<!-- Modal payment-->
<div class="modal fade" id="insertPaymentPage" role="dialog">

    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><fmt:message key="payment"></fmt:message></h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <form style="width:300px" action="controller" method="post">
                        <input type="hidden" name="command" value="insertInvoiceBalance">
                        <input type="hidden" id="idRepairRequest" name="idRepairRequest" value="${repairRequest.id}"/>
                        <input type="hidden" id="total" name="total" value="${total}"/>
                        <input type="hidden" id="idUser" name="idUser" value="${repairRequest.userId}"/>
                        <input type="hidden" name="operation" value="payment">
                        <div>
                            <label for="ammountValue"><fmt:message key="ammount"></fmt:message></label>
                            <input type="number" step="0.01" name="ammount" class="form-control" id="ammountValue"><br>
                            <div class="form-inline">
                            <label style="color:red; visibility: hidden" id="errorNotEnoughFundsToPay"><fmt:message
                                    key="not_enough_funds_to_pay"></fmt:message></label>
                            <label style="color:red; visibility: hidden" id="errorIndicateAmmount"><fmt:message
                                    key="indicate_ammount"></fmt:message></label>
                            </div>
                        </div>
                        <div>
                        <button type="submit" onclick="return checkPayment()"
                                class="btn btn-default"><fmt:message
                                key="pay"></fmt:message></button>
                        </div>
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
