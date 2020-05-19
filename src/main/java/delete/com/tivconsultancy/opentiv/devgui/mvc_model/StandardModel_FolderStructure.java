/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.mvc_model;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class StandardModel_FolderStructure implements Model_FolderStructure {

    public String sSettings = "Settings";
    public String sDebug = "Debug";
    public String sPictures = "Pictures";
    public String sData = "Data";
    
    public String sOut;
    
    public StandardModel_FolderStructure(String sOut){
        this.sOut = sOut;
    }
    
    @Override
    public String getFolder(Enum FolderName){
        if(FolderName.equals(StandardFolders.Settings)) return sSettings;
        if(FolderName.equals(StandardFolders.Debug)) return sDebug;
        if(FolderName.equals(StandardFolders.Pictures)) return sPictures;
        if(FolderName.equals(StandardFolders.Data)) return sData;
        if(FolderName.equals(StandardFolders.Out)) return sOut;
        return null;
    }

    @Override
    public void setOutFolder(String sOut) {
        this.sOut = sOut;
    }

    @Override
    public Enum getEnum() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static enum StandardFolders {
        //possible folders        
        Settings, Debug, Pictures, Data, Out;

        public class GUI_Data {

            public GUI_Data() {
            }
        }
    }
    
}
