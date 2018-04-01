<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>

<head>
    <title>Title</title>
</head>

<body>

<jsp:include page="/WEB-INF/common/header.jsp"/>
<script src="${pageContext.request.contextPath}/resource/js/manage.js"></script>

<div class="container">

    <div class="row clearfix" style="padding-top: 20%">
        <button id="manage_fresh" type="button" class="btn btn-success btn-default">Fresh</button>
    </div>

    <div class="row clearfix">
        <div class="col-md-12 column">
            <form role="form" class="form-inline" id="start_form">

                <div class="form-group">
                    <label>Amount</label><input type="text" class="form-control" id="start_amount" />
                </div>

                <div class="checkbox">
                    <label><input type="checkbox" id="start_type" />Using TMGI Pool(default TMGI)</label>
                </div>

                <button class="btn btn-default" id="start_submit">Submit</button>

            </form>
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-12 column">
            <span><strong>${fn:length(applicationScope.startingItems)}</strong> Sessions are being starting</span>
        </div>
        <div class="col-md-12 column">
            <span><strong>0</strong> items failed</span>
        </div>
    </div>

    <div class="row clearfix" style="margin-top: 100px">

        <div class="col-md-12 column">

            <h2 class="panel-title">
                Active Sessions
            </h2>

            <table class="table" id="live_status_table">
                <tr >
                    <th>Session ID</th>
                    <th>Version</th>
                    <th>TMGIPool</th>
                    <th>TMGI</th>
                    <th>StartTime</th>
                    <th>StopTime</th>
                    <th>Action</th>
                </tr>

                <tbody id="live_status_tbody">

                <c:forEach  items="${applicationScope.startedItems}"  var="entry">
                    <tr>
                        <td >  <strong> <c:out value="${entry.value.deliverySessionId}"/> </strong> </td>
                        <td >  <c:out value="${entry.value.version}"/> </td>
                        <td >  <c:out value="${entry.value.tMGIPool}"/> </td>
                        <td >  <c:out value="${entry.value.tMGI}"/> </td>
                        <td >  <c:out value="${entry.value.startTime}"/> </td>
                        <td >  <c:out value="${entry.value.stopTime}"/> </td>
                        <td >  <button class="stop_session_buttons">Stop Session</button> </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>

        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-12 column">
            <a href="/session/stopAll">
            <button type="button" class="btn btn-lg btn-danger">Stop all sessions and Exit!</button>
            </a>
        </div>
    </div>

</div>


<jsp:include page="/WEB-INF/common/footer.jsp"/>

</body>