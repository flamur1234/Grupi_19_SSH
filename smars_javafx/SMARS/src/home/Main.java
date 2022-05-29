package home;

import services.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpResponse;

import org.json.JSONObject;
import org.json.JSONArray;

import com.mysql.cj.xdevapi.JsonParser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));
        primaryStage.getIcons().add(new Image("/home/icons/icon.png"));
        primaryStage.setTitle("SMARS");
        primaryStage.setScene(new Scene(root));
        
        	if(!dbConnection.getConnection().isClosed() || dbConnection.getConnection() != null){
        		System.out.println("Database is connected");
        	}
        	else {
        		System.out.println("Database is not connected");
        	}
        	
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
