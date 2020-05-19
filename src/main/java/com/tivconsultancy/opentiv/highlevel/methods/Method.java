/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.opentiv.highlevel.methods;

import delete.com.tivconsultancy.opentiv.devgui.main.ImagePath;
import com.tivconsultancy.opentiv.highlevel.protocols.NameSpaceProtocolResults1D;
import com.tivconsultancy.opentiv.highlevel.protocols.Protocol;
import com.tivconsultancy.opentiv.highlevel.protocols.Result1D;
import com.tivconsultancy.opentiv.math.specials.LookUp;
import java.io.File;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public interface Method {
    public List<ImagePath> getInputImages();
    public List<Protocol> getProtocols();
    public Protocol getProtocol(String ident);
    public void set1DResult(NameSpaceProtocolResults1D e);
    
    public Result1D get1DResults();
    public void readInFileForView(File f) throws Exception;
    public void setFiles(File[] f);
    public void run() throws Exception;
}
