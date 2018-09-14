package mazeProblem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;


/**
 * @author manchaoyang 2017/07/07
 */
public class TheUtils {
    private static Color[] colors = {Color.BLACK, Color.WHITE};
    public static Color c1 = new Color(191, 174, 185);
    public static Color c2 = Color.BLUE;

    public static int ROWS;
    public static int CLOS;
    private static List<JButton> path;
    private static int[][] dir = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    private static int[][] dir1 = {{-2, 0}, {2, 0}, {0, 2}, {0, -2}};
    private static JButton begin = null;
    private static JButton end = null;
    private static boolean flag = true;
    private static boolean[][] visited;
    private static Point[][] points;
    private static int[][] maze;
    static Random random = new Random();
    private static Point[][] pres;
    /**
     * 通过map来获得每个按钮在数组中的位置然后来进行BFS，
     * 按钮的位置包括启示位置和终止位置
     */
    private static Map<JButton, Point> map = new HashMap<>();

    //通过实现一个内部类来选择起止节点
    private static class SetAction extends AbstractAction {
        private JButton button = null;

        public SetAction(JButton jButton) {
            button = jButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            System.out.print("beg: " + begin + "\n end:" + end + "  flag" + flag);
//            System.out.println(map.get(button).x + " " + map.get(button).y);
            if (!button.getBackground().equals(Color.BLACK)) {
                if (begin == null && flag) {
                    begin = button;
                    begin.setBackground(Color.green);
                    flag = !flag;
                } else if (end == null && !flag) {
                    end = button;
                    end.setBackground(Color.CYAN);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "起止点不能是墙", "错误", JOptionPane.ERROR_MESSAGE);
            }

        }

    }


    public static void clear() {
        if (visited != null) {
            for (int i = 0; i < (ROWS * 2 + 1); i++) {
                for (int j = 0; j < (CLOS * 2 + 1); j++) {
                    visited[i][j] = false;
                    if (MainWindow.buttons[i][j].getBackground().equals(c1)
                            || MainWindow.buttons[i][j].getBackground().equals(c2)) {
                        MainWindow.buttons[i][j].setBackground(Color.WHITE);
                    }
                }
            }
        }
        maze = null;
        pres = null;
        if (begin != null) {
            begin.setBackground(Color.WHITE);
        }
        if (end != null) {
            end.setBackground(Color.WHITE);
        }
        begin = null;
        end = null;
        flag = true;
    }

    public static void creatRandomMaze() {
        //初始化各项
        visited = new boolean[(ROWS * 2 + 1)][(CLOS * 2 + 1)];
        points = new Point[(ROWS * 2 + 1)][(CLOS * 2 + 1)];
        for (int i = 0; i < (ROWS * 2 + 1); i++) {
            for (int j = 0; j < (CLOS * 2 + 1); j++) {
                points[i][j] = new Point(i, j);
            }
        }
        prim();
//        for (int i = 0; i < (ROWS * 2 + 1); i++) {
//            for (int j = 0; j < (CLOS * 2 + 1); j++) {
//                System.out.print(maze[i][j]);
//            }
//            System.out.println();
//        }
        for (int i = 0; i < (ROWS * 2 + 1); i++) {
            for (int j = 0; j < (CLOS * 2 + 1); j++) {
                map.put(MainWindow.buttons[i][j], new Point(i, j));
                if (maze[i][j] == 0) {
                    MainWindow.buttons[i][j].setBackground(colors[1]);
                } else {
                    MainWindow.buttons[i][j].setBackground(colors[0]);
                }
                MainWindow.buttons[i][j].addActionListener(new SetAction(MainWindow.buttons[i][j]));
            }
        }
    }

    public static void changeMaze() {

        prim();
        for (int i = 0; i < (ROWS * 2 + 1); i++) {
            for (int j = 0; j < (CLOS * 2 + 1); j++) {
                map.put(MainWindow.buttons[i][j], new Point(i, j));
                if (maze[i][j] == 0) {
                    MainWindow.buttons[i][j].setBackground(colors[1]);
                } else {
                    MainWindow.buttons[i][j].setBackground(colors[0]);
                }
            }
        }

    }

