/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.tblCategory;

import java.io.Serializable;

/**
 *
 * @author minhv
 */
public class TblCategoryDTO implements Serializable{
    private String cateId;
    private String categoryName;

    public TblCategoryDTO() {
    }

    public TblCategoryDTO(String cateId, String categoryName) {
        this.cateId = cateId;
        this.categoryName = categoryName;
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

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
