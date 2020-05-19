/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.opentiv.datamodels;

import com.tivconsultancy.opentiv.logging.TIVLog;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class TempOnDisk {

    private int intRand = (int) (Math.random() * 10000000);

    public TempOnDisk(File whereToSave) {        
        if (whereToSave == null) {
            try {
                cleanup();
                (new File(File.createTempFile("tivcon-temp-file", "tmp").getParent() + File.separator + "tivcon-" + intRand)).mkdir();
            } catch (IOException ex) {
                TIVLog.tivLogger.log(Level.SEVERE, null, ex);
            }            
        }
    }

    private void cleanup() throws IOException {
        File newTemp = File.createTempFile("tivcon-temp-file", "tmp");
        File parent = newTemp.getParentFile();
        for (File f : parent.listFiles()) {
            if (f.toString().contains("tivcon-")) {
                FileTime creationTime = (FileTime) Files.readAttributes(f.toPath(), BasicFileAttributes.class).creationTime();
                if (System.currentTimeMillis() - creationTime.toMillis() > 1.728e+8 ) {
                    TIVLog.tivLogger.log(Level.INFO,"Temp file cleanup, the following file/folder will be deleted: " + f.getName(), new Throwable());
                    if(f.isDirectory()){
                        FileUtils.deleteDirectory(f);
                    }else{
                        f.delete();
                    }                   
                }
            }
        }
    }

}
