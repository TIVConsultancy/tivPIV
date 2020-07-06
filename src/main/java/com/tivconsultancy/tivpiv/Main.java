/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.tivpiv;

import com.tivconsultancy.opentiv.helpfunctions.io.Writer;
import com.tivconsultancy.tivGUI.MainFrame;
import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivGUI.TIVScene;
import com.tivconsultancy.tivpiv.helpfunctions.RecArea;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Thomas Ziegenhein
 */
public class Main extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
//        imgTobase64();
//        System.exit(0);
        launch(args);
    }
    
    public static void imgTobase64() throws IOException{
        byte[] fileContent = FileUtils.readFileToByteArray(new File("D:\\Sync\\TIVConsultancy\\_Customers\\HZDR\\DataProject\\FolderHZDRDataManagement\\Examples-2020-3-30 20.46.28\\NabilLongColumn\\Tergitol\\1p0\\00000.jpg"));
        String encodedString = Base64
          .getEncoder()
          .encodeToString(fileContent);
        Writer owrite = new Writer("D:\\Sync\\TIVConsultancy\\JupyterNotebooks\\HZDR\\Text\\base64img.txt");
        owrite.write(encodedString);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        showEmptyMainFrame(primaryStage);
    }

    public static void showEmptyMainFrame(Stage primaryStage) throws IOException {

        StaticReferences.setStandardIcons(new RecArea(null, null), "com/tivconsultancy/tivPIV/menuicons/");       

        List<String> icons = new ArrayList<>();
        icons.add("/com/tivconsultancy/tivPIV/icons/Icon128x128.png");
        icons.add("/com/tivconsultancy/tivPIV/icons/Icon64x64.png");
        icons.add("/com/tivconsultancy/tivPIV/icons/Icon32x32.png");
        icons.add("/com/tivconsultancy/tivPIV/icons/Icon16x16.png");
        MainFrame.setIcons(new RecArea(null, null).getClass(), icons);
        MainFrame.setLogo(new RecArea(null, null).getClass(), "/com/tivconsultancy/tivPIV/logo/Logo-BlackBlueText_noBckGr_s.png");
        MainFrame.setLoadPicture(new RecArea(null, null).getClass(), "/com/tivconsultancy/tivPIV/icons/load.png");

        StaticReferences.controller = new PIVController();
        StaticReferences.controller.startNewMethod(new PIVMethod());
        MainFrame tivGUI = new MainFrame();
        TIVScene scene = new TIVScene(tivGUI);
        StaticReferences.controller.setScene(scene);
        StaticReferences.controller.setGUI(tivGUI);

        scene.getStylesheets().add(scene.getClass().getResource("/com/tivconsultancy/tivPIV/cssFiles/tiv.css").toExternalForm());

        primaryStage.getIcons().addAll(MainFrame.getIcon());
        primaryStage.setTitle("tivPIV");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
//        StaticReferences.controller.startNewSession(new File("D:\\Sync\\TIVConsultancy\\_Customers\\HZDR\\DataProject\\FolderHZDRDataManagement\\Examples-2020-3-30 20.46.28\\NabilLongColumn\\Water\\1p0"));
        StaticReferences.controller.startNewSession(null);
//        tivGUI.startNewSession();

    }
}
