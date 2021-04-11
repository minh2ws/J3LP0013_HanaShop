<%-- 
    Document   : create-food
    Created on : Jan 18, 2021, 2:21:53 AM
    Author     : minhv
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/customize.css">
        <title>Create Food Page</title>
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <c:set var="categoryList" value="${requestScope.CATEGORY_LIST}"/>
        <h3 class="text-center">Food</h3>
        <div class="row justify-content-center">
            <div class="col-12 col-md-6 col-lg-4 my-3">
                <div class="card-h100">
                    <div class="card-body">
                        <!--Create food form-->
                        <form action="DispatchServlet" id="CreateFood" method="POST" class="form-group" enctype="multipart/form-data">
                            <div class="form-group">
                                <span style="font-weight: bold">Name: </span>
                                <input type="text" name="txtFoodName" value="" placeholder="(eg 1 - 50 chars)" class="form-control" required/>
                            </div>
                            <div class="form-group">
                                <span style="font-weight: bold">Description: </span>
                                <input type="text" name="txtFoodDescription" value="" placeholder="(eg 1 - 100 chars)" class="form-control" required/>
                            </div>
                            <div>
                                <span style="font-weight: bold">Price: </span>
                                <input type="number" name="txtFoodPrice" value="" class="form-control" required min="1000" step="0.5"/>
                            </div>
                            <div class="form-group">
                                <span style="font-weight: bold">Quantity: </span>
                                <input type="number" name="txtFoodQuantity" value="" class="form-control" required min ="0" step="1"/>
                            </div>
                            <div class="form-group">
                                <span style="font-weight: bold">Choose an image: </span>
                                <span>${item.food.image}</span>
                                <input type="file" accept="image/*" name="image" class="form-control-file" required/>
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
                        </form>
                        <!--End create food form-->
                    </div>
                    <div class="card-footer justify-content-center">
                        <div class="row">
                            <div class="col-sm-12 col-md-6 col-lg-6 text-left">
                                <!--Update Food-->
                                <input type="submit" form="CreateFood" value="Create Food" name="btAction" class="btn btn-primary mt-3"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
