/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblProducts;

import java.io.Serializable;
import java.sql.Date;



/**
 *
 * @author minhv
 */
public class TblProductsDTO implements Serializable{
    private String productId;
    private String name;
    private String status;
    private int quantity;
    private String image;
    private String description;
    private double price;
    private Date createOfDate;
    private String cateId;

    public TblProductsDTO() {
    }

    public TblProductsDTO(String productId, String name, String status, int quantity, String image, String description, double price, Date createOfDate, String cateId) {
        this.productId = productId;
        this.name = name;
        this.status = status;
        this.quantity = quantity;
        this.image = image;
        this.description = description;
        this.price = price;
        this.createOfDate = createOfDate;
        this.cateId = cateId;
    }

    /**
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the createOfDate
     */
    public Date getCreateOfDate() {
        return createOfDate;
    }

    /**
     * @param createOfDate the createOfDate to set
     */
    public void setCreateOfDate(Date createOfDate) {
        this.createOfDate = createOfDate;
    }

    /**
     * @return the cateId
     */
    public String getCateId() {
        return cateId;
    }

    /**
     * @param cateId the cateId to set
     */
    public void setCateId(String cateId) {
        this.cateId = cateId;
    }
}
