/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.mvc_model;

/**
 *
 * @author Thomas Ziegenhein
 */
public interface Model_FolderStructure {
    
    public String getFolder(Enum FolderName); 
    public void setOutFolder(String sOut);
    public Enum getEnum();
    
}
