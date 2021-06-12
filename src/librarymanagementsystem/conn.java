/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.sql.*;
public class conn {
    Connection c;
    Statement s;
    PreparedStatement p;
    public conn(){
        try{  
//step1 load the driver class  
Class.forName("oracle.jdbc.driver.OracleDriver");  
  
//step2 create  the connection object  
this.c =DriverManager.getConnection(  
"jdbc:oracle:thin:@localhost:1521:orcl","hassan","july102001");  
  
//step3 create the statement object  

this.s=c.createStatement();  
  
//step4 execute query  
 
  
//step5 close the connection object  

  
}catch(Exception e){ System.out.println(e);}
    
        
    }
    public static void main(String args[]){ 


} 
}
 
    
    

