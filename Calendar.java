import java.util.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.*;
import java.text.*;
import java.time.LocalDate;
import java.time.DayOfWeek;


public class Calendar extends Application{
  private Text[] month = new Text[12];
  private Text[] day = new Text[7];
  private Button[] B = new Button[31];
  private Text[] details;
  private Text s21 = new Text("Stuff to do");
  private Text s22 = new Text("Nothing to do");
  private Button close = new Button("Go back");
  private Button edit = new Button("Edit");
  private Button save = new Button("Save");
  private String date;
  private TextField[] eDetails;
  private int dayNo;
  private int monthNum;
  private int[] dayNum = new int[12];
  private Text year = new Text("2021");
  private boolean firstRun = true;
  private GridPane pane = new GridPane();
  
  public void initializer(){
    for(int i=0; i<7; i++){day[i] = new Text();}
	day[0].setText("S");
	day[1].setText("M");
	day[2].setText("T");
	day[3].setText("W");
	day[4].setText("T");
	day[5].setText("F");
	day[6].setText("S");
	for(int i=0; i<12; i++){month[i] = new Text();}
	month[0].setText("January");
	month[1].setText("February");
	month[2].setText("March");
	month[3].setText("April");
	month[4].setText("May");
	month[5].setText("June");
	month[6].setText("July");
	month[7].setText("August");
	month[8].setText("September");
	month[9].setText("October");
	month[10].setText("November");
	month[11].setText("December");
	dayNum[0]=31;dayNum[1]=28;dayNum[2]=31;dayNum[3]=30;dayNum[4]=31;
	dayNum[5]=30;dayNum[6]=31;dayNum[7]=31;dayNum[8]=30;dayNum[9]=31;
	dayNum[10]=30;dayNum[11]=31;
	LocalDate d = LocalDate.now();
	dayNo = d.getDayOfMonth();
	monthNum = d.getMonthValue()-1;
	firstRun = false;
  }
  
  public void start(Stage primaryStage){
	if(firstRun){initializer();}
	Scene scene;
    primaryStage.setTitle("Calendar");
	pane.add(year,0,0,8,1);
	pane.setHalignment(year,HPos.CENTER);
	Text monthOut = new Text(month[monthNum].getText());;
	Label label = new Label("Select Month:");
    
    ObservableList<String> months = FXCollections.observableArrayList(//
            "January", "February", "March", "April", //
            "May", "June", "July", "August", //
            "September", "October", "November", "December");
 
    final Spinner<String> spinner = new Spinner<String>();
    SpinnerValueFactory<String> valueFactory = 
            new SpinnerValueFactory.ListSpinnerValueFactory<String>(months);
    valueFactory.setValue(month[monthNum].getText());
    spinner.setValueFactory(valueFactory);
	spinner.valueProperty().addListener(new ChangeListener<String>(){
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
				for(int j=0;j<month.length;j++){
					if(newValue.compareTo(month[j].getText()) == 0){
					  for(int i=0; i<dayNum[monthNum]; i++){
					    pane.getChildren().remove(B[i]);
						if(i == dayNo-1){B[i].setStyle("");}
					  }
					  monthNum = j; 
					  String monthName = month[monthNum].getText();
					  monthOut.setText(monthName);
					  LocalDate cal = LocalDate.of(2021,monthNum+1,1);
                      DayOfWeek day2 = cal.getDayOfWeek();
					  int rn=4;
	                  int cn=day2.getValue()+1;
					  String num = "";
					  LocalDate d = LocalDate.now();	
					  for(int i=0; i<dayNum[monthNum]; i++){
	                    if(cn==8){cn=1;}
	                    num+= (i+1);
	                    B[i].setText(num+"");			
	                    if(monthNum == d.getMonthValue()-1){
	                      if (i == dayNo-1){B[i].setStyle("-fx-background-color: #008080; ");}
	                    }
	                    pane.add(B[i],cn,rn,1,1);
	                    pane.setHalignment(B[i],HPos.CENTER);
	                    num = "";
	                    cn++;
	                    if(cn%8 == 0){cn=1; rn++;}
	                  }
					  break;
					}			
			    }
            }
        });
    pane.setPadding(new Insets(5));
	pane.add(label,0,1,3,1);
    pane.add(spinner,3,1,4,1);
	pane.add(monthOut,0,2,8,1);
	pane.setHalignment(monthOut,HPos.CENTER);
	LocalDate cal = LocalDate.of(2021,monthNum+1,1);
    DayOfWeek day2 = cal.getDayOfWeek();	
	for(int i=0;i<7;i++){
	  pane.add(day[i],i+1,3,1,1);
	  pane.setHalignment(day[i],HPos.CENTER);
	}
	int rn=4;
	int cn=day2.getValue()+1;
	String num = "";
	System.out.println(cn);
	for(int i=0; i<dayNum[monthNum]; i++){
	  if(cn==8){cn=1;}
	  num+= (i+1);
	  B[i] = new Button(num);
	  LocalDate d = LocalDate.now();
	  if(monthNum == d.getMonthValue()-1){
	    if (i == dayNo-1){B[i].setStyle("-fx-background-color: #008080; ");}
	  }
	  pane.add(B[i],cn,rn,1,1);
	  pane.setHalignment(B[i],HPos.CENTER);
	  num = "";
	  cn++;
	  B[i].setOnAction(this::stuff);
	  if(cn%8 == 0){cn=1; rn++;}
	}
	pane.setHgap(5);
	pane.setVgap(5);
    scene = new Scene(pane,265,305);
	primaryStage.setScene(scene);
	primaryStage.show();
  }
  
 public void start2(Stage primaryStage,String num){
    date = num + month[monthNum].getText() + ".txt";
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
	  pane.add(close,1,27,1,1);
	  pane.setHalignment(close,HPos.LEFT);
	  pane.add(edit,31,27,1,1);
	  pane.setHalignment(edit,HPos.RIGHT);
	  pane.setHgap(5);
	  pane.setVgap(5);
	  Scene scene = new Scene(pane,265,305);
	  primaryStage.setScene(scene);
	  primaryStage.show();
	}
	catch(FileNotFoundException e){
	  pane.add(s22,1,0);
	  pane.add(close,1,49);
	  pane.add(edit,27,49);
	  pane.setHgap(5);
	  pane.setVgap(5);
	  Scene scene = new Scene(pane,265,305);
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
      pane.add(close,1,16,1,1);
	  pane.setHalignment(close,HPos.LEFT);
	  pane.add(save,30,16,1,1);
	  pane.setHalignment(save,HPos.RIGHT);
	  pane.setHgap(5);
	  pane.setVgap(5);
	  Scene scene = new Scene(pane,265,305);
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
      pane.add(close,1,16,1,1);
	  pane.add(save,30,16,1,1);
	  pane.setHgap(5);
	  pane.setVgap(5);
	  Scene scene = new Scene(pane,265,305);
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
	
	
  