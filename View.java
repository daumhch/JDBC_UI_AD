import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;

public class View {
    private JFrame mainFrame; //나는 JFrame 상속 받는게 싫다. 그래서 객체 선언해서 사용했다.
    private final int MAIN_WIDTH = 600; //화면 기본 너비
    private final int MAIN_HEIGHT = 600; //화면 기본 높이
    private Container container;

    View(){
        mainFrame = new JFrame("JTable example");
        mainFrame.setSize(new Dimension(MAIN_WIDTH+mainFrame.getInsets().left, 
                                        MAIN_HEIGHT+mainFrame.getInsets().top) );
        mainFrame.setLocationRelativeTo(null); //mainFrame.setLocation(50, 50);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    //화면구조
    JPanel mainPanel;
        JPanel loginPanel;
            JLabel userNameLabel;
            JTextField userNameInput;
            JLabel userPassLabel;
            JTextField userPassInput;
            JButton connectButton;
        JPanel dbTreePanel;
            JScrollPane jspList;
                JList<String> dbTreeList;
                    DefaultListModel<String> dlm;
        JPanel dbTablePanel;
            JScrollPane jspTable;
                JTable dbTable;
                    DefaultTableModel dtmTable;

    void initUI(){
        container = mainFrame.getContentPane();
        mainPanel = new JPanel();
        mainPanel.setLayout( new BorderLayout() );
        
        loginPanel = new JPanel();
        loginPanel.setLayout( new GridLayout(1,5, 10, 0) );
        loginPanel.setBorder( BorderFactory.createEmptyBorder(10, 10, 10, 10) );
            userNameLabel = new JLabel("insert ID:");
            userNameLabel.setHorizontalAlignment(JLabel.RIGHT);;
            userNameInput = new JTextField();
            userPassLabel = new JLabel("insert Pass:");
            userPassLabel.setHorizontalAlignment(JLabel.RIGHT);;
            userPassInput = new JTextField();
            connectButton = new JButton("connect");
            loginPanel.add( userNameLabel, 0);
            loginPanel.add( userNameInput, 1);
            loginPanel.add( userPassLabel, 2);
            loginPanel.add( userPassInput, 3);
            loginPanel.add( connectButton, 4);
        mainPanel.add(loginPanel, BorderLayout.NORTH);

        dbTreePanel = new JPanel();
        dbTreePanel.setBackground(Color.WHITE);
        dbTreePanel.setLayout(new CardLayout());
        int loginPanelHeight = (int)loginPanel.getPreferredSize().getHeight();
        dbTreePanel.setPreferredSize(new Dimension(150, 600-loginPanelHeight ) );
            dlm = new DefaultListModel<String>();
            dbTreeList = new JList<>();
            dbTreeList.setModel(dlm);
            dbTreeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jspList = new JScrollPane(dbTreeList);
            jspList.setPreferredSize(new Dimension(150, 600-loginPanelHeight ) );
            dbTreePanel.add(jspList);
        mainPanel.add(dbTreePanel, BorderLayout.WEST);

        dbTablePanel = new JPanel();
        dbTablePanel.setLayout(new CardLayout() );
        //dbTablePanel.setPreferredSize(new Dimension(300, 300));
        //dbTablePanel.setBackground(Color.BLACK);
            dtmTable = new DefaultTableModel();
            dbTable = new JTable(dtmTable);
            dbTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jspTable = new JScrollPane(dbTable);
            dbTablePanel.add(jspTable, BorderLayout.CENTER);
        mainPanel.add(dbTablePanel, BorderLayout.CENTER);

        container.add(mainPanel);
        container.revalidate();
        container.repaint();
    }


    void updateTable(Vector<Vector<Object>> rowData, Vector<String> columnNames){
        //dtmTable = new DefaultTableModel(rowData, columnNames);
        dtmTable = new DefaultTableModel(rowData, columnNames);
        dtmTable.fireTableDataChanged();
        dbTable.setModel(dtmTable);
        dbTable.revalidate();
        dbTable.repaint();
        container.revalidate();
        container.repaint();
    }


    void resetDlm(){
        dlm.clear();
        dbTreeList.revalidate();
        dbTreeList.repaint();
    }

    void setDbTreeList(Vector<String> list){
        dlm.clear();
        for(String str : list){
            dlm.addElement(str);
        }
    }


    String getUserName(){
        return userNameInput.getText();
    }
    void setUserNameStatus(boolean b){
        userNameInput.setEnabled(b);
    }
    String getUserPass(){
        return userPassInput.getText();
    }
    void setUserPassStatus(boolean b){
        userPassInput.setEnabled(b);
    }
    void setConnectButtonStatus(boolean b){
        if(b) connectButton.setText("connect");
        else connectButton.setText("disconnect");
    }


    //self test code
    public static void main(String[] args) {
        View view = new View();
        view.initUI();
    }
}