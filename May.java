import java.util.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.*;
import java.text.*;
import java.time.LocalDate;

public class May extends Application{
  
  private Text month = new Text("May");
  private Text[] day = new Text[7];
  private Button[] B = new Button[31];
  private Text[] details;
  private Text s21 = new Text("Stuff to do");
  private Text s22 = new Text("Nothing to do!");
  private Button close = new Button("Go back");
  private Button edit = new Button("Edit");
  private Button save = new Button("Save");
  private String date;
  private TextField[] eDetails;
  private int today;
	  
  public void start(Stage primaryStage){
    primaryStage.setTitle("Calendar");
	for(int i=0; i<7; i++){day[i] = new Text();}
	day[0].setText("S");
	day[1].setText("M");
	day[2].setText("T");
	day[3].setText("W");
	day[4].setText("T");
	day[5].setText("F");
	day[6].setText("S");
	String num = "";
	LocalDate d = LocalDate.now();
	today = d.getDayOfMonth();
	GridPane pane = new GridPane();
	pane.add(month,0,0,8,1);
	 pane.setHalignment(month,HPos.CENTER);
	int rn=2;
	int cn=7;
	for(int i=0;i<7;i++){
	  pane.add(day[i],i+1,1,1,1);
	  pane.setHalignment(day[i],HPos.CENTER);
	}
	for(int i=0; i<31; i++){
	  num+= (i+1);
	  B[i] = new Button(num);
	  if (i == today-1){B[i].setStyle("-fx-background-color: #008080; ");}
	  pane.add(B[i],cn,rn,1,1);
	  pane.setHalignment(B[i],HPos.CENTER);
	  num = "";
	  cn++;
	  B[i].setOnAction(this::stuff);
	  if(i%7 == 0){cn=1; rn++;}
	}
	pane.setHgap(5);
	pane.setVgap(5);
	Scene scene = new Scene(pane,247,275);
	primaryStage.setScene(scene);
	primaryStage.show();
  }
  
  public void start2(Stage primaryStage,String num){
    date = num + month.getText() + ".txt";
	Scanner sc ;
	File file;
	GridPane pane = new GridPane();
    close.setOnAction(this::closeWindow);
	edit.setOnAction(this::editStuff);
	try{
	  file = new File(date);
	  sc = new Scanner(file);
	  int count = 0;
	  while(sc.hasNextLine()){
		if(sc.nextLine().trim().length() != 0)
	    count++;
	  }
	  if(count == 0){throw new FileNotFoundException();}
	  details = new Text[7]; 
	  pane.add(s21,1,0);
	  sc = new Scanner(file);
	  int i=0;
	  String text = "";
	  while(sc.hasNextLine()){
		details[i] = new Text();
		text = sc.nextLine();
		if(!text.isEmpty())
	     details[i].setText(text);
		pane.add(details[i],1,i+1,25,1);
		i++;
	  } 
      while(i<7){	
        details[i] = new Text();
		pane.add(details[i],1,i+1,25,1);
		i++;
	  }  		
	  pane.add(close,1,20,1,1);
	  pane.setHalignment(close,HPos.LEFT);
	  pane.add(edit,27,20,1,1);
	  pane.setHalignment(edit,HPos.RIGHT);
	  pane.setHgap(5);
	  pane.setVgap(5);
	  Scene scene = new Scene(pane,247,275);
	  primaryStage.setScene(scene);
	  primaryStage.show();
	}
	catch(FileNotFoundException e){
	  pane.add(s22,1,0);
	  pane.add(close,1,42);
	  pane.add(edit,23,42);
	  pane.setHgap(5);
	  pane.setVgap(5);
	  Scene scene = new Scene(pane,247,275);
	  primaryStage.setScene(scene);
	  primaryStage.show();
	}
  }
  
  public void stuff(ActionEvent event){
    Button b = (Button)event.getTarget();
	String num = b.getText();
	Stage primaryStage = new Stage();
	primaryStage.setTitle("Checklist");
	start2(primaryStage,num);
  }
  
  public void editStuff(ActionEvent event){
    Stage s = (Stage)edit.getScene().getWindow();
    s.close();
	Stage primaryStage = new Stage();
	primaryStage.setTitle("Edit Checklist");
	start3(primaryStage);
  }
  
  public void start3(Stage primaryStage){
	Scanner sc ;
	File file;
	PrintWriter pw;
    GridPane pane = new GridPane();
    close.setOnAction(this::closeWindow);
	save.setOnAction(this::saveStuff);
	try{
	  file = new File(date);
	  sc = new Scanner(file);
	  int count = 0;
	  while(sc.hasNextLine() && count<=7){
	    if(!sc.nextLine().isEmpty())
	     count++;
	  }
	  if(count == 0){throw new FileNotFoundException();}
	  eDetails = new TextField[7];
	  pane.add(s21,1,0);
	  sc = new Scanner(file);
	  int i=0;
	  String text;
	  while(sc.hasNextLine() && i<=count){
		text = sc.nextLine();
		eDetails[i] = new TextField();
		eDetails[i].setPrefWidth(130);
		if(!text.isEmpty())
	      eDetails[i].setText(text);
		pane.add(eDetails[i],1,i+1,23,1);
		i++;
	  }  
	  while(i<eDetails.length){
	    eDetails[i] = new TextField();
		eDetails[i].setPrefWidth(130);
		pane.add(eDetails[i],1,i+1,23,1);
		i++;
	  }
      pane.add(close,1,10,1,1);
	  pane.setHalignment(close,HPos.LEFT);
	  pane.add(save,25,10,1,1);
	  pane.setHalignment(save,HPos.RIGHT);
	  pane.setHgap(5);
	  pane.setVgap(5);
	  Scene scene = new Scene(pane,247,275);
	  primaryStage.setScene(scene);
	  primaryStage.show();
	}
	catch(FileNotFoundException e){
	  eDetails = new TextField[7];
	  pane.add(s21,1,0);
	  int i=0;
	  while(i<7){
		eDetails[i] = new TextField();
		eDetails[i].setPrefWidth(130);
		pane.add(eDetails[i],1,i+1,23,1);
		i++;
	  }  
      pane.add(close,1,10,1,1);
	  pane.add(save,25,10,1,1);
	  pane.setHgap(5);
	  pane.setVgap(5);
	  Scene scene = new Scene(pane,247,275);
	  primaryStage.setScene(scene);
	  primaryStage.show();
	}
  }
  
  public void saveStuff(ActionEvent event){
    File file = new File(date);
	PrintWriter pw ;
	try{
	  pw= new PrintWriter(file);
	  System.out.println(eDetails.length);
	  for(int i=0; i<eDetails.length;i++){
	  System.out.println(i);
	  if(!eDetails[i].getText().isEmpty()){
	    pw.println(eDetails[i].getText());
	  }
	}
	pw.close();
    Stage s = (Stage)save.getScene().getWindow();
    s.close();
	}
    catch(FileNotFoundException e){}
  }
	  
  public void closeWindow(ActionEvent event){
    Stage s = (Stage)close.getScene().getWindow();
    s.close();
  }  
}
	
	