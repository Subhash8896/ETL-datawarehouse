package etl;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
/**
 *
 * @author Subhash Nadkarni
 */
public class Methods {
    String[] name = new String[9];
    String[] post = new String[9];
    int[] salary = new int[9];
    String[] gender = new String[9];
    String[] tname = new String[9];
    String[] tpost = new String[9];
    int[] tsalary = new int[9];
    String[] tgender = new String[9];
    String[] lname = new String[9];
    String[] lpost = new String[9];
    int[] lsalary = new int[9];
    String[] lgender = new String[9];
    int count = 0;
    int tcount=0;
    int lcount=0;
    
    void extractaccess() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//"sun.jdbc.odbc.JdbcOdbcDriver"com.mysql.jdbc.Driver                
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver " + e);
        }
        //Define connection url
        try {
            String url = "jdbc:ucanaccess://C:/Users/Subhash Nadkarni/Documents/DatabaseEmp.accdb";
            //Establish open connection
            Connection conn = DriverManager.getConnection(url, "", "");
            //Create statement object
            Statement st = conn.createStatement();

            String query = "select * from Emp";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                name[count] = rs.getString("custname");
                post[count] = rs.getString("post");
                salary[count] = rs.getInt("salary");
                gender[count++] = rs.getString("gender");
            }
        } catch (Exception e) {
        }
    }
    
    void extractmysql() {
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp","root1","");
            Statement st = conn.createStatement();
            String query1 = "Select * from emp";
            ResultSet rs = st.executeQuery(query1);
            
            while (rs.next()) {
                name[count] = rs.getString("custname");
                post[count] = rs.getString("post");
                salary[count] = rs.getInt("salary");
                gender[count++] = rs.getString("gender");
            }
            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
        }

    }
    
    void extracttext() {
        try {
            FileInputStream fstream = new FileInputStream("C:/Users/Subhash Nadkarni/Desktop/data.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                name[count] = tokens[0];
                post[count] = tokens[1];
                salary[count] = Integer.parseInt(tokens[2]);
                gender[count++] = tokens[3];
            }
        } catch (Exception e) {
        }
    }
    
    void putaccess() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");                
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver " + e);
        }
        //Define connection url
        try {
            String url = "jdbc:ucanaccess://C:/Users/Subhash Nadkarni/Documents/DatabaseEmp.accdb";
            //Establish open connection
            Connection conn = DriverManager.getConnection(url, "", "");
            //Create statement object
            Statement st = conn.createStatement();

            for(int i=0;i<count;i++)
            {
           String query="INSERT into EmpTemp(custname,post,salary,gender) values ('"+name[i]+"','"+post[i]+"','"+salary[i]+"','"+gender[i]+"')";            
           st.executeUpdate(query);
                System.out.println("Dumped");
            }
        }
        catch (Exception e) {
        }
    }
    
    void transform(){
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");                
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver " + e);
        }
        //Define connection url
        try {
            String url = "jdbc:ucanaccess://C:/Users/Subhash Nadkarni/Documents/DatabaseEmp.accdb";
            //Establish open connection
            Connection conn = DriverManager.getConnection(url, "", "");
            //Create statement object
            Statement st = conn.createStatement();

            String query = "select * from EmpTemp";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                tname[tcount] = rs.getString("custname");
                tpost[tcount] = rs.getString("post");
                tsalary[tcount] = rs.getInt("salary");
                tgender[tcount] = rs.getString("gender");
// This transformation classifies salaries into classes 1,2 and 3. Gender is standardised to M/F format.
                if(tsalary[tcount]<50000)
                    tsalary[tcount]=3;
                else if(tsalary[tcount]>50000&&tsalary[tcount]<100000)
                    tsalary[tcount]=2;
                else
                    tsalary[tcount]=1;
                
                if(tgender[tcount].equals("male")||tgender[tcount].equals("m"))
                    tgender[tcount]="M";
                if(tgender[tcount].equals("female")||tgender[tcount].equals("f"))
                    tgender[tcount]="F";
                tcount++;
            }
        } catch (Exception e) {
        }
    }
    
    void dumptransform() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");                
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver " + e);
        }
        //Define connection url
        try {
            String url = "jdbc:ucanaccess://C:/Users/Subhash Nadkarni/Documents/DatabaseEmp.accdb";
            //Establish open connection
            Connection conn = DriverManager.getConnection(url, "", "");
            //Create statement object
            Statement st = conn.createStatement();
            for(int i=0;i<tcount;i++)
            {
                String query="INSERT into EmpTransform(custname,post,salary,gender) values ('"+tname[i]+"','"+tpost[i]+"','"+tsalary[i]+"','"+tgender[i]+"')";            
                st.executeUpdate(query);
                System.out.println("Dumped");
            }
        }
    catch (Exception e) {
        }
    }
    
    void forload() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");               
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver " + e);
        }
        //Define connection url
        try {
            String url = "jdbc:ucanaccess://C:/Users/Subhash Nadkarni/Documents/DatabaseEmp.accdb";
            //Establish open connection
            Connection conn = DriverManager.getConnection(url, "", "");
            //Create statement object
            Statement st = conn.createStatement();
            String query = "select * from EmpTransform";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                lname[lcount] = rs.getString("custname");
                lpost[lcount] = rs.getString("post");
                lsalary[lcount] = rs.getInt("salary");
                lgender[lcount++] = rs.getString("gender");
            }
        } catch (Exception e) {
        }
    }
    
    void dumpload() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");               
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver " + e);
        }
        //Define connection url
        try {
            String url = "jdbc:ucanaccess://C:/Users/Subhash Nadkarni/Documents/DatabaseEmp.accdb";
            //Establish open connection
            Connection conn = DriverManager.getConnection(url, "", "");
            //Create statement object
            Statement st = conn.createStatement();
            for(int i=0;i<lcount;i++)
            {
                String query="INSERT into EmpFinal(custname,post,salary,gender) values ('"+lname[i]+"','"+lpost[i]+"','"+lsalary[i]+"','"+lgender[i]+"')";            
                st.executeUpdate(query);
                System.out.println("Dumped");
            }
        }
        catch (Exception e) {
        }
    }
}
