package home.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class Administruesi implements Initializable {

    @FXML
    private Button RegjistroAdminbtn;

    @FXML
    private Button RegjistroStudentbtn;
    
    @FXML
    private Button MenaxhoStudentbtn;
    
    @FXML
    private Button MenaxhoAdminbtn;
  
    @FXML
    private Button AdminChatbtn;
    
    @FXML
    private Button Backbtn;
   
    //my bad - the freaking mouse event
    @FXML
    private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() == RegjistroAdminbtn) {
        
        	loadStage ("/home/fxml/LoginRegjistroAdmin.fxml");
            
           
        } else if (mouseEvent.getSource() == RegjistroStudentbtn) {
        	
        	loadStage ("/home/fxml/RegjistroStudent.fxml");
            
           
        } else if (mouseEvent.getSource() == MenaxhoAdminbtn) {
        	loadStage ("/home/fxml/LoginMenaxhoAdmin.fxml");
            
           
        }else if (mouseEvent.getSource() == MenaxhoStudentbtn) {
        	loadStage ("/home/fxml/MenaxhoStudent.fxml");
            
        }else if (mouseEvent.getSource() == Backbtn) {
        	Parent view2 = FXMLLoader.load(getClass().getResource("/home/fxml/Home.fxml"));

            Scene scene2 = new Scene(view2);

            Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            window.setScene(scene2);
            window.show();
        }
        else if (mouseEvent.getSource() == AdminChatbtn) {
           	
           	try {
           	    Desktop.getDesktop().browse(new URL("http://localhost:3000/").toURI());
           	} catch (IOException e) {
           	    e.printStackTrace();
           	} catch (URISyntaxException e) {
           	    e.printStackTrace();
           	}
           	
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
