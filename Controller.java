import java.awt.event.*;
import javax.swing.*;

public class Controller implements ActionListener, MouseListener {
    View view;
    Model model;

    Controller(View view, Model model){
        this.view = view;
        this.model = model;
    } //end of Controller

    void initController(){
        view.initUI();
        view.connectButton.addActionListener(this);
        view.dbTreeList.addMouseListener(this);
    } //end of startController

    @Override //MouseListener
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
        Object obj = e.getSource();
        if( obj instanceof JList){
            JList jt = (JList)obj;
            if(jt == view.dbTreeList){
                //System.out.println("???"+jt.getSelectedValue() );
                model.selectTable( jt.getSelectedValue().toString() );
                view.updateTable(model.getRowData(), model.getColumnNames() );
            }
        }
    } //end of mouseClicked

    @Override //ActionListener
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if(obj instanceof JButton){
            JButton jb = (JButton)obj;
            if(jb == view.connectButton){
                boolean tempB = false;
                if(jb.getText().equals("connect")){
                    if(model.connectOracle(view.getUserName(), view.getUserPass()) ){
                        tempB = false;
                        model.getTableList();
                        view.setDbTreeList(model.tableList);
                        System.out.println("연결이 성공했습니다.");
                    }else{
                        tempB = true;
                        view.resetTableList();
                        JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 확인하세요.");
                        System.out.println("연결이 실패했습니다.");
                    }

                }else{
                    model.closeAll();
                    tempB = true;
                }
                view.setUserNameStatus(tempB);
                view.setUserPassStatus(tempB);
                view.setConnectButtonStatus(tempB);

            }
        } //end of JButton
    } //end of actionPerformed



    public static void main(String[] args) {
        Controller controller = new Controller(new View(), new Model() );
        controller.initController();

        controller.closeProgram();
    } //end of main

    public void closeProgram(){
        //윈도우 종료 이외에도 Ctrl+C로 강제종료 할 때에도 어쨌든 DB 연결을 종료해야 한다.
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                model.closeAll();
                System.out.println("프로그램을 종료합니다.");
            }
        });
    } // end of closeProgram
}