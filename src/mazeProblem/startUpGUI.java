package mazeProblem;

import javax.swing.*;

/**
 * @author manchaoyang 2017/07/07
 */
public class startUpGUI {
    MainWindow mainWindow;

    public startUpGUI() {
        String rows = JOptionPane.showInputDialog(null, "请输入迷宫的行：");
        String cols = JOptionPane.showInputDialog(null, "请输入迷宫的列：");
        TheUtils.ROWS = Integer.parseInt(rows);
        TheUtils.CLOS = Integer.parseInt(cols);
        mainWindow = MainWindow.getMainWindow();
        TheUtils.creatRandomMaze();
        System.out.println("row:" + TheUtils.ROWS + ": clos:" + TheUtils.CLOS);
    }

    public static void main(String[] ars) {
        startUpGUI temp = new startUpGUI();
    }
}
