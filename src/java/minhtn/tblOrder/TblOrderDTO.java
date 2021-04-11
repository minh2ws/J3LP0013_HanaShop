/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblOrder;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author minhv
 */
public class TblOrderDTO implements Serializable {
    private String orderId;
    private Date orderDate;
    private double total;
    private String userId;
    private String methodId;

    public TblOrderDTO() {
    }

    public TblOrderDTO(String orderId, Date orderDate, double total, String userId, String methodId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.total = total;
        this.userId = userId;
        this.methodId = methodId;
    }

    /**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the orderDate
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the methodId
     */
    public String getMethodId() {
        return methodId;
    }

    /**
     * @param methodId the methodId to set
     */
    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }
}
