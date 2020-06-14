import java.sql.*;
import java.util.*;

public class Model{
    String url = "jdbc:oracle:thin:@127.0.0.1:1521:JAVA";
	Connection con;
    Statement stmt;

    Vector<String> tableList;

    Model(){
        tableList = new Vector<String>();

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("1. ����̹� �ε� ����.");
		}catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		}
    } //end of Model


    boolean connectOracle(String userName, String userPass){
        try{
            con = DriverManager.getConnection(url, userName, userPass); 
            System.out.println("2. Oracle DBMS�� ���� ����.");

            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                       ResultSet.CONCUR_UPDATABLE);
            System.out.println("3. Statement ���� ����.");
            return true;

        }catch(SQLException sqle){
            //sqle.printStackTrace();
            System.out.println("���̵�� ��й�ȣ�� Ȯ���ϼ���.");
            return false;
        }
    } //end of connectOracle

    void getTableList(){
        //select TNAME from TAB;
        ResultSet rs = null;
        tableList.clear();
        try{
            String sql = "select TNAME from TAB";
            rs = stmt.executeQuery(sql);
            rs.beforeFirst();
            while(rs.next() ) {
                tableList.add(rs.getString(1) );
            }
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }finally{
            try{
				if(rs != null) rs.close();
			}catch(SQLException sqle){
                sqle.printStackTrace();
            }
        }

        System.out.println("???");
        for(String str : tableList ){
            System.out.println(str);
        }
    }


    void closeAll(){
        try{
            if(stmt != null) stmt.close();
            if(con != null) con.close();
            System.out.println("DB ������ ���� ���� �Ǿ����ϴ�.");

        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
    } //end of closeAll


    public static void main(String[] args) {
        Model model = new Model();
        if( model.connectOracle("scott", "tiger") ) {
            System.out.println("������ �����߽��ϴ�.");
        }else{
            System.out.println("������ �����߽��ϴ�.");
        }

        model.closeAll();
    } //end of main

}