package mazeProblem;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
/**
 * @author manchaoyang 2017/07/07
 */
public class MainWindow extends JFrame {

    static JButton[][] buttons;//保持包私有
    private JPanel panel = new JPanel();
    private JPanel panel2 = new JPanel();
    private static MainWindow mainWindow = null;
    public static MainWindow getMainWindow() {
        if (mainWindow == null) {
            mainWindow = new MainWindow();
            return mainWindow;
        }
        return mainWindow;
    }

    private MainWindow() {
        setLayout(null);
        setSize(((TheUtils.ROWS*2+1)+10) * 14, ((TheUtils.CLOS*2+1)+6)* 14);
        JButton start=new JButton("开始");
        JButton clear=new JButton("清空");
        JButton change=new JButton("更换地图");
        start.setLocation(((TheUtils.ROWS*2+1) ) *14 , 0);
        start.setSize(90,40);
        start.addActionListener(new SearchAction());
        add(start);
        clear.setLocation(((TheUtils.ROWS*2+1) ) * 14 , 46);
        clear.setSize(90,40);
        clear.addActionListener(new ClearAction());
        add(clear);
        change.setLocation(((TheUtils.ROWS*2+1) ) * 14 , 92);
        change.setSize(90,40);
        change.addActionListener(new changeAction());
        add(change);
        panel.setLayout(new GridLayout((TheUtils.ROWS*2+1), (TheUtils.CLOS*2+1)));
        //1 setLayout(new GridLayout(5,5));
        // 用按钮作为迷宫的格子
        panel.setSize(((TheUtils.ROWS*2+1) ) * 14, (TheUtils.CLOS*2+1) * 14);
        buttons = new JButton[(TheUtils.ROWS*2+1)][(TheUtils.CLOS*2+1)];
        for (int i = 0; i < (TheUtils.ROWS*2+1); i++)
            for (int j = 0; j < (TheUtils.CLOS*2+1); j++) {
                buttons[i][j] = new JButton();

                panel.add(buttons[i][j]);
            }
        add(panel);
        setTitle("Welcome to The Maze.");
        //pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);           //让窗体居中显示
    }
    public static void main(String[] args){
        MainWindow mainWindow=new MainWindow();
    }
}
class ClearAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        TheUtils.clear();
    }
}

class SearchAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        TheUtils.search();
    }
}
class changeAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        TheUtils.clear();
        TheUtils.changeMaze();
    }
}