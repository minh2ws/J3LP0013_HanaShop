<%-- 
    Document   : view-cart
    Created on : Mar 21, 2021, 10:42:04 PM
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
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <title>Cart Page</title>
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <c:set var="cart" value="${requestScope.CUSTOMER_CART}"/>
        <c:set var="error" value="${requestScope.CART_ERRORS}"/>
        <c:set var="listPayment" value="${requestScope.PAYMENT_METHOD}"/>
        <c:if test="${not empty cart}">
            <div class="text-center mt-4">
                <h3>Cart</h3>
            </div>
            <div class="shopping-cart">
                <div class="px-4 px-lg-0">
                    <div class="pb-5">
                        <div class="container">
                            <div class="row">
                                <div class="col-lg-12 p-5 bg-white rounded shadow-sm mb-5">
                                    <!--Car list-->
                                    <div class="table-responsive">
                                        <table class="table table-bordered">
                                            <thead class="thead-dark">
                                                <tr>
                                                    <th scope="col" class="border-0 bg-dark">
                                                        <div class="p-2 px-3 text-uppercase">Food Name</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-dark">
                                                        <div class="py-2 text-uppercase">Quantity</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-dark">
                                                        <div class="py-2 text-uppercase">Amount</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-dark">
                                                        <div class="py-2 text-uppercase">Price</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-dark">
                                                        <div class="py-2 text-uppercase">Total</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-dark">
                                                        <div class="py-2 text-uppercase">Delete</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-dark">
                                                        <div class="py-2 text-uppercase">Update</div>
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="item" items="${cart.listItem}">
                                                <form action="DispatchServlet" method="POST">
                                                    <tr>
                                                        <td class="align-middle">
                                                            <strong>${item.food.name}</strong>
                                                            <img src="DispatchServlet?file=${item.food.image}&btAction=Image" width="150" class="img-fluid rounded shadow-sm" />
                                                        </td>
                                                        <td class="align-middle">
                                                            <strong>${item.food.quantity}</strong>
                                                        </td>
                                                        <td class="align-middle">
                                                            <input type="number" class="text-center rounded-pill border" name="txtQuantity" value="${item.quantity}" min="1" step="1" required/>
                                                        </td>
                                                        <td class="align-middle">
                                                            <strong>${item.food.price}</strong>
                                                        </td>
                                                        <td class="align-middle">
                                                            <strong>${item.total}</strong>
                                                        </td>
                                                        <td class="align-middle">
                                                            <a href="#delete${item.food.productId}" class="delete" data-toggle="modal">
                                                                <button type="button" class="btn btn-primary">Delete</button>
                                                            </a>
                                                        </td>
                                                        <td class="align-middle">
                                                            <input type="hidden" name="productId" value="${item.food.productId}"/>
                                                            <input type="submit" value="Update" name="btAction" class="btn btn-success"/>
                                                        </td>
                                                    </tr>
                                                </form>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <!--End car table-->
                                </div>
                            </div>

                            <!--Checkout zone-->
                            <div class="card-deck">
                                <div class="card">
                                    <c:set var="total" value="${requestScope.TOTAL_PRICE}"/>
                                    <div class="card-header"  style="font-weight: bold">Payment</div>
                                    <div class="card-body">
                                        <ul class="list-unstyled mb-4">
                                            <li class="d-flex justify-content-between py-3 border-bottom">
                                                <strong class="text-muted">Total</strong>
                                                <strong>${total} </strong>
                                            </li>
                                        </ul>
                                        <form action="DispatchServlet" method="POST" class="text-right py-4">
                                            <select name="cmbPaymentMethod" class="form-control">
                                                <c:forEach var="payment" items="${listPayment}">
                                                    <option value="${payment.methodId}">${payment.methodName}</option>
                                                </c:forEach>
                                            </select>
                                            <input type="submit" value="Checkout" name="btAction" class="btn btn-secondary px-4 rounded-pill"/>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <!--End checkout zone-->
                        </div>
                    </div> 
                </div>
            </div>
            <!--End checkout page-->

            <!--Pop up-->
            <!--Delete Modal Car-->
            <c:forEach var="item" items="${cart.listItem}">
                <div class="modal fade" id="delete${item.food.productId}" role="dialog" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <form action="DispatchServlet" method="POST">
                                <div class="modal-header">
                                    <h5 class="modal-title">Delete Food From Cart</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <p>Do you want to delete <strong>${item.food.name}</strong>?</p>
                                </div>
                                <div class="modal-footer">
                                    <input type="hidden" name="productId" value="${item.food.productId}" />
                                    <input type="submit" class="btn btn-secondary" data-dismiss="modal" value="Cancel" />
                                    <input type="submit" name="btAction" class="btn btn-primary" value="Delete" />
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>

        <c:if test="${empty cart}">
            <h1>Your cart is empty!</h1>
        </c:if>

        <!--Check field validation Modal-->
        <c:if test="${not empty error}">
            <!--JavaScript awake check-->
            <script>
                $(document).ready(function () {
                    $("#errorModal").modal();
                });
            </script>

            <!--Check Modal-->
            <div class="modal fade" id="errorModal" role="dialog" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <img src="assets/image/images.png" alt="warning" width="40" height="40"><br/>
                            <h4 class="modal-title">Warning</h4>
                            <button type="button" class="close" data-dismiss="modal">×</button>
                        </div>
                        <div class="modal-body">
                            <c:if test="${not empty error.quantityError}">
                                <p class="text-danger">${error.quantityError}</p>
                            </c:if>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>

        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/script.js"></script>
    </body>
</html>
