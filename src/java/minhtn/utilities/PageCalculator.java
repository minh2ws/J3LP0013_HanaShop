/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.utilities;

import java.io.Serializable;

/**
 *
 * @author minhv
 */
public class PageCalculator implements Serializable{
    
    /**
     * Calculate total page need
     * @param totalResult total result
     * @param resultPerPage number of result in one page
     * @return number of total page
     */
    public static int calculateTotalPage(int totalResult, int resultPerPage) {
        int totalPage;
        
        if (totalResult % resultPerPage != 0)
            totalPage = (totalResult / resultPerPage) + 1;
        else
            totalPage = (totalResult / resultPerPage);
        
        return totalPage;
    }
}
