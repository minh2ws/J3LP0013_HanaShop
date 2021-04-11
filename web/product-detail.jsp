<%-- 
    Document   : product-detail
    Created on : Mar 21, 2021, 6:38:56 PM
    Author     : minhv
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Food Page</title>
        <link rel="stylesheet"  href="assets/css/bootstrap.min.css">
        <link rel="stylesheet"  href="assets/css/customize.css">
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <!--Body-->
        <div class="container-fluid my-4">
            <div class="col-sm-12">
                <div class="container">
                    <c:set var="item" value="${requestScope.FOOD}"/>
                    <div class="card">
                        <div class="row">
                            <aside class="col-sm-2 col-md-3 col-lg-4">
                                <img src="DispatchServlet?file=${item.food.image}&btAction=Image" class="img-fluid rounded shadow-sm" />
                            </aside>
                            <aside class="col-sm-7">
                                <article class="card-body p-5">
                                    <h3 class="tilte mb-3">${item.food.name}</h3>

                                    <p class="price-detail-wrap">
                                        <span class="price h3 text-danger"> 
                                            <span class="currency">Price: </span><span class="num">${item.food.price}</span>
                                        </span>
                                    </p>
                                    <div>
                                        <span style="font-weight: bold">Category: </span> ${item.category}
                                    </div>
                                    <div>
                                        <span style="font-weight: bold">Quantity: </span> ${item.food.quantity}
                                    </div>
                                    <dl class="item-property">
                                        <span style="font-weight: bold">Description: </span> <span>${item.food.description} </span>
                                    </dl>
                                    <a href="DispatchServlet?productId=${item.food.productId}&btAction=AddToCart" class="btn btn-lg btn-outline-primary text-uppercase"> <i class="fa fa-shopping-cart"></i> Add to cart </a>
                                </article>
                            </aside>
                        </div>
                    </div>
                    <c:set var="resultSuggest" value="${requestScope.FOOD_SUGGEST_LIST}"/>
                    <div class="my-4">
                        <h3 class="text-center">Frequently Bought Together</h3>
                        <div class="row justify-content-center">
                            <c:forEach var="item" items="${resultSuggest}">
                                <div class="col-12 col-md-6 col-lg-4 my-3">
                                    <div class="card-h100  bg-light">
                                        <div class="card-header">
                                            <h5>${item.food.name}</h5>
                                        </div>
                                        <div class="card-body">
                                            <image src="DispatchServlet?file=${item.food.image}&btAction=Image" class="card-img-top"/>
                                            <div>
                                                <span style="font-weight: bold">Description: </span>
                                                ${item.food.description}
                                            </div>
                                            <div>
                                                <span style="font-weight: bold">Category: </span>
                                                ${item.category}
                                            </div>
                                            <div>
                                                <span style="font-weight: bold">Price: </span>
                                                ${item.food.price}
                                            </div>
                                        </div>
                                        <div class="card-footer">
                                            <div class="text-center">
                                                <form action="DispatchServlet" method="POST">
                                                    <input type="hidden" name="productId" value="${item.food.productId}"/>
                                                    <input type="hidden" name="txtSearch" value="${searchValue}"/>
                                                    <input type="hidden" name="cmbCategory" value="${searchCategory}"/>
                                                    <input type="hidden" name="cmbPrice" value="${searchPrice}"/>
                                                    <input type="hidden" name="page" value="${param.page}" />
                                                    <input type="submit" value="View Detail" name="btAction" class="btn btn-success"/>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
