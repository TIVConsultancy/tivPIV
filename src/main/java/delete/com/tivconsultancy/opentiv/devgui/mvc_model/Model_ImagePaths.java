/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.mvc_model;

import delete.com.tivconsultancy.opentiv.devgui.main.ImagePath;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public interface Model_ImagePaths {
 
    public List<ImagePath> getIMGFiles();
    public void addImage(ImagePath o);
    public void setImage(int iPos, ImagePath o);
    public void removeImage(int iPos);
    public void clearImages();
    public String getName();
    public void setName(String name);
    
}
