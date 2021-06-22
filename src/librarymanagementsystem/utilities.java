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
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Dell
 */
public class utilities {
    
    public static String[] searchByISBN(int isbn){
        String values[] = new String [7];
        try{
            conn c  = new conn();
            String sql = "select book.isbn, book.title, book.language, book.quantity, publisher.name from book, publisher where  book.isbn = publisher.book_isbn and book.isbn =?";
            PreparedStatement s = c.c.prepareStatement(sql);
            s.setInt(1, isbn);
            ResultSet rs =s.executeQuery();
            
            
            if(rs.next()){
                
                for(int i = 0; i < 5; i++){
                    values[i] = rs.getString(i+1);
                }
                sql = "select  listagg(author.name, ', ') as authors from author where author.id in (select hasauthor.author_id from hasauthor, book where book.isbn = hasauthor.book_isbn  and book.isbn = ?)";
                s = c.c.prepareStatement(sql);
                s.setInt(1,isbn);
                rs = s.executeQuery();
                
                if(rs.next())
                    values[5] = rs.getString(1);
                
                sql = "select listagg(category.cname,', ') from category where category.id in (select hascategory.cid from hascategory, book where book.isbn = hascategory.bookid and book.isbn = ?)";
                s = c.c.prepareStatement(sql);
                s.setInt(1,isbn);
                rs = s.executeQuery();
                
                if(rs.next())
                    values[6] = rs.getString(1);
                
            }else{
                System.out.println("dfg");
            }
            return values;
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static String[] searchByName(String title){
    
        String values[] = new String [7];
        int isbn = getisbn(title);
        try{
            conn c  = new conn();
            String sql = "select book.isbn, book.title, book.language, book.quantity, publisher.name from book, publisher where  book.isbn = publisher.book_isbn and book.isbn = ?";
            PreparedStatement s = c.c.prepareStatement(sql);
            s.setInt(1, isbn);
            ResultSet rs =s.executeQuery();
            
            
            if(rs.next()){
                
                for(int i = 0; i < 5; i++){
                    values[i] = rs.getString(i+1);
                }
                isbn = Integer.valueOf(values[0]);
                sql = "select  listagg(author.name, ', ') as authors from author where author.id in (select hasauthor.author_id from hasauthor, book where book.isbn = hasauthor.book_isbn  and book.isbn = ?)";
                s = c.c.prepareStatement(sql);
                s.setInt(1,isbn);
                rs = s.executeQuery();
                
                if(rs.next())
                    values[5] = rs.getString(1);
                
                sql = "select listagg(category.cname,', ') from category where category.id in (select hascategory.cid from hascategory, book where book.isbn = hascategory.bookid and book.isbn = ?)";
                s = c.c.prepareStatement(sql);
                s.setInt(1,isbn);
                rs = s.executeQuery();
                
                if(rs.next())
                    values[6] = rs.getString(1);
                
            }else{
                JOptionPane.showMessageDialog(null, "no book of this name exists");
            }
            return values;
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static int getisbn(String title){
        try{
            PreparedStatement s= new conn().c.prepareStatement("select isbn, title from book");
            ResultSet rs = s.executeQuery();
            
            while(rs.next()){
                if(rs.getString(2).equals(title))
                    return rs.getInt(1);
            }
        
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    
    }
    
    
    
    
    
    public static String DateToString(Date date){
        
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }
    
    public static Date toDate(String string){
        try{
        return new SimpleDateFormat("dd/MM/yyyy").parse(string); 
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static int getdifference(String d1, String d2){
        
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
	 String dateBeforeString = d1;
	 String dateAfterString = d2;
         Date dateBefore = null;

	 try {
	       dateBefore = myFormat.parse(dateBeforeString);
	       Date dateAfter = myFormat.parse(dateAfterString);
	       long difference = dateAfter.getTime() - dateBefore.getTime();
	       float daysBetween = (difference / (1000*60*60*24));
               int day = (int) daysBetween;
               
                
	      return day;
	} catch (Exception e) {
	       e.printStackTrace();
	 }
        
        return 0;
    }
    
    public static ResultSet displayBook(){
        
        try{
            PreparedStatement stmt = new conn().c.prepareStatement("select * from book");
            return stmt.executeQuery();
            
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    public static boolean checkInteger(String s){
        try{
            
            int i = Integer.valueOf(s);
            return true;
        
        }catch(NumberFormatException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "isbn and quantity must be in digits");
        }
    
        return false;
    }
    
    public static boolean checkemail(String email){
        String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        
        return matcher.matches();
        
    }
    
    public static boolean checkPhoneNumber(String phone){
        int l = phone.length();
        
        return checkInteger(phone) && phone.length() == 11;
        
        
        
    
        
    }
    
  /*  public static String[] viewAllbooks(){
    
         String values[] = new String [7];
        try{
            conn c  = new conn();
            String sql = "select book.isbn, book.title, book.language, book.quantity, publisher.name from book, publisher where  book.isbn = publisher.book_isbn";
            PreparedStatement s = c.c.prepareStatement(sql);
            
            ResultSet rs =s.executeQuery();
            
            
            while(rs.next()){
                
                for(int i = 0; i < 5; i++){
                    values[i] = rs.getString(i+1);
                }
                sql = "select  listagg(author.name, ', ') as authors from author where author.id in (select hasauthor.author_id from hasauthor, book where book.isbn = hasauthor.book_isbn  and book.isbn = ?)";
                s = c.c.prepareStatement(sql);
                s.setInt(1,Integer.valueOf(values[0]));
                ResultSet rs1 = s.executeQuery();
                
                if(rs1.next())
                    values[5] = rs.getString(1);
                
                sql = "select listagg(category.cname,', ') from category where category.id in (select hascategory.cid from hascategory, book where book.isbn = hascategory.bookid and book.isbn = ?)";
                s = c.c.prepareStatement(sql);
                s.setInt(1,Integer.valueOf(values[0]));
                ResultSet rs2 = s.executeQuery();
                
                if(rs2.next())
                    values[6] = rs.getString(1);
                
                for(String i : values)
                    System.out.println(i);
                System.out.println();
                
            }
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }*/

        
    }
    

