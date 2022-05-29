package home.controllers;

import java.net.URLEncoder;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebHistory.Entry;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Chat extends Application {
  private Stage mainWindow;
  private WebView webView;
  private Button backButton;
  private Button nexButton;
  private Button reloadButton;
  private Button searchButton;
  private TextField urlTextField;

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    mainWindow = primaryStage;

    BorderPane pane = new BorderPane();
    pane.setTop(getTopPane());
    pane.setCenter(getWebViewContainer());

    Scene scene = new Scene(pane, 960, 600);
    primaryStage.setScene(scene);

    primaryStage.setTitle("JavaFX WebView Example");
    primaryStage.setMaximized(true);
    primaryStage.show();

    urlTextField.requestFocus();
  }

  private Node getMenuBarContainer() {
    MenuBar menuBar = new MenuBar();
    Menu fileMenu = new Menu("File");
    MenuItem historyItem = new MenuItem("History");
    fileMenu.getItems().add(historyItem);

    Menu editMenu = new Menu("Edit");
    MenuItem editHtmlItem = new MenuItem("Insert HTML");
    MenuItem execJsItem = new MenuItem("Execute Javascript");
    editMenu.getItems().addAll(editHtmlItem, execJsItem);

    menuBar.getMenus().addAll(fileMenu, editMenu);

    historyItem.setOnAction(e -> this.showHistory());
    editHtmlItem.setOnAction(e -> insertHtmlContent());
    execJsItem.setOnAction(e -> execJs());

    return menuBar;
  }

  private Node getNavBarContainer() {
    HBox navbar = new HBox();
    navbar.setSpacing(10);
    navbar.setPadding(new Insets(5));

    backButton = new Button("🡄");
    nexButton = new Button("🡆");
    reloadButton = new Button("⭮");

    urlTextField = new TextField();
    urlTextField.setPromptText("Enter URL here");
    HBox.setHgrow(urlTextField, Priority.ALWAYS);
    searchButton = new Button("Search");

    navbar.getChildren().addAll(backButton, nexButton, reloadButton, urlTextField, searchButton);

    backButton.setOnAction(e -> historyPrev());
    nexButton.setOnAction(e -> historyNext());
    reloadButton.setOnAction(e -> reloadPage());
    searchButton.setOnAction(e -> this.searchPage(urlTextField.getText()));
    urlTextField.setOnKeyReleased(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        this.searchPage(urlTextField.getText());
      }
    });

    return navbar;
  }

  private Pane getTopPane() {
    VBox vBox = new VBox();
    vBox.setSpacing(2.5);

    vBox.getChildren().addAll(getMenuBarContainer(), getNavBarContainer());
    return vBox;
  }

  private Node getWebViewContainer() {
    VBox vBox = new VBox();

    webView = new WebView();
    vBox.getChildren().add(webView);
    webView.prefHeightProperty().bind(vBox.heightProperty());
    webView.prefWidthProperty().bind(vBox.widthProperty());
//    System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
    webView.getEngine().load("http://localhost:3000/");
    webView.getEngine().locationProperty().addListener(ov -> {
      urlTextField.setText(webView.getEngine().getLocation());
    });

    return vBox;
  }

  private void historyPrev() {
    WebHistory history = this.webView.getEngine().getHistory();
    if (history.getCurrentIndex() > 0)
      history.go(-1);
  }

  private void historyNext() {
    WebHistory history = this.webView.getEngine().getHistory();
    if (history.getCurrentIndex() < history.getEntries().size() - 1)
      history.go(1);
  }

  private void reloadPage() {
    this.webView.getEngine().reload();
  }

  private void searchPage(String url) {
    try {
      url = url.trim();
      if (url.length() == 0)
        return;

      if (url.indexOf(' ') >= 0) {
        url = "https://www.google.com/search?q=" + URLEncoder.encode(url, "UTF-8");
      } else if (url.indexOf("://") < 0) {
        url = "http://" + url;
      }
      this.webView.getEngine().load(url);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  private void showHistory() {
    StackPane pane = new StackPane();
    TreeItem<String> rootItem = new TreeItem<>("Browser history");
    rootItem.setExpanded(true);
    TreeView<String> treeView = new TreeView<>(rootItem);
    pane.getChildren().add(treeView);

    WebHistory history = this.webView.getEngine().getHistory();

    for (Entry entry : history.getEntries()) {
      rootItem.getChildren()
          .add(new TreeItem<>(entry.getTitle() + " " + entry.getUrl() + " " + entry.getLastVisitedDate().toString()));
    }

    Scene scene = new Scene(pane, 800, 600);
    Stage stage = new Stage();
    stage.setScene(scene);
    mainWindow.hide();
    stage.showAndWait();
    mainWindow.show();
  }

  private void insertHtmlContent() {
    try {
      String content = textAreaPopup("Insert");
      webView.getEngine().loadContent(content, "text/html");
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  private void execJs() {
    try {
      String content = textAreaPopup("Execute");
      webView.getEngine().executeScript(content);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  private String textAreaPopup(String buttonTitle) {
    VBox pane = new VBox();
    pane.setSpacing(2.5);

    TextArea textArea = new TextArea();
    Button insertButton = new Button(buttonTitle);
    VBox.setVgrow(textArea, Priority.ALWAYS);

    pane.getChildren().addAll(textArea, insertButton);

    Scene scene = new Scene(pane, 800, 600);
    Stage stage = new Stage();
    stage.setScene(scene);

    insertButton.setOnAction(e -> {
      stage.close();
    });

    mainWindow.hide();
    stage.showAndWait();
    mainWindow.show();

    return textArea.getText();
  }



}
