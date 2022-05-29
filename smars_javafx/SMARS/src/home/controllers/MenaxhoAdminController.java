package home.controllers;
import services.*;
import home.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import services.dbConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.Result;
import com.sun.javafx.logging.Logger;



public class MenaxhoAdminController implements Initializable {

    @FXML
    private TableView<MenaxhoAdminModel> tbData;
    
    @FXML
    public TableColumn<MenaxhoAdminModel, Integer> adminId;

    @FXML
    public TableColumn<MenaxhoAdminModel, String> firstName;

    @FXML
    public TableColumn<MenaxhoAdminModel, String> lastName;
    
    @FXML
    public TableColumn<MenaxhoAdminModel, String> email;
    
    @FXML
    public TableColumn<MenaxhoAdminModel, String> password;
    
    @FXML
    private TextField adminIdtxt;
    
    @FXML
    private TextField firstNametxt;
    
    @FXML
    private TextField lastNametxt;
    
    @FXML
    private TextField emailtxt;
    
    @FXML
    private TextField passwordtxt;
    
    
    private ObservableList<MenaxhoAdminModel> data;


    Connection dbconn = dbConnection.getConnection();
    

    
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList();
       
        	try{
//    	   
			    	URL url1 = new URL("http://localhost:8000/api/getAllAdmins");
			   		HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
			   		conn.setUseCaches(false);
			   		conn.setDoInput(true);
			   		conn.setDoOutput(true);
			   		conn.setRequestMethod("GET");
			   		conn.getInputStream();
			   		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			//   		JsonParser parser = new JsonParser();
			//   		JSONArray jsonArray = (JSONArray) parser.parseDoc(in.readLine());
			   		JSONArray obj = new JSONArray(in.readLine().toString());
			   		System.out.println(obj);
			   		
			   		for(int i = 0; i<obj.length(); i++) {
			   			JSONObject jsonObject = new JSONObject(obj.getJSONObject(i).toString());
			   			data.add(new MenaxhoAdminModel(
			   					jsonObject.getInt("adminId"),
			   					jsonObject.getString("firstName"),
			   					jsonObject.getString("lastName"),
			   					jsonObject.getString("email"),
			   					jsonObject.getString("password")
			   					));
			   		}
    	   
           adminId.setCellValueFactory(new PropertyValueFactory<MenaxhoAdminModel, Integer>("adminId"));
           firstName.setCellValueFactory(new PropertyValueFactory<MenaxhoAdminModel, String>("firstName"));
           lastName.setCellValueFactory(new PropertyValueFactory<MenaxhoAdminModel, String>("lastName"));
           email.setCellValueFactory(new PropertyValueFactory<MenaxhoAdminModel, String>("email"));
           password.setCellValueFactory(new PropertyValueFactory<MenaxhoAdminModel, String>("password"));
           
           adminIdtxt.setDisable(true);
         

           tbData.setItems(data);             
       
        	}catch (Exception e){
           e.printStackTrace();;
           System.out.println(e);
       }    
        
 
  	   		tbData.setRowFactory(tv -> {
  	   			
  	            TableRow<MenaxhoAdminModel> row = new TableRow<>();
  	            row.setOnMouseClicked(event -> {
  	            	adminIdtxt.setText( String.valueOf(row.getItem().getAdminId()));
	            	firstNametxt.setText(row.getItem().getFirstName());
	            	lastNametxt.setText(row.getItem().getLastName());
	            	emailtxt.setText(row.getItem().getEmail());
	            	passwordtxt.setText(row.getItem().getPassword());
	            	

  	            });
  	   			
  	            return row ;
  	        });
  	    
    }
	public void updateAdmin() throws IOException, JSONException {
		if(MenaxhoAdminModel.updateAdmin(Integer.parseInt(adminIdtxt.getText()), firstNametxt.getText(), lastNametxt.getText(),emailtxt.getText(),passwordtxt.getText()) & validateEmail()) {
			showAdmin();
			clearForm();
		
		}
	}

	public void deleteAdmin() throws IOException, JSONException {
		if(MenaxhoAdminModel.deleteAdmin(Integer.parseInt(adminIdtxt.getText()))) {
			showAdmin();
			clearForm();
			
		}
	}
	
	public void showAdmin() throws IOException, JSONException {
			List<MenaxhoAdminModel> admins = MenaxhoAdminModel.getAdmins();
			
			ObservableList<MenaxhoAdminModel> adminList = FXCollections.observableArrayList();
			
			for(int i = 0; i < admins.size(); i++) {
				adminList.add(admins.get(i));
			}
			
			tbData.setItems(adminList);
		}
	
	public void clearForm() {
		adminIdtxt.setText("");
		firstNametxt.setText("");
		lastNametxt.setText("");
		emailtxt.setText("");
		passwordtxt.setText("");
	}
	
	public boolean validateEmail() {
	  	  Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
	  	  Matcher m = p.matcher(emailtxt.getText());
	  	  if(m.find() && m.group().equals(emailtxt.getText())) {
	  		  return true;
	  	  }else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Validimi i Email");
				alert.setHeaderText(null);
				alert.setContentText("Ju lutem shenoni nje email valide!");
				alert.showAndWait();
				return false;
	  	  }

	}

}

