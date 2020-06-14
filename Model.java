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
            System.out.println("1. 드라이버 로딩 성공.");
		}catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		}
    } //end of Model


    boolean connectOracle(String userName, String userPass){
        try{
            con = DriverManager.getConnection(url, userName, userPass); 
            System.out.println("2. Oracle DBMS와 연결 성공.");

            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                       ResultSet.CONCUR_UPDATABLE);
            System.out.println("3. Statement 생성 성공.");
            return true;

        }catch(SQLException sqle){
            //sqle.printStackTrace();
            System.out.println("아이디와 비밀번호를 확인하세요.");
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
            System.out.println("DB 연결이 정상 종료 되었습니다.");

        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
    } //end of closeAll


    public static void main(String[] args) {
        Model model = new Model();
        if( model.connectOracle("scott", "tiger") ) {
            System.out.println("연결이 성공했습니다.");
        }else{
            System.out.println("연결이 실패했습니다.");
        }

        model.closeAll();
    } //end of main

}