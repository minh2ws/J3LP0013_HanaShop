<%-- 
    Document   : home
    Created on : Jan 13, 2021, 5:26:01 PM
    Author     : minhv
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link rel="stylesheet"  href="assets/css/bootstrap.min.css">
        <link rel="stylesheet"  href="assets/css/customize.css">
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <div class="container">
            <!--Search form-->
            <c:set var="searchValue" value="${param.txtSearch}"/>
            <c:set var="searchPrice" value="${param.cmbPrice}"/>
            <c:set var="searchCategory" value="${param.cmbCategory}"/>
            <c:set var="categoryList" value="${requestScope.CATEGORY_LIST}"/>
            <c:set var="resultSuggest" value="${requestScope.FOOD_SUGGEST_LIST}"/>

            <div class="my-4">
                <div class="row justify-content-center">
                    <div class="col-12 col-md-6 col-lg-4 my-3">
                        <div class="card">
                            <div class="card-header bg-info text-white text-uppercase">
                                <span style="font-weight: bold">Search Food</span>
                            </div>
                            <div class="card-body">
                                <form action="DispatchServlet" class="form-group">
                                    <input type="text" name="txtSearch" value="${searchValue}" class="form-control" placeholder="Search food..."/>
                                    <label>Price Range (VND)</label>
                                    <select name="cmbPrice" class="form-control">
                                        <option></option>
                                        <option <c:if test="${searchPrice eq '0-20000'}"> selected="true" </c:if>>0-20000</option>
                                        <option <c:if test="${searchPrice eq '20000-40000'}"> selected="true" </c:if>>20000-40000</option>
                                        <option <c:if test="${searchPrice eq '40000-60000'}"> selected="true" </c:if>>40000-60000</option>
                                        <option <c:if test="${searchPrice eq '60000-80000'}"> selected="true" </c:if>>60000-80000</option>
                                        <option <c:if test="${searchPrice eq '80000-100000'}"> selected="true" </c:if>>80000-100000</option>
                                        <option <c:if test="${searchPrice eq '>100000'}"> selected="true" </c:if>>>100000</option>
                                        </select>
                                        <lable>Category</lable>
                                        <select name="cmbCategory" class="form-control">
                                            <option></option>
                                        <c:forEach var="item" items="${categoryList}">
                                            <option value="${item.cateId}"
                                                    <c:if test="${item.cateId eq searchCategory}">
                                                        selected="true"
                                                    </c:if>>
                                                ${item.categoryName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" name="page" value="1" />
                                    <div class="text-right">
                                        <input type="submit" value="Search" name="btAction" lass="btn btn-success my-3"/>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <c:forEach var="item" items="${resultSuggest}">
                        <div class="col-12 col-md-6 col-lg-4 my-3">
                            <h3 class="text-center">Best Seller</h3>
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

            <!--Display search result-->
            <c:set var="result" value="${requestScope.FOOD_LIST}"/>
            <c:set var="totalPage" value="${requestScope.TOTAL_PAGES}"/>

            <c:if test="${not empty result}">
                <h3 class="text-center">Food</h3>
                <div class="row justify-content-center">
                    <c:forEach var="item" items="${result}">
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
                <!--get current page-->
                <c:set var="curPage" value="${requestScope.LOAD_DEFAULT_PAGE}"/>
                <c:if test="${empty curPage}">
                    <c:set var="curPage" value="${param.page}"/>
                </c:if>
                <!--Page indicator-->
                <nav>
                    <ul class="pagination justify-content-center mt-4">
                        <c:forEach var="page" begin="1" end="${totalPage}" step="1">
                            <c:set var="urlPaging" value="DispatchServlet?txtSearch=${searchValue}&cmbPrice=${searchPrice}&cmbCategory=${searchCategory}&page=${page}&btAction=Search"/>
                            <c:if test="${curPage eq page}">
                                <li class="page-item active">
                                    <a class="page-link" href="${urlPaging}">${page}</a>
                                </li>
                            </c:if>
                            <c:if test="${curPage ne page}">
                                <li class="page-item">
                                    <a class="page-link" href="${urlPaging}">${page}</a>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </nav>
            </c:if>
            <c:if test="${empty result}">
                <h2 class="text-center">Not food found!!!</h2>
            </c:if>
        </div>
    </div>
    <script src="assets/js/bootstrap.min.js"></script>
</body>
</html>
