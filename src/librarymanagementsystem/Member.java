/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import static librarymanagementsystem.login.conn;

/**
 *
 * @author HP
 */
public class Member extends person {
    
    public Member(){}
    
    public Member(int id, String fname, String lname, String email, String contact, String address){
        super(id, fname, lname, email, contact,address);
    }
    
    public Member(int id, String fname, String lname, String email, String contact, String address, String password){
        super(id, fname, lname, email, contact,address, password);
    }
    
    public void checkhistory(){}
    public void logout(){}
    public boolean login(int id, String password){
        PreparedStatement s;
        
          try{
              ResultSet rs;
              
              String sql = "select * from mlogin where member_id = ? ";
              s = conn.c.prepareStatement(sql);
              s.setInt(1,id);
              rs = s.executeQuery();
              if(rs.next()){
                  
                  
                  if( rs.getString(1).equals(password)){
                      return true;
                      
                  }
              }
              
              
          }catch(Exception e){
              e.printStackTrace();
          
          }
          return false;
    }
    
    public boolean borrow(int id, String password, int isbn, String title){
        conn c = new conn();
        int quantity = 0;
        if(this.login(id, password)){
            if(checkAvailabilty(c, isbn))
                quantity = checkQuantity(c,isbn);
                this.issueBook(c, id, password, isbn,quantity, title);
                   
        }
        
    
        return false;
    }
    
    private boolean checkAvailabilty(conn c,int isbn){
        try{
            PreparedStatement stmt = c.c.prepareStatement("select * from book where isbn = ?");
            stmt.setInt(1, isbn);
            ResultSet rs= stmt.executeQuery();
            if(rs.next())
                return true;
            else{
                JOptionPane.showMessageDialog(null, "book not available in the library");
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    private int checkQuantity(conn c, int isbn){
        try{
            PreparedStatement stmt = c.c.prepareStatement("select quantity from book where isbn = ?");
            stmt.setInt(1, isbn);
            ResultSet rs= stmt.executeQuery();
            if(rs.next())
                if(rs.getInt(1)>= 1)
                    return rs.getInt(1)-1;
                    
                
        }catch(Exception e){
            e.printStackTrace();
        }
        
            return 0;
    }
    
    public ArrayList<String> getDates(){
        ArrayList<String> dates = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); 
        
        String output = sdf.format(c.getTime());
        
        dates.add(sdf.format(c.getTime()));
        c.add(Calendar.DATE, 5);
        dates.add(sdf.format(c.getTime()));
        
        
        
        return dates;
    }
    
    public void issueBook(conn c,int id, String password, int isbn,int quantity, String title){
        ArrayList<String> arr = this.getDates();
        String issueDate = arr.get(0);
        String returnDate = arr.get(1);
        try{
            String sql = "insert into borrowed(id,book_isbn, title, issuedate, returndate) values (?,?,?,?,?)";
            PreparedStatement stmt = c.c.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setInt(2, isbn);
            stmt.setString(3,title );
            stmt.setString(4, issueDate);
            stmt.setString(5, returnDate);
            stmt.executeUpdate();
            
            sql = "update book set quantity = ? where isbn =?";
            stmt = c.c.prepareStatement(sql);
            stmt.setInt(1, quantity);
            stmt.setInt(2, isbn);
            stmt.executeUpdate();
            
            
            
            JOptionPane.showMessageDialog(null, String.format("book issued sucessfully.....\n ISSUE DATE: %s\n Return Date: %s", issueDate, returnDate));
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    
}
