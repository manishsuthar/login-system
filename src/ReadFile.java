
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.SwingConstants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//Class Start
public class ReadFile{
	//Start MD5 Encryption Function
	public static String myMD5(String password) {
        final byte[] defaultBytes = password.getBytes();
        try {
            final MessageDigest md5MsgDigest = MessageDigest.getInstance("MD5");
            md5MsgDigest.reset();
            md5MsgDigest.update(defaultBytes);
            final byte messageDigest[] = md5MsgDigest.digest();
            final StringBuffer hexString = new StringBuffer();
            for (final byte element : messageDigest) {
                final String hex = Integer.toHexString(0xFF & element);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            password = hexString + "";
        } catch (final NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        return password;
    }
	//End of MD5 Encrypt Function
	
	//Password Validating Function
    public static boolean CheckPassword(String password){
    if(password.matches(".*[0-9]{1,}.*") && password.matches(".*[@#$]{1,}.*") && password.length()>=6 && password.length()<=20)
       { return true; }
    else
        { return false; }       
    }
    //End Password Validating Function
    //Email Address Validation Function
    public static boolean CheckEmail(String email){
        if(email.matches("^(.+)@(.+)$"))
           { System.out.print("True");
               return true;}
        else
           { System.out.print("False");
               return false; }
    }
    //Email Validation End
    //Mobile Number Validation
    public static boolean Checkphone(String phone){
        if(phone.matches("^[789]\\d{9}$")){ return true;}     
        else{ return false; }
    }
    //End of Mobile Number Validation
    //Name Getting Function
    public static String getFname(){
        System.out.println("\nEnter First Name\n");
        Scanner fname = new Scanner(System.in);
        String fnameValue = fname.next();
        return fnameValue; 
    }
    //End Name Getting Function
    //Last Name Gettig Function
    public static String getlname(){
        System.out.println("\nEnter Last Name\n");
        Scanner lname = new Scanner(System.in);
        String lnameValue = lname.next();
        return lnameValue; 
    }
    //End of Lastname getting Function
    //Email Getting
    public static String getEmail(){
        System.out.println("\n Enter Email \n");
        Scanner email = new Scanner(System.in);
        String newemail = email.next();
        boolean chk = CheckEmail(newemail);
        if(chk){ return newemail; }
        else{ 
            System.out.println("\n In-Valid Email Address Please Correct it. \n");
            return getEmail();
        }
    }
    //End of Email Getting
    //Phone Number Getting
    public static String getPhone(){
        System.out.println("Enter Phone");
        Scanner phone = new Scanner(System.in);
        String newphone = phone.next();
        boolean chk = Checkphone(newphone);
        if(chk){ return newphone; }
        else{
          System.out.println("\n In-Valid Phone Number Please Correct it. \n");  
          return getPhone();
        }
    }
    //End Phone Number Getting
    //Password Getting
    public static String getPassword(){
        System.out.println("Enter Password");
        System.out.println("\nPassword must included \n Special Char \n Length minimum 6 Char \n With Include one Number\n");
        Scanner pass = new Scanner(System.in);
        String newpass = pass.next();
        boolean chk = CheckPassword(newpass);
        if(chk){ return newpass; }
        else{
          System.out.println("\n In-Valid Password. \n");  
          return getPassword();
        }
    }
    //End Password Getting
    //Auth User Start
    public static boolean Authuser(String pass) {
    	System.out.println("\n Enter Old Password for Validate user \n");
    	Scanner validpass = new Scanner(System.in);
    	String newvalidpass = myMD5(validpass.next());
    	if(newvalidpass.equals(pass)) {
    		return true;
    	}else {
    		return false;
    	}
    }
    //End Auth user
    //Sign Up Function
    @SuppressWarnings("unused")
	public static void SignUp(){
        System.out.println("\n Welcome to Sign Up Page \n \nIt Will Create you as a new User \n");
        String fname = getFname();
        String lname = getlname();
        String email = getEmail();
        String phone = getPhone();
        String password = myMD5(getPassword());
        String sql2 = "Select `email` from user";
        Connection con1 = null;
        Statement stmt1 = null;
        ResultSet rs1 = null;
        try {
        	Boolean unq = false;
        	con1 = DbConnection.getConnection();
			stmt1 = con1.createStatement();
			rs1 = stmt1.executeQuery(sql2);
			ResultSetMetaData rsmd = rs1.getMetaData(); 
			int columnCount = rsmd.getColumnCount();
			ArrayList<String> resultList= new ArrayList<>(columnCount); 
			while (rs1.next()) {
			   int i = 1;
			   while(i <= columnCount) {
			        resultList.add(rs1.getString(i++));
			   }
			}
			for(int i=0;i<resultList.size();i++){
				if((resultList.get(i)).equalsIgnoreCase(email)) {
					unq = true;
				}
		   }
			if(!unq){
		        String sql = "INSERT INTO `user`(`fname`, `lname`, `email`, `phone`, `password`) VALUES ('"+fname+"','"+lname+"','"+ email +"','"+ phone +"','"+password+"')";
		        Connection con = null;
				Statement stmt = null;
				try 
				{
					con = DbConnection.getConnection();
					stmt = con.createStatement();
					stmt.executeUpdate(sql);
					System.out.println("\n User Insert Successfully \n");
					System.exit(1);
				}
				catch (SQLException ex) 
				{
		           ex.printStackTrace();
				}
			}else {
				
				System.out.println("\n Email Address is Already in User You Must Be user Unique Email Address \n");
				
		RESET: System.out.println("\n if you Want to Login then Press 1 and for SignUp Press 2");
				Scanner chos = new Scanner(System.in);
				int chosse = chos.nextInt();
				switch (chosse) {
				case 1:
					SignIn();
					break;
				case 2:
					SignUp();
					break;
				default:
					break;
//					goto RESET 
//					break RESET
					
				}
			}
        }catch (SQLException e) {
        	e.printStackTrace();
		}
    }
    //End Sign Up Function
    //Editing Profile Function
    public static void EditProfile(int idp,String password){
        System.out.println("\n Choose what you Want to Update ?? \n");
        System.out.println("\n 1: First Name \n 2: Last Name\n 3:Phone\n");
        Scanner choose = new Scanner(System.in);
        int chic = choose.nextInt();
        Connection con = null;
		Statement stmt = null;
		String sql = null;
        switch (chic) {
		case 1:
			String fname = getFname();
			if(Authuser(password)){
				sql = "UPDATE `user` SET `fname`= '"+ fname +"' WHERE `_id` ="+idp;
				try 
				{
					con = DbConnection.getConnection();
					stmt = con.createStatement();
					stmt.executeUpdate(sql);
					System.out.println("Operation Successfully Done !!!");
				}
				catch (SQLException ex) 
				{
		           ex.printStackTrace();
				}
				break;
			}else {
				System.out.println("\n In-Valid User Please confirm passwor and try again later \n");
				break;
			}
			
		case 2:
			String lname = getlname();
			if(Authuser(password)){
				sql = "UPDATE `user` SET `lname`= '"+ lname +"' WHERE `_id` ="+idp;
				try 
				{
					con = DbConnection.getConnection();
					stmt = con.createStatement();
					stmt.executeUpdate(sql);
					System.out.println("Operation Successfully Done !!!");
				}
				catch (SQLException ex) 
				{
		           ex.printStackTrace();
				}
				break;
			}else {
				System.out.println("\n In-Valid User Please confirm passwor and try again later \n");
				break;
			}
			
		case 3:
			String phone = getPhone();
			if(Authuser(password)){
				sql = "UPDATE `user` SET `phone`= '"+ phone +"' WHERE `_id` ="+idp;
				try 
				{
					con = DbConnection.getConnection();
					stmt = con.createStatement();
					stmt.executeUpdate(sql);
					System.out.println("Operation Successfully Done !!!");
				}
				catch (SQLException ex) 
				{
		           ex.printStackTrace();
				}
				break;
			}else {
				System.out.println("\n In-Valid User Please confirm passwor and try again later \n");
				break;
			}
			
		}
        System.out.println("\n Update anything Else then Press 1 otherwise press 2 \n");
        Scanner choose1 = new Scanner(System.in);
        int chic1 = choose.nextInt();
        switch(chic1){
           case 1:
        	   EditProfile(idp,password);
        	   break;
           case 2:
        	   System.out.println("@@@@@ Good Bye @@@@@");
        	   System.exit(1);
        	   break;
        }
        
    }
    //End Edit Profile
    //Change Password Function
    public static void ChangePassword(int idp,String password) {
    	String pass = myMD5(getPassword());
    	if(Authuser(password)) {
    		Connection con = null;
    		Statement stmt = null;
        	String sql = "UPDATE `user` SET `password`= '"+ pass +"' WHERE `_id` ="+idp;
    		try 
    		{
    			con = DbConnection.getConnection();
    			stmt = con.createStatement();
    			stmt.executeUpdate(sql);
    			System.out.println("\n Password Has been Successfully Changed\n");
    			System.out.println("\nGood Bye\n");
    		}
    		catch (SQLException ex) 
    		{
               ex.printStackTrace();
    		}
    	}else {
    		System.out.println("\n In-Valid User Please confirm passwor and try again later \n");
    		System.exit(1);
    	}
    	
    }
    //End Change Password
    @SuppressWarnings("null")
    //Sign In Function
	public static void SignIn(){
        System.out.print("\nWelcome to System Sign In Portal\n");
        System.out.print("\n Enter Valid Email\n");
        Scanner uname = new Scanner(System.in);
        String newuname = uname.next();
        String sql = "Select * from user where `email`='"+ newuname +"'";
        Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
        try 
		{
			con = DbConnection.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData(); 
			int columnCount = rsmd.getColumnCount();
			 
			ArrayList<String> resultList= new ArrayList<>(columnCount); 
			while (rs.next()) {
			   int i = 1;
			   while(i <= columnCount) {
			        resultList.add(rs.getString(i++));
			   }
			}

			if(resultList.size() > 0) {
//				for(int i=0;i<resultList.size();i++){
//				System.out.println(i);
//				System.out.println(resultList.get(i));
//			 }
				System.out.print("\n Enter Password\n");
				Scanner password = new Scanner(System.in);
		        String newpassword = myMD5(password.next());
		        if(newpassword.equals(resultList.get(5))) {
		           System.out.print("\n Login Successfull \n");	
		           System.out.format("+-----------+-----------+------------------------------------+----------------------+%n");
		           System.out.format("| FirstName | Last Name |               Email                |        Phone         |%n");
		           System.out.format("+-----------+-----------+------------------------------------+----------------------+%n");
				   System.out.println("|"+resultList.get(1)+"|"+resultList.get(2)+"|"+resultList.get(3)+"|"+resultList.get(4)+"|");
				   System.out.format("+-----------+-----------+------------------------------------+----------------------+%n");
		           System.out.println("Press 1 to Edit your Profile or Press 2 for Change Password");
		           Scanner choice = new Scanner(System.in);
		           int newchoice = choice.nextInt();
		           switch (newchoice) {
				case 1:
					EditProfile(Integer.parseInt(resultList.get(0)),resultList.get(5));
					break;
				case 2:
					ChangePassword(Integer.parseInt(resultList.get(0)),resultList.get(5));
					break;
				}
		        }else {
		        	System.out.println("\n Invalid Password \n");
		        	SignIn();
		        }
			}else {
				System.out.println("\nIn-Valid User Name\n");
				SignIn();
			}
		}
		catch (SQLException ex) 
		{
           ex.printStackTrace();
           return;
		}
//        if(){
//
//        }
//        else{
//            System.out.print("\nInvalid user Press 1 to Exit and Press 2 for Sign Up\n");
//            Scanner invalided = new Scanner(System.in);
//            int newinvalied = invalided.nextInt();
//            switch(newinvalied){
//                case 1 : {
//                        return ;
//                    }
//                case 2 : {
//                        SignUp();
//                    }
//            }
//        }
    }
   //End Sign In Function

    //Main Function Start
    public static void main(String[] args){
        System.out.print("\n Welcome to Login System \n");
	        System.out.print("\nEnter 1 for Login And Enter 2 for Sign up\n");
	        Scanner ls = new Scanner(System.in);
	        int lsvalue = ls.nextInt();
	        if(lsvalue == 1){
	            SignIn();
	        }else{
	            SignUp();
	        }
        
    }
    //Done Executation
}