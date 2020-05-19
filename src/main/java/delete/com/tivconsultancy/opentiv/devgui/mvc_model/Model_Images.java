/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.mvc_model;

import delete.com.tivconsultancy.opentiv.devgui.main.ImagePath;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageGrid;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public interface Model_Images {
    
    public ImageInt getImage(Enum DataName);
    public List<ImagePath> getIMGFiles();
    public void addImage(ImageInt o, Enum DataName);
    public void replaceImage(ImageInt oNew, Enum DataName);
    public void setImage(int iPos, ImageInt oNew, Enum DataName);
    public void clearImages();
    
    public static enum DataNames {
        //Names of the possible Data
        //Images in ImageInt format
        Input, TransEnd, PreProcEnd, MaskingEnd, PIVGrid, PIVVectors, CD_Edges, CD_ShapeFit
        //Other
    }
    
}