    private static void prim() {
        maze = new int[(ROWS * 2 + 1)][(CLOS * 2 + 1)];
        for (int i = 0; i < (ROWS * 2 + 1); i++) {
            for (int j = 0; j < (CLOS * 2 + 1); j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    maze[i][j] = 0;
                } else {
                    maze[i][j] = 1;
                }
            }
        }
        //迷宫生成算法：

        int x = 0;
        int y = 0;
        Point current = points[x][y];
        LinkedList<Point> visitedP = new LinkedList<>();
        visitedP.add(points[x][y]);
        visited[x][y] = true;
        while (visited[current.x][current.y]) {
            LinkedList<Point> neighborArray = new LinkedList<>();
            for (int i = 0; i <= 3; i++) {
                int _x = current.x + dir1[i][0];
                int _y = current.y + dir1[i][1];
                if (_x >= 0 && _x < (ROWS * 2 + 1) && _y >= 0 && _y < (CLOS * 2 + 1) && !visited[_x][_y]) {
                    neighborArray.add(points[_x][_y]);
                }
            }
            if (neighborArray.size() > 0) {
                Point neighborNode = neighborArray.get(random.nextInt(neighborArray.size()));
                maze[(neighborNode.x + current.x) / 2][(neighborNode.y + current.y) / 2] = 0;//打通两点之间的墙
                visitedP.add(neighborNode);
                current = neighborNode;
                visited[current.x][current.y] = true;
            } else {
                if (visitedP.size() == 0) {
                    break;
                }
                current = visitedP.get(random.nextInt(visitedP.size()));
                visitedP.remove(current);
            }
        }

    }

    //搜索算法 ：因为要找最短路径所以用的bfs
    public static void search() {
        pres = new Point[(ROWS * 2 + 1)][(CLOS * 2 + 1)];
        if (begin != null && end != null) {
            for (int i = 0; i < (ROWS * 2 + 1); i++) {
                for (int j = 0; j < (CLOS * 2 + 1); j++) {
                    visited[i][j] = false;
                }
            }
            path = new LinkedList<>();
            int x = map.get(begin).x;
            int y = map.get(begin).y;

            pres[x][y] = new Point(-1, -1);
            if (helpSearch(x, y)) {

                for (int i = 0; i < path.size(); i++) {
                    path.get(i).setBackground(c2);
                }
                JOptionPane.showMessageDialog(null,
                        "你找到了离开迷宫的最短路径", "恭喜", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "哈哈，死路一条", "提示", JOptionPane.WARNING_MESSAGE);
                clear();
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    "警告 ", "未正确设置迷宫的起止点", JOptionPane.WARNING_MESSAGE);
            clear();
        }
    }

    private static boolean helpSearch(int x, int y) {
        Queue<Point> que = new LinkedList<>();
        visited[x][y] = true;
        que.offer(points[x][y]);
        while (!que.isEmpty()) {
            Point temp = que.poll();
//            System.out.println("x:" + temp.x + "  y:" + temp.y);
            MainWindow.buttons[temp.x][temp.y].setBackground(c1);
            if (MainWindow.buttons[temp.x][temp.y].equals(end)) {
                getPath(temp.x, temp.y);
                return true;
            }
            for (int i = 0; i <= 3; i++) {
                int x_ = temp.x + dir[i][0];
                int y_ = temp.y + dir[i][1];
                if (x_ >= 0 && x_ < (ROWS * 2 + 1) && y_ >= 0 && y_ < (CLOS * 2 + 1) && !visited[x_][y_] &&
                        !MainWindow.buttons[x_][y_].getBackground().equals(Color.BLACK)) {
                    que.offer(points[x_][y_]);
                    visited[x_][y_] = true;
                    pres[x_][y_] = new Point(temp.x, temp.y);
                }
            }
        }
        return false;
    }

    static void getPath(int x, int y) {
        path.add(MainWindow.buttons[x][y]);
        Point temp = pres[x][y];
        for (; ; ) {
            if (temp.x != -1) {
                path.add(MainWindow.buttons[temp.x][temp.y]);
                temp = pres[temp.x][temp.y];
            } else {
                path.add(begin);
                break;
            }
        }
//        System.out.println("出来了");
    }
}

class Point {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
 