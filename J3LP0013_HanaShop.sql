CREATE DATABASE J3LP0013_HanaShop

USE J3LP0013_HanaShop

CREATE TABLE tblUsers (
userId VARCHAR(100) NOT NULL PRIMARY KEY,
password VARCHAR(50) NOT NULL,
fullname NVARCHAR(50) NOT NULL,
role BIT NOT NULL)

ALTER TABLE tblUsers
ALTER COLUMN fullname NVARCHAR(50) NOT NULL

ALTER TABLE tblUsers
ALTER COLUMN userId VARCHAR(100) NOT NULL

ALTER TABLE tblUsers
DROP COLUMN address

CREATE TABLE tblCategory(
cateId NVARCHAR(10) PRIMARY KEY,
categoryName NVARCHAR(50) NOT NULL)

CREATE TABLE tblProducts(
productId INT IDENTITY(1,1) PRIMARY KEY,
name NVARCHAR(50) NOT NULL,
status BIT NOT NULL,
quantity INT NOT NULL,
image VARCHAR(100) NOT NULL,
description NVARCHAR(200) NOT NULL,
price REAL NOT NULL,
createOfDate DATE NOT NULL,
cateId NVARCHAR(10) FOREIGN KEY REFERENCES tblCategory(cateId) NOT NULL)

ALTER TABLE tblProducts
ALTER COLUMN cateId NVARCHAR(10) NOT NULL

CREATE TABLE tblLog(
	logId INT IDENTITY(1,1) PRIMARY KEY,
	productId INT FOREIGN KEY REFERENCES tblProducts(productId) NOT NULL,
	userId VARCHAR(100) FOREIGN KEY REFERENCES tblUsers(userId) NOT NULL,
	updateDate DATETIME DEFAULT GETDATE() NOT NULL
)

DROP TABLE tblLog

SELECT cateId, categoryName
FROM tblCategory

SELECT productId, name, quantity, image, description, price, createOfDate, status, tblCategory.cateId, categoryName 
FROM tblProducts, tblCategory
WHERE tblProducts.cateId = tblCategory.cateId
AND quantity > 0 AND status = 'Active'
GROUP BY productId, name, quantity, image, description, price, createOfDate, status, tblCategory.cateId, categoryName
ORDER BY createOfDate DESC
OFFSET 0 ROWS
FETCH NEXT 20 ROWS ONLY

SELECT COUNT(productId) as total
FROM tblProducts
WHERE quantity > 0 AND status = 'True'

INSERT INTO tblLog(productId, userId) VALUES(1, admin)

CREATE TRIGGER updateQuantityInTblProduct ON tblOrderDetail AFTER INSERT AS
BEGIN
	UPDATE tblProducts
	SET tblProducts.quantity = tblProducts.quantity - (
		SELECT inserted.quantity
		FROM inserted
		WHERE productId = tblProducts.productId)
	FROM tblProducts
	JOIN inserted ON tblProducts.productId = inserted.productId
END

SELECT orderId, orderDate, total, userId, methodName
FROM tblOrder o JOIN tblPaymentMethod p ON o.methodId = p.methodId
WHERE (orderDate BETWEEN '2021-03-20' AND '2021-03-23 23:30:38.733') AND userId = 'user' 

SELECT orderId, orderDate, total, userId, methodName
FROM tblOrder o join tblPaymentMethod p on o.methodId = p.methodId

SELECT productId, name, status, quantity, image, description, price, createOfDate, p.cateId, categoryName 
FROM tblProducts p, tblCategory c 
WHERE c.cateId = p.cateId 
AND quantity > 0  AND status = 'Active' AND productId IN (SELECT TOP(2) od.productId
															FROM tblOrderDetail od JOIN tblProducts P ON od.productId = p.productId
															WHERE p.quantity > 0  AND status = 'Active'
															GROUP BY od.productId
															ORDER BY SUM(od.quantity) DESC)

SELECT od.productId, SUM(od.quantity) AS total
FROM tblOrderDetail od JOIN tblProducts P ON od.productId = p.productId
WHERE p.quantity > 0  AND status = 'Active'
GROUP BY od.productId
ORDER BY SUM(od.quantity) DESC

SELECT TOP(3) productId, name, status, quantity, image, description, price, createOfDate, p.cateId, categoryName 
FROM tblProducts p, tblCategory c 
WHERE c.cateId = p.cateId 
AND quantity > 0  AND status = 'Active' AND p.cateId != 'chicken'
ORDER BY NEWID()