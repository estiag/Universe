package maze;


import java.util.*;

/**
 * Created by estiag on 2019/5/28.
 */
public class Maze {
    private static Map<Operate, List<Operate>> condition = new HashMap<>();
    private static Queue<TreeNode> queue = new LinkedList<>();
    private static Integer start = 2018;
    private static Integer end = 2019;

    public static void main(String[] args) {
        condition.put(Operate.root, Arrays.asList(Operate.r7, Operate.r2));
        condition.put(Operate.r7, Arrays.asList(Operate.l2, Operate.r3, Operate.r5));
        condition.put(Operate.l7, Arrays.asList(Operate.r2));
        condition.put(Operate.r3, Arrays.asList(Operate.l5));
        condition.put(Operate.l3, Arrays.asList(Operate.r5, Operate.l2, Operate.l7));
        condition.put(Operate.r2, Arrays.asList(Operate.l7, Operate.r3, Operate.r5));
        condition.put(Operate.l2, Arrays.asList(Operate.r7));
        condition.put(Operate.r5, Arrays.asList(Operate.l3));
        condition.put(Operate.l5, Arrays.asList(Operate.r3, Operate.l7, Operate.l2));
        breadthFirstSearch();
    }

    /**
     * Breadth First Search
     */
    public static void breadthFirstSearch() {
        queue.add(new TreeNode(Operate.root, start, start + "", 0));
        int lastDepth = 1;
        int step = 0;
        while (!queue.isEmpty()) {
            TreeNode parent = queue.poll();
            List<Operate> children = condition.get(parent.getOperate());
            for (int i = 0; i < children.size(); i++) {
                Operate child = children.get(i);
                TreeNode node = new TreeNode(child, parent.apply(child), parent.getPath() + child.getOperate(), parent.getDepth() + 1);
                queue.offer(node);
                if (node.getResult() == end && (node.getOperate() == Operate.r3 || node.getOperate() == Operate.r5)) {
                    System.out.println("result is" + node);
                }
                // System.out.println(node);
            }
            if (lastDepth != parent.getDepth()) {
                lastDepth = parent.getDepth();
                System.out.println("depth=" + lastDepth + "steps=" + step);
                step = 0;
            }

            step += children.size();

            if (parent.getDepth() >= 26) {
                //return;
            }
        }
    }
}

    enum Operate {
        r7("+7"),
        l7("+7"),
        r3("*3"),
        l3("*3"),
        r2("/2"),
        l2("/2"),
        r5("-5"),
        l5("-5"),
        root("");
private String Operate;

        Operate(String Operate){
        this.Operate=Operate;
        }

public String getOperate(){
        return Operate;
        }

public void setOperate(String Operate){
        this.Operate=Operate;
        }

@Override
public String toString(){
        return"Operate{"+
        "Operate='"+Operate+'\''+
        '}';
        }
        }

class TreeNode {
    private Operate operate;
    private double result;
    private String path;
    private int depth;

    public TreeNode(Operate operate, double result, String path, int depth) {
        this.operate = operate;
        this.result = result;
        this.path = path;
        this.depth = depth;
    }

    public Operate getOperate() {
        return operate;
    }

    public void setOperate(Operate operate) {
        this.operate = operate;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getPath() {
        return path;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double apply(Operate operate) {

        if (operate == Operate.r7 || operate == Operate.l7) {
            return this.result + 7;
        } else if (operate == Operate.r3 || operate == Operate.l3) {
            return this.result * 3;
        } else if (operate == Operate.r2 || operate == Operate.l2) {
            return this.result / 2;
        } else {
            return this.result - 5;
        }
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "operate=" + operate +
                ", result=" + result +
                ", path='" + path + '\'' +
                ", depth=" + depth +
                '}';
    }
}