/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.main;

import java.io.File;

/**
 *
 * @author Thomas Ziegenhein
 */
public class ImagePath {
    
    public File o;
    public String sInput;
    
    public ImagePath(String s){
        this.sInput = s;
        this.o = new File(s);
    }
    
    public String toString(){
        return o.getName();
    }
    
}
