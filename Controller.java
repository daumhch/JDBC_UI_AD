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

        closeProgram();
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
                System.out.println("???"+jt.getSelectedValue() );
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
                        System.out.println("������ �����߽��ϴ�.");
                    }else{
                        tempB = true;
                        view.resetDlm();
                        JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� Ȯ���ϼ���.");
                        System.out.println("������ �����߽��ϴ�.");
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

    public void closeProgram(){
        //������ ���� �̿ܿ��� Ctrl+C�� �������� �� ������ ��·�� DB ������ �����ؾ� �Ѵ�.
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    model.closeAll();
                    Thread.sleep(200);
                    System.out.println("���α׷��� �����մϴ�.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    } // end of closeProgram

    public static void main(String[] args) {
        Controller controller = new Controller(new View(), new Model() );
        controller.initController();
    } //end of main

}