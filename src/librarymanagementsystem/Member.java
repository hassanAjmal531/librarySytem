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
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import static librarymanagementsystem.login.conn;
import static librarymanagementsystem.test.checkMemberType;

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
        conn c = new conn();
        
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
                if(checkfine(c, id)){
                quantity = checkQuantity(c,isbn);
                    if(quantity >=0){
                        this.issueBook(c, id, password, isbn,quantity, title);
                        return true;
                    }
                    else
                        JOptionPane.showMessageDialog(null, "book is not available");
                }
                   
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
            if(rs.next()){
                int quantity = rs.getInt(1);
                if(rs.getInt(1)>= 1)
                    return rs.getInt(1)-1;
            }   
                    
                
        }catch(Exception e){
            e.printStackTrace();
        }
        
            return 0;
    }
    
    public boolean checkfine(conn c, int id){
        try{
            PreparedStatement stmt = c.c.prepareStatement("select fine from member where id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next())
                if(rs.getInt(1) == 0)
                    return true;
                else 
                    System.out.println("you have fine");
            JOptionPane.showMessageDialog(null, "yu have some due fine, kindly clear the fine before proceeding");
                
                    
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
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
    
    public void returnBook(int id, int isbn, String title,String rDate){
        int days = utilities.getdifference(this.getReturnDate(id, isbn), rDate);
        int fine = 0;
        if(days == 0 || days < 0)
            fine = 0;
        else
            fine = this.calculateFine(days, id);
        
        conn c = new conn();
        int quantity = this.getQuantity(c, isbn) +1;
        String idate = this.getissueDate(id, isbn);
        String returnDate = this.getReturnDate(id, isbn);
        
        try{
            
            PreparedStatement stmt = c.c.prepareStatement("delete from borrowed where id = ? and book_isbn = ?");
            stmt.setInt(1, id);
            stmt.setInt(2, isbn);
            stmt.executeUpdate();
            
            stmt = c.c.prepareStatement("update book set quantity = ? where isbn =?");
            stmt.setInt(1, quantity);
            stmt.setInt(2, isbn);
            stmt.executeUpdate();
            
            stmt = c.c.prepareStatement("update member set fine = ? where id =?");
            stmt.setInt(1,fine);
            stmt.setInt(2,id);
            stmt.executeUpdate();
            
            stmt = c.c.prepareStatement("insert into history(title, issuedate, returndate, member_id) values (?,?,?,?)");
            stmt.setString(1,title);
            stmt.setString(2,idate);
            stmt.setString(3, returnDate);
            stmt.setInt(4, id);
            stmt.executeUpdate();
                    
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        
    }
    
    private String getReturnDate(int id, int isbn){
        conn c= new conn();
        try{
            PreparedStatement stmt = c.c.prepareStatement("select returndate from borrowed where id = ? and book_isbn = ?");
            stmt.setInt(1, id);
            stmt.setInt(2, isbn);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return rs.getString(1);
        
        }catch(Exception e){
            
            e.printStackTrace();
        }
        
        return null;
    }
    
    private String getissueDate(int id, int isbn){
        conn c= new conn();
        try{
            PreparedStatement stmt = c.c.prepareStatement("select issuedate from borrowed where id = ? and book_isbn = ?");
            stmt.setInt(1, id);
            stmt.setInt(2, isbn);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return rs.getString(1);
        
        }catch(Exception e){
            
            e.printStackTrace();
        }
        
        return null;
    }
    private int calculateFine(int days, int id){
        int fine = 0;
        try{
            conn c = new conn();
            
            PreparedStatement s = c.c.prepareStatement(String.format("select fine from member where id = %d",id));
            ResultSet rs = s.executeQuery();
            if(rs.next()){
                fine  = rs.getInt(1);
            }
        
        }catch(Exception e){
            e.printStackTrace();
        }
        return 100 *days + fine;
        
    }
    
    private int getQuantity(conn c, int isbn){
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
    
    private int getFine(conn c,int id){
        
        try{
            PreparedStatement stmt = c.c.prepareStatement("select fine from member where id = ?");
            stmt.setInt(1, id);
            ResultSet rs= stmt.executeQuery();
            if(rs.next())
                
                return rs.getInt(1);
                    
                
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public ResultSet displayHistory(int id){
        try{
            String sql = String.format("select title, issuedate, returndate from history where member_id = %d",id) ;
            Statement s = new conn().c.createStatement();
            
            
            ResultSet rs = s.executeQuery(sql) ;
            
            return rs;
        
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
        
    }
    
    public ResultSet currentlyBorrowed(int id){
        
        try{
            String sql = String.format("select book_isbn, title, issuedate, returndate from borrowed where id = %d",id) ;
            Statement s = new conn().c.createStatement();
            
            
            ResultSet rs = s.executeQuery(sql) ;
            
            return rs;
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    public ResultSet displayFine(int id){
        try{
            String sql = String.format("select id, fine from member where id =%d",id) ;
            Statement s = new conn().c.createStatement();
            
            
            ResultSet rs = s.executeQuery(sql) ;
            
            return rs;
        
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean deleteAccount(int id){
         conn c = new conn();
        int type = 0;
        boolean flag = false;
        PreparedStatement stmt;
        try{
            String sql = "";
            stmt = c.c.prepareStatement("delete from mlogin where member_id = ?");
            stmt.setInt(1, id);
            stmt.execute();
            type = checkMemberType(id);
            if(type == 1){
                sql = "delete from student where id = ?";
                flag = true;
            }
            else if(type == 2){
                sql = "delete from faculty where id = ?";
                flag = true;
            }
            else if(type == 3){
                System.out.println("type data not found");
                flag = false;
            }
            
            if(flag){
                stmt = c.c.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.execute();
            }
            
            stmt = c.c.prepareStatement("delete from member where id = ?");
            stmt.setInt(1, id);
            stmt.execute();
            
            
            
        
        }catch(Exception e){
            
            e.printStackTrace();
            System.out.println("cannot delete the account member must first return all the borrowed books");
        }
        return false;
    }
    
    private int checkMemberType(int id){
        
        try{
            PreparedStatement stmt = new conn().c.prepareStatement("select * from student where id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return 1;
            stmt = new conn().c.prepareStatement("select * from faculty where id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if(rs.next())
                return 2;
            else return 3;
            
            
            
            
            
            
        
        }catch(Exception e){
            
            e.printStackTrace();
            return 3;
        }
        
       
    }
    private boolean checkBook(int isbn){
        try{
            
        
        }catch(Exception e){
        
        }
    
        return false;
        
    }
    
    
}
