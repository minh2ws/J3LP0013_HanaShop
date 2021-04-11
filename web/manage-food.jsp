<%-- 
    Document   : manage-food
    Created on : Jan 13, 2021, 5:27:44 PM
    Author     : minhv
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Food Page</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/customize.css">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <div class="container">
            <!--Search form-->
            <c:set var="searchValue" value="${param.txtSearch}"/>
            <c:set var="searchPrice" value="${param.cmbPrice}"/>
            <c:set var="searchCategory" value="${param.cmbCategory}"/>
            <c:set var="categoryList" value="${requestScope.CATEGORY_LIST}"/>
            <div class="my-4">
                <!--Search form-->
                <div class="my-4">
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
                            <input type="submit" value="Search" name="btAction" class="btn btn-success my-3"/>
                        </div>
                    </form>
                </div>
                <!--End search form-->

                <!--Display search result-->
                <c:set var="result" value="${requestScope.FOOD_LIST}"/>
                <c:set var="totalPage" value="${requestScope.TOTAL_PAGES}"/>

                <c:if test="${not empty result}">
                    <h3 class="text-center">Food</h3>
                    <div class="row justify-content-center">
                        <c:forEach var="item" items="${result}">
                            <div class="col-12 col-md-6 col-lg-4 my-3">
                                <div class="card-h100">
                                    <h5 class="card-header">${item.food.name}</h5>
                                    <div class="card-body">
                                        <!--Update food form-->
                                        <form action="DispatchServlet" id="updateFood${item.food.productId}" method="POST" class="form-group" enctype="multipart/form-data">
                                            <image src="DispatchServlet?file=${item.food.image}&btAction=Image" class="card-img-top"/>
                                            <div class="form-group">
                                                <span style="font-weight: bold">Name: </span>
                                                <input type="text" name="txtFoodName" value="${item.food.name}" placeholder="(eg 1 - 50 chars)" class="form-control" required/>
                                            </div>
                                            <div class="form-group">
                                                <span style="font-weight: bold">Description: </span>
                                                <input type="text" name="txtFoodDescription" value="${item.food.description}" placeholder="(eg 1 - 100 chars)" class="form-control" required/>
                                            </div>
                                            <div>
                                                <span style="font-weight: bold">Created date: </span> <span>${item.food.createOfDate}</span>
                                            </div>
                                            <div>
                                                <span style="font-weight: bold">Price: </span>
                                                <input type="number" name="txtFoodPrice" value="${item.food.price}" class="form-control" required min="1000" step="0.5"/>
                                            </div>
                                            <div class="form-group">
                                                <span style="font-weight: bold">Quantity: </span>
                                                <input type="number" name="txtFoodQuantity" value="${item.food.quantity}" class="form-control" required min ="0" step="1"/>
                                            </div>
                                            <div class="form-group">
                                                <span style="font-weight: bold">Choose an image: </span>
                                                <span>${item.food.image}</span>
                                                <input type="file" accept="image/*" name="image" class="form-control-file"/>
                                            </div>
                                            <div>
                                                <span style="font-weight: bold">Category: </span>
                                                <select name="cmbCategoryFood" class="form-control">
                                                    <c:forEach var="categoryItem" items="${categoryList}">
                                                        <option value="${categoryItem.cateId}"
                                                                <c:if test="${categoryItem.cateId eq item.food.cateId}">
                                                                    selected="true"
                                                                </c:if>>
                                                            ${categoryItem.categoryName}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <!--Hidden field for update Food-->
                                            <input type="hidden" name="productId" value="${item.food.productId}"/>
                                            <input type="hidden" name="oldImageLink" value="${item.food.image}" />
                                            <input type="hidden" name="txtSearch" value="${searchValue}"/>
                                            <input type="hidden" name="cmbCategory" value="${searchCategory}"/>
                                            <input type="hidden" name="cmbPrice" value="${searchPrice}"/>
                                            <input type="hidden" name="page" value="${param.page}" />
                                        </form>
                                        <!--End update food form-->
                                    </div>
                                    <div class="card-footer justify-content-center">
                                        <div class="row">
                                            <div class="col-sm-12 col-md-6 col-lg-6 text-left">
                                                <!--Update Food-->
                                                <input type="submit" form="updateFood${item.food.productId}" value="Update Food" name="btAction" class="btn btn-primary mt-3"/>
                                            </div>
                                            <!--Delete Food-->
                                            <div class="col-sm-12 col-md-6 col-lg-6 text-right">
                                                <c:if test="${item.food.status eq 'Active'}">
                                                    <a href="#updateStatus${item.food.productId}" class="delete" data-toggle="modal">
                                                        <button type="button" class="btn btn-success mt-3">Active</button>
                                                    </a>
                                                </c:if>
                                                <c:if test="${item.food.status eq 'Inactive'}">
                                                    <a href="#updateStatus${item.food.productId}" class="delete" data-toggle="modal">
                                                        <button type="button" class="btn btn-danger mt-3">Inactive</button>
                                                    </a>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                        <!--Pop up-->
                        <!--Delete Modal Car-->
                        <c:forEach var="item" items="${result}">
                            <div class="modal fade" id="updateStatus${item.food.productId}" role="dialog" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <form action="DispatchServlet" method="POST">
                                            <div class="modal-header">
                                                <h5 class="modal-title">Update Status Product</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <p>Do you want to 
                                                    <strong></strong>
                                                    <c:if test="${item.food.status eq 'Active'}">
                                                        <strong>Inactive</strong>
                                                    </c:if>
                                                    <c:if test="${item.food.status eq 'Inactive'}">
                                                        <strong>Active</strong>
                                                    </c:if> 
                                                    <strong${item.food.name}</strong> ?</p>
                                            </div>
                                            <div class="modal-footer">
                                                <input type="hidden" name="productId" value="${item.food.productId}" />
                                                <input type="hidden" name="status" value="${item.food.status}" />
                                                <input type="hidden" name="txtSearch" value="${searchValue}" />
                                                <input type="hidden" name="cmbCategory" value="${searchCategory}" />
                                                <input type="hidden" name="cmbPrice" value="${searchPrice}" />
                                                <input type="submit" class="btn btn-secondary" data-dismiss="modal" value="Cancel" />
                                                <input type="submit" value="Update Status" name="btAction" class="btn btn-primary"/>
                                            </div>
                                        </form>
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
