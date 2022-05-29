package home.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button Administruesibtn;

    @FXML
    private Button Regjistrohubtn;

    @FXML
    private Button Chatbtn;
    
    

  

    //my bad - the freaking mouse event
    @FXML
    private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) throws Exception {
        if (mouseEvent.getSource() == Administruesibtn) {
        	Parent view2 = FXMLLoader.load(getClass().getResource("/home/fxml/LogIn.fxml"));

            Scene scene2 = new Scene(view2);

            Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            window.setScene(scene2);
            window.show();
            
        } else if (mouseEvent.getSource() == Regjistrohubtn) {
        	loadStage ("/home/fxml/Regjistrohu.fxml");
        	
            
        
        } else if (mouseEvent.getSource() == Chatbtn) {
//        	 Parent view2 = FXMLLoader.load(getClass().getResource("/home/fxml/Students.fxml"));
//
//             Scene scene2 = new Scene(view2);
//
//             Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
//             window.setScene(scene2);
//             window.show();

        	try {
        	    Desktop.getDesktop().browse(new URL("http://localhost:3000/").toURI());
        	} catch (IOException e) {
        	    e.printStackTrace();
        	} catch (URISyntaxException e) {
        	    e.printStackTrace();
        	}

            
//        	Stage stage = new Stage();
//        	Chat menu = new Chat();
//        	menu.start(stage);
//        	stage.show();

        }
    }
    
   

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("SMARS");
            stage.getIcons().add(new Image("/home/icons/icon.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  
}
