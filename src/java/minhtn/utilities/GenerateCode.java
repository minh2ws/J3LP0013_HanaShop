/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.utilities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 *
 * @author minhv
 */
public class GenerateCode implements Serializable {
    //create OrderId with format YYYYMMdd-hhmmss-xxxxxx (x is category Id)
    public static String generateProductID(String cateId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd-hhmmss");
        java.util.Date date = new java.util.Date();
        
        String dateGetted = dateFormat.format(date);
        
        return dateGetted + "-" + cateId;
    }
    
    //create OrderId with format YYYYMMdd-hhmmss-yyy (x is subject Id, y is random number)
    public static String generateLogID() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd-hhmmss");
        java.util.Date date = new java.util.Date();
        
        String dateGetted = dateFormat.format(date);
        
        String randomNumberString = "";
        
        for (int i = 0; i < 3; i++) {
            int randomNumber = new Random().nextInt(10);
            randomNumberString += randomNumber;
        }
        
        return dateGetted + "-" + randomNumberString;
    }
    
        //create OrderId with format YYYYMMdd-hhmmss-yyy (x is subject Id, y is random number)
    public static String generateOrderID() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd-hhmmss");
        java.util.Date date = new java.util.Date();
        
        String dateGetted = dateFormat.format(date);
        
        String randomNumberString = "";
        
        for (int i = 0; i < 3; i++) {
            int randomNumber = new Random().nextInt(10);
            randomNumberString += randomNumber;
        }
        
        return dateGetted + "-" + randomNumberString;
    }
}
