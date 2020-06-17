import java.sql.*;
import java.util.*;

public class Model{
    String url = "jdbc:oracle:thin:@127.0.0.1:1521:JAVA";
	Connection con;
    Statement stmt;
    String sql_select_table = "select * from ";

    Vector<String> tableList;

    Model(){
        tableList = new Vector<String>();

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("1. 드라이버 로딩 성공.");
		}catch(final ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		}
    } //end of Model

    boolean connectOracle(final String userName, final String userPass){
        try{
            con = DriverManager.getConnection(url, userName, userPass); 
            con.setAutoCommit(false);
            System.out.println("2. Oracle DBMS와 연결 성공.");

            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                       ResultSet.CONCUR_UPDATABLE);
            System.out.println("3. Statement 생성 성공.");
            return true;

        }catch(final SQLException sqle){
            //sqle.printStackTrace();
            System.out.println("아이디와 비밀번호를 확인하세요.");
            return false;
        }
    } //end of connectOracle

    ResultSetMetaData rsmd;
    Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
    Vector<Vector<Object>> getRowData(){
        return rowData;
    }

    Vector<String> columnNames = new Vector<String>();
    Vector<String> getColumnNames(){
        return columnNames;
    } //end of getColumnNames

    void selectTable(final String tname){
        ResultSet rs = null;
        int number = 0;
        double dNumber = 0.0D;
        java.util.Date date = new java.util.Date();
        Timestamp timeStamp = new Timestamp(0);
        String str = "";

        columnNames.clear();
        rowData.clear();

        try{
            rs = stmt.executeQuery(sql_select_table+tname);
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            for(int i=1; i<=rsmd.getColumnCount(); i++){
                columnNames.add( rsmd.getColumnName(i) );
            }

            while(rs.next() ) {
                Vector<Object> unitVector = new Vector<Object>();
                for(int i=1; i<=columnCount; i++){
                    switch(rsmd.getColumnType(i)){
                        case Types.NUMERIC:
                        case Types.INTEGER:
                            number = rs.getInt(i);
                            unitVector.add(number);
                            break;
                        case Types.FLOAT:
                        case Types.DOUBLE:
                            dNumber = rs.getDouble(i);
                            unitVector.add(dNumber);
                            break;
                        case Types.DATE:
                            date = rs.getDate(i);
                            unitVector.add(date);
                            break;
                        case Types.TIMESTAMP:
                            timeStamp = rs.getTimestamp(i);
                            unitVector.add(timeStamp);
                            break;
                        case Types.NULL:
                            unitVector.add("");
                            break;
                        default:
                            str = rs.getString(i);
                            unitVector.add(str);
                            break;
                    }
                } //end of for

                rowData.add(rs.getRow()-1, unitVector);
            } //end of while

            //데이터 확인용
            //for(Vector<Object> v : rowData){
            //    System.out.println(v);
            //}

        }catch(final SQLException sqle){
            sqle.printStackTrace();
        }finally{
            try{
				if(rs != null) rs.close();
			}catch(final SQLException sqle){
                sqle.printStackTrace();
            }
        }
    } //end of selectTable

    void getTableList(){
        //select TNAME from TAB;
        ResultSet rs = null;
        tableList.clear();
        try{
            final String sql = "select TNAME from TAB";
            rs = stmt.executeQuery(sql);
            rs.beforeFirst();
            while(rs.next() ) {
                final String str = rs.getString(1);
                if(!str.startsWith("BIN$")) tableList.add( str );
            }
        }catch(final SQLException sqle){
            sqle.printStackTrace();
        }finally{
            try{
				if(rs != null) rs.close();
			}catch(final SQLException sqle){
                sqle.printStackTrace();
            }
        }
    } //end of getTableList


    void closeAll(){
        try{
            if(stmt != null) stmt.close();
            if(con != null) con.close();
            System.out.println("DB 연결이 정상 종료 되었습니다.");

        }catch(final SQLException sqle){
            sqle.printStackTrace();
        }
    } //end of closeAll


    public static void main(final String[] args) {
        final Model model = new Model();
        if( model.connectOracle("scott", "tiger") ) {
            System.out.println("연결이 성공했습니다.");
        }else{
            System.out.println("연결이 실패했습니다.");
        }

        model.closeAll();
    } //end of main

}