/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;

/**
 *
 * @author Dell
 */
public class test {
    public static void main(String arg[]){
        dateTest();
    }
    
    public static boolean insertAuthor(String name, int isbn,String title){
        conn c= new conn();
        boolean flag = true;
        boolean flag2 = true;
        boolean flag3 =true;
        int AId = 0;
        String sql = "";
        PreparedStatement stmt;
        PreparedStatement stmt2;
        int CId = 0;
        while(flag2){
            try{
                Statement s = c.c.createStatement();
                ResultSet rs=s.executeQuery("select * from author");
                while (rs.next()){
                    System.out.println(rs.getString(2));
                    if(rs.getString(2).equals(name)){
                        System.out.print(name);
                        CId = rs.getInt(1);
                        flag = false;
                        break;

                    }
                }
                if(flag){
                    AId = getSeqValue(c);
                    sql = String.format("insert into author (id,name) values(%d, '%s')",AId,name );
                    
                    stmt2 = c.c.prepareStatement(sql);
                    
                    stmt2.executeUpdate(sql);
                    flag3 = true;

                }
                else if(!flag){
                    sql = "insert into hasauthor (book_isbn, title, author_id) values (?,?,?)";
                    stmt = c.c.prepareStatement(sql);
                    stmt.setInt(1, isbn);
                    stmt.setString(2, title);
                    stmt.setInt(3, CId);
                    stmt.executeUpdate();
                    if(flag3){
                        flag2 = false;
                    }
                        

                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    public static int getSeqValue(conn c){
        
        try{
            ResultSet rs = c.s.executeQuery("select incauthor.NEXTVAL from dual");
            if(rs.next())
                return rs.getInt(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public static void dateTest(){
       /* SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
	 String dateBeforeString = "29 01 2014";
	 String dateAfterString = "02 02 2014";
         Date dateBefore = null;

	 try {
	       dateBefore = myFormat.parse(dateBeforeString);
	       Date dateAfter = myFormat.parse(dateAfterString);
	       long difference = dateAfter.getTime() - dateBefore.getTime();
	       float daysBetween = (difference / (1000*60*60*24));
               int day = (int) daysBetween;
               /* You can also convert the milliseconds to days using this method
                * float daysBetween = 
                *         TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
                */
	    /*   System.out.println("Number of Days between dates: "+day);
	} catch (Exception e) {
	       e.printStackTrace();
	 }*/
         
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Using today's date
        c.add(Calendar.DATE, 5); // Adding 5 days
        String output = sdf.format(c.getTime());
        System.out.println(output);
    }
    
    
    
}
