/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.mvc_model;

import com.tivconsultancy.opentiv.logging.TIVLog;
import delete.com.tivconsultancy.opentiv.devgui.main.ImagePath;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class StandardModel_Data implements Model_Images {

    List<ImageInt> loImages = new ArrayList<>();

    @Override
    public ImageInt getImage(Enum DataName) {
        for (ImageInt o : loImages) {
            if (o.sIdent.equals(DataName.name())) {
                return o;
            }
        }
        return null;

    }

    @Override
    public void addImage(ImageInt o, Enum DataName) {
        o.sIdent = DataName.name();
        loImages.add(o);
    }
    
    @Override
    public void replaceImage(ImageInt oNew, Enum DataName){
        ImageInt oOld = getImage(DataName);
        if(oOld == null){
//            TIVLog.Log.warn((new Throwable()).getStackTrace()[0].getMethodName() + "Tried to replace a non existing image, Image added");
            this.addImage(oNew, DataName);
        }
        if(oOld!=null && oNew != null){
            int iPos = loImages.indexOf(oOld);
            if(iPos>=0){
                setImage(iPos, oNew, DataName);
            }            
        }        
    }

    @Override
    public void clearImages() {
        loImages.clear();
    }

    @Override
    public void setImage(int iPos, ImageInt oNew, Enum DataName) {
        oNew.sIdent = DataName.name();
        loImages.set(iPos, oNew);
    }
        
//    
//    public static enum StandardDataNames {
//        //Names of the possible Data
//        //Images in ImageInt format
//        ReadIn, Transformation, PreProccessor, Mask
//        //Other
//    }

    @Override
    public List<ImagePath> getIMGFiles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
