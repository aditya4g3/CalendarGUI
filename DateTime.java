import java.util.*;
import java.text.*;
import java.time.Instant;

public class DateTime{

  public static void main(String[] args){
	  long start = Instant.now().toEpochMilli();
	  Scanner sc = new Scanner(System.in);
	  int t=0;
	  while(t!=9){
		  t = sc.nextInt();
	  }
      long finish = Instant.now().toEpochMilli();
	  NumberFormat formatter = NumberFormat.getNumberInstance();
	  formatter.setMaximumFractionDigits(2);
	  formatter.setMinimumFractionDigits(2);
	  long time = finish-start;
	  System.out.println(formatter.format((double)(finish-start)/1000));	
  }
  
} 
