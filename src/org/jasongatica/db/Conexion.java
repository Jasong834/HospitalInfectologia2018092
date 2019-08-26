package org.jasongatica.db;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.jdbc.Driver;

public class Conexion {
    private Connection Conexion;
    private static Conexion instancia;
    
    private String driver;
    private String url;
    private String usuario;
    private String password;
    private String dbname;
 
public Conexion() { 
    String dbname = "DBHospitalInfectologia2018092";
    String url = "jdbc:mysql://localhost:3306/"+dbname+"?useSSl=false&zeroDateTimeBehavior=convertToNull";
    String driver = "com.mysql.jdbc.Driver";
    String usuario = "root";
    String password = "admin"; 
     
    try{
        Class.forName("com.mysql.jdbc.Driver").newInstance();  
        
        
        Conexion=DriverManager.getConnection(url, usuario, password);        
       
    }catch(ClassNotFoundException e){ 
            e.printStackTrace();
           e.getMessage();
    }catch(InstantiationException e){ 
            e.printStackTrace();
            e.getMessage();
            
    }catch(IllegalAccessException e){ 
            e.printStackTrace();
            e.getMessage();
    }catch(SQLException e){ 
            e.printStackTrace();
            e.getMessage();
            } 
    }  
    public static Conexion getInstancia(){ 
        if(instancia == null){ 
            instancia = new Conexion();
        } 
        return instancia;
    } 


    public Connection getConexion() {
        return Conexion;
    }

    public void setConexion(Connection conexion) {
        this.Conexion = conexion;
    }
   
}
    



