
import java.sql.Connection;
import java.sql.DriverManager;
public class MySqlConnection {

	public static Connection getConnection() {
		Connection con=null;
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        con=DriverManager.getConnection("jdbc:mysql://localhost:/hotelmanagement","root","root123");
        } 
        catch(Exception ex){
           System.out.println(ex.getMessage());
        }
        return con;
    }
	}

