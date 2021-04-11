/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtn.utilities;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.http.Part;

/**
 *
 * @author minhv
 */
public class ImageUtil implements Serializable{
    public static String writeToDisk(Part part, String destinationPath)
        throws IOException {
        String imageName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        File finalPath = new File(destinationPath + "\\" + imageName);
        
        Files.copy(part.getInputStream(), finalPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        return imageName;
    }
}
