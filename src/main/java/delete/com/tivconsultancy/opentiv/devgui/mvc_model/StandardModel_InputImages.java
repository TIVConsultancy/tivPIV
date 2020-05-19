/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package delete.com.tivconsultancy.opentiv.devgui.mvc_model;

import delete.com.tivconsultancy.opentiv.devgui.main.ImagePath;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class StandardModel_InputImages implements Model_ImagePaths{

    private List<ImagePath> images;
    private String name = "";
    
    public StandardModel_InputImages(String name){
        this.name = name;
        images = new ArrayList<>();
    }
    
    @Override
    public List<ImagePath> getIMGFiles() {
        return images;
    }

    @Override
    public void addImage(ImagePath o) {
        images.add(o);
    }

    @Override
    public void setImage(int iPos, ImagePath o) {
        images.set(iPos, o);
    }

    @Override
    public void removeImage(int iPos) {
        images.remove(iPos);
    }

    @Override
    public void clearImages() {
        images.clear();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}
