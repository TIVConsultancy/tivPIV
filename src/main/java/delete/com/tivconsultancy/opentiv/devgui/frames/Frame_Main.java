/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.frames;

import delete.com.tivconsultancy.opentiv.devgui.mvc_model.Model_Features;
import delete.com.tivconsultancy.opentiv.devgui.mvc_model.Model_FolderStructure;
import delete.com.tivconsultancy.opentiv.devgui.mvc_model.Model_Save;
import delete.com.tivconsultancy.opentiv.devgui.mvc_model.Model_Settings;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageGrid;
import java.awt.Container;
import java.util.List;
import javax.swing.SwingWorker;
import delete.com.tivconsultancy.opentiv.devgui.mvc_model.Model_Images;

/**
 *
 * @author Thomas Ziegenhein
 */
public interface Frame_Main {
    public Frame_Feature getFeatureFrame();    
    public StandardFrame_ImageView getImageViewFrame();    
    public Model_Images getDataModel();    
    public Model_Settings getSettingsModel();
    public Model_Features getFeatureModel();
    public Model_FolderStructure getFoldersModel();
    public Model_Save getSaveModel();
    public void recalc(Enum Idend);    
    public List<? extends Object> getImageLayers();
    public SwingWorker getBackgroundTask();
    public ImageGrid readPic();
    public Container getContentPane_MainFrame();
    public void cancelAllProcesses();
    public Frame_Control getControlFrame();
    
}
