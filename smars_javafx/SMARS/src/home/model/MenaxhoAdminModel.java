package home.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import services.*;
public class MenaxhoAdminModel {

    private SimpleIntegerProperty adminId;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty email;
    private SimpleStringProperty password;

    public MenaxhoAdminModel(int adminId, String firstName, String lastName, String email, String password) {
        this.adminId = new SimpleIntegerProperty(adminId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);

    }

    public int getAdminId() {
        return adminId.get();
    }

    public void setAdminId(int adminId) {
        this.adminId = new SimpleIntegerProperty(adminId);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName = new SimpleStringProperty(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName = new SimpleStringProperty(lastName);
    }
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }
    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);
    
	}
    public static boolean updateAdmin(int adminId, String firstName, String lastName, String email, String password) throws IOException, JSONException {
//    	String query = "UPDATE admin SET first_name=?, last_name=?, email=?, pass=? WHERE id=?";
    	
    	URL url1 = new URL("http://localhost:8000/api/updateAdmin/"+adminId+"/"+firstName+"/"+lastName+"/"+email+"/"+password);
		HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		JSONObject obj = new JSONObject(in.readLine());
		if (obj.get("status").equals("success")) {
			return true;
		}
		else {
			return false;
		}
    }

    public static boolean deleteAdmin(int adminId) throws IOException, JSONException {
//    	String query = "Delete from admin where id=?";
        	
        	URL url1 = new URL("http://localhost:8000/api/deleteAdmin/"+adminId);
    		HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
    		conn.setUseCaches(false);
    		conn.setDoInput(true);
    		conn.setDoOutput(true);
    		conn.setRequestMethod("DELETE");
    		conn.getInputStream();
    		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    		JSONObject obj = new JSONObject(in.readLine());
    		System.out.println(obj);
    		if (obj.get("status").equals("success")) {
    			return true;
    		}
    		else {
    			return false;
    		}
    }
	public static List<MenaxhoAdminModel> getAdmins() throws IOException, JSONException {
		List<MenaxhoAdminModel> adminList = new ArrayList();
		
//		String query = "Select * from admin";
		URL url1 = new URL("http://localhost:8000/api/getAllAdmins");
		HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		JSONArray obj = new JSONArray(in.readLine().toString());
		System.out.println(obj);
		
		
		for(int i = 0; i<obj.length(); i++) {
			JSONObject jsonObject = new JSONObject(obj.getJSONObject(i).toString());
			MenaxhoAdminModel book = new MenaxhoAdminModel(jsonObject.getInt("adminId"), jsonObject.getString("firstName"), jsonObject.getString("lastName"), jsonObject.getString("email"), jsonObject.getString("password"));
			adminList.add(book);

		}
		
		return adminList;
	}
}
