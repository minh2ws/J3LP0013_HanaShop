function validateQuantity(productId) {
    var quantityFood = document.getElementById("quantityFood" + productId).value;
    var quantityUpdate = document.getElementById("txtQuantity" + productId).value;
    
    if (parseInt(quantityUpdate, 10) > parseInt(quantityFood, 10)) {
        alert("Only have " + quantityFood + " left");
        return false;
    }
    return true;
}