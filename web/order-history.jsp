<%-- 
    Document   : order-history
    Created on : Mar 23, 2021, 10:32:50 PM
    Author     : minhv
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet"  href="assets/css/bootstrap.min.css">
        <link rel="stylesheet"  href="assets/css/customize.css">
        <title>Order History Page</title>
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <h1 class="text-center">Order History</h1>

        <div class="container">
            <c:set var="fromDate" value="${param.txtFromDate}"/>
            <c:set var="toDate" value="${param.txtToDate}"/>

            <div class= "my-4">
                <form action="DispatchServlet" class="form-inline ml-auto"> 
                    <input type="date" name="txtFromDate" value="${fromDate}" class="form-control col-2"/>
                    <input type="date" name="txtToDate" value="${toDate}" class="form-control col-2"/>
                    <input type="submit" value="Search Order" name="btAction" class="btn btn-success col-2" style="margin-left: 1rem" /> 
                </form> 
            </div>

            <c:set var="listOrder" value="${requestScope.ORDER_LIST}"/>
            <c:set var="orderDetailMap" value="${requestScope.ORDER_DETAIL_MAP}"/>
            <c:if test="${not empty listOrder}">
                <c:forEach var="order" items="${listOrder}">
                    <label>OrderID: ${order.orderId}</label></br>
                    <label>OrderDate: ${order.orderDate}</label></br>
                    <label>UserId: ${order.userId}</label></br>
                    <table class="table text-center table-bordered">
                        <thead class="thead-light">
                            <tr>
                                <th scope="col">No.</th>
                                <th scope="col">Food Name</th>
                                <th scope="col">Price</th>
                                <th scope="col">Quantity</th>
                                <th scope="col">Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${orderDetailMap[order.orderId]}" varStatus="counter">
                                <tr>
                                    <td>
                                        ${counter.count}
                                    </td>
                                    <td>
                                        ${item.name}
                                    </td>
                                    <td>
                                        ${item.price}
                                    </td>
                                    <td>
                                        ${item.quantity}
                                    </td>
                                    <td>
                                        ${item.total} VND
                                    </td>
                                </tr>
                            </c:forEach> 
                            <tr class="alert-primary text-right" style="font-weight: bold">
                                <td scope="row" colspan="4">Cart Total:</td>
                                <td class="text-center" scope="row">${order.total} VND</td>
                            </tr>
                            <tr class="alert-warning text-right" style="font-weight: bold">
                                <td scope="row" colspan="4">
                                    Payment Method: 
                                </td>
                                <td class="text-center">
                                    ${order.methodId}
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </c:forEach>
            </c:if>
            <c:if test="${empty listOrder}">
                <h4 class="text-center my-5">
                    No Orders Found!!
                </h4>
            </c:if>
        </div>
    </body>
</html>
