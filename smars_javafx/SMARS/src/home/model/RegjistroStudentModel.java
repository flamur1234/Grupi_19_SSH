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

import javax.swing.text.html.parser.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.cj.xdevapi.JsonParser;

import services.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import services.dbConnection;

public class RegjistroStudentModel {

    private SimpleIntegerProperty nid;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleIntegerProperty mosha;
    private SimpleStringProperty gjinia;
    private SimpleStringProperty fakulteti;
    private SimpleStringProperty departamenti;
    private SimpleStringProperty email;
    private SimpleStringProperty telefon;


    public RegjistroStudentModel(Integer nid, String firstName, String lastName, Integer mosha, String gjinia, String fakulteti, String departamenti,  String email, String telefon) {
        this.nid = new SimpleIntegerProperty(nid);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.mosha = new SimpleIntegerProperty(mosha);
        this.gjinia = new SimpleStringProperty(gjinia);
        this.fakulteti = new SimpleStringProperty(fakulteti);
        this.departamenti = new SimpleStringProperty(departamenti);
        this.email = new SimpleStringProperty(email);
        this.telefon = new SimpleStringProperty(telefon);

    }

    public int getNrId() {
        return nid.get();
    }

    public void setNrId(int nid) {
        this.nid = new SimpleIntegerProperty(nid);
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
    
    public int getMosha() {
        return mosha.get();
    }

    public void setMosha(int mosha) {
        this.mosha = new SimpleIntegerProperty();
    }
    
    public String getGjinia() {
        return gjinia.get();
    }

    public void setGjinia(String gjinia) {
        this.gjinia = new SimpleStringProperty(gjinia);
    }
    
    public String getFakulteti() {
        return fakulteti.get();
    }

    public void setFakulteti(String fakulteti) {
        this.fakulteti = new SimpleStringProperty(fakulteti);
    }
    
    public String getDepartamenti() {
        return departamenti.get();
    }

    public void setDepartamenti(String departamenti) {
        this.departamenti = new SimpleStringProperty(departamenti);
    }
    
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }
    public final SimpleStringProperty telefonProperty() {
    	   return telefon;
    	}
    
    public String getTelefoni() {
        return telefon.get();
    }

    public void setTelefoni(String telefon) {
        this.telefon = new SimpleStringProperty(telefon);
    }
    


    public static boolean deleteStudent(int nid) throws IOException, JSONException, SQLException {
//    	String query = "Delete from student where nid=?";
    	
    	URL url1 = new URL("http://localhost:8000/api/deleteStudent/"+nid);
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
    
    public static boolean konfirmoStudent(int nid) throws IOException, JSONException {
//    	String query1 = "Update student set kf=1 where nid=?";
//    	
    	URL url1 = new URL("http://localhost:8000/api/student/"+nid+"/1");
		HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
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
    
	public static List<RegjistroStudentModel> getStudents() throws JSONException, IOException, SQLException {
		List<RegjistroStudentModel> studentList = new ArrayList();
		
//		String query = "Select * from student where kf=0";
		
		URL url1 = new URL("http://localhost:8000/api/students/0");
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
			RegjistroStudentModel book = new RegjistroStudentModel(jsonObject.getInt("nid"), jsonObject.getString("eid"), jsonObject.getString("mid"), jsonObject.getInt("aid"), jsonObject.getString("gid"),jsonObject.getString("fid"), jsonObject.getString("did"), jsonObject.getString("eeid"), jsonObject.getString("tid"));
			studentList.add(book);

		}
		
		
		return studentList;
	}
}