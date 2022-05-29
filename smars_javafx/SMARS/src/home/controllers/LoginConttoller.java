package home.controllers;
import services.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.scene.Node;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.*;

	
	public class LoginController implements Initializable {
		

	    @FXML
	    private Button loginlabel;
	    
	    @FXML
	    private Button kthehuBtn;
	    
	    @FXML
	    private TextField txtUsername;
	    
	    @FXML
	    private TextField txtPassword;
	    
	     
	    Stage dialogStage = new Stage();
	    Scene scene;
	    
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	   
	    public LoginController() {
	   }
	        @FXML
	        public void loginAction(ActionEvent event){
            String firstName = txtUsername.getText().toString();
	        String password = txtPassword.getText().toString();
	    
//	        String sql = "SELECT * FROM admin WHERE first_name = ? and pass = ?";
	        
	        
	        
	        try{
	        	URL url = new URL("http://localhost:8000/api/admins/"+firstName+"/"+password);
	    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    		conn.setUseCaches(false);
	    		conn.setDoInput(true);
	    		conn.setDoOutput(true);
	    		conn.setRequestMethod("GET");
	    		conn.getInputStream();
	    		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));	
//	    		JsonParser parser = new JsonParser();
//	    		JSONArray jsonArray = (JSONArray) parser.parseDoc(in.readLine());
	    		JSONArray obj = new JSONArray(in.readLine());
	    		
	            if(obj.length() == 0){
	                infoBox("Please enter correct Name and Password", null, "Failed");
	            }else{
	                infoBox("Login Successfull",null,"Success" );
	                Node node = (Node)event.getSource();
	                dialogStage = (Stage) node.getScene().getWindow();
	                dialogStage.close();
	                scene = new Scene(FXMLLoader.load(getClass().getResource("/home/fxml/Administruesi.fxml")));
	                dialogStage.setScene(scene);
	                dialogStage.show();
	            }
	        }
	        catch(Exception e){
	            e.printStackTrace();
	        }
	        
	    }
	    public static void infoBox(String infoMessage, String headerText, String title){
	        Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setContentText(infoMessage);
	        alert.setTitle(title);
	        alert.setHeaderText(headerText);
	        alert.showAndWait();
	    }
	    

	   
	    //my bad - the freaking mouse event
	    @FXML
	    private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) throws IOException {
	      if (mouseEvent.getSource() == kthehuBtn) {
	        	Parent view2 = FXMLLoader.load(getClass().getResource("/home/fxml/Home.fxml"));

	            Scene scene2 = new Scene(view2);

	            Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
	            window.setScene(scene2);
	            window.show();
	        }
	    }
	    

	    @Override
	    public void initialize(URL location, ResourceBundle resources) {

	    }
	    
	    
	   
	    }
	    
	   
	 
	


