package threedoor;

import java.text.DecimalFormat;
import java.util.Scanner;

// 三门
class ThreeDoor {
    String[] threeDoor = new String[3]; // 三门

    // 设置门后的奖品
    public void setPrize() {
        int carPosition = (int) (Math.random() * 100) % 3;

        threeDoor[0] = "Goat";
        threeDoor[1] = "Goat";
        threeDoor[2] = "Goat";
        threeDoor[carPosition] = "Car";
    }
}


// 玩家
class Player {
    int firstChoicePosition = 0; // 首次选择的门
    int lastChoicePosition = 0; // 最终选择的门

    // 选择一个门
    public void choiceDoor() {
        firstChoicePosition = (int) (Math.random() * 100) % 3;
        lastChoicePosition = firstChoicePosition;
    }

    // 更换为另一个门
    public void changeDoor(int firstGoatPosition) {
        lastChoicePosition = 3 - firstChoicePosition - firstGoatPosition;
    }
}


// 主持人
class Presenter {
    int firstGoatPosition = 0; // 主持人打开的门

    // 打开后面为山羊的门
    public void openFirstGoatPosition(String[] threeDoor, int playerFirstChoicePosition) {
        if (threeDoor[playerFirstChoicePosition].equals("Car")) {
            do {
                firstGoatPosition = (int) (Math.random() * 100) % 3;
            } while (firstGoatPosition == playerFirstChoicePosition);
        } else {
            for (int i = 0; i < 3; i++)
                if (!threeDoor[i].equals("Car") && i != playerFirstChoicePosition) firstGoatPosition = i;
        }
    }
}


// 计分牌
class Scorer {
    int playCount = 0; // 玩的总次数
    int choiceCarCount = 0; // 获得车的次数
    int choiceGoatCount = 0; // 获得山羊的次数

    // 计分
    public void score(String[] threeDoor, int playLastChoicePosition) {
        playCount++;
        if (threeDoor[playLastChoicePosition].equals("Car"))
            choiceCarCount++;
        else
            choiceGoatCount++;
    }

    // 计算获得车的概率
    public void statistics() {
        DecimalFormat df = new DecimalFormat("##.00%");
        System.out.println("Choice Goat Count: " + choiceGoatCount);
        System.out.println("Choice Car  Count: " + choiceCarCount);
        System.out.println("Choice Car  Rate : " + df.format((float) choiceCarCount / playCount));
    }
}


// 设置模式
class Moder {
    String mode = ""; // 模式，A：换门，B：不换门
    int playTotalCount = 0; // 玩的总次数

    // 设置模式
    public void setMode() {
        Scanner sc = new Scanner(System.in);

        // 设置选门模式
        while (!mode.equals("A") && !mode.equals("B")) {
            System.out.println("Plase input mode: A[Change]  B[Don't Change]");
            mode = sc.nextLine();
            if (!mode.equals("A") && !mode.equals("B")) System.out.println("Input Error, Input again.");
        }

        // 设置玩的总次数
        while (playTotalCount <= 0) {
            System.out.println("Plase input play total count: ");
            try {
                Scanner scCnt = new Scanner(System.in);
                playTotalCount = scCnt.nextInt();
            } catch (Exception e) {
                playTotalCount = 0;
            }
            if (playTotalCount <= 0) System.out.println("Input Error, Input again.");
        }

        System.out.println();
    }

    // 显示设置的模式
    public void showMode() {
        if (mode.equals("A"))
            System.out.println("Mode: [Change]");
        else
            System.out.println("Mode: [Don't Change]");

        System.out.println("Play Total Count: " + playTotalCount);
        System.out.println();
    }
}


// main程序
class Test1 {
    public static void main(String[] args) {
        ThreeDoor threeDoor = new ThreeDoor(); // 三门
        Player player = new Player(); // 玩家
        Presenter presenter = new Presenter(); // 主持人
        Scorer scorer = new Scorer(); // 计分者
        Moder moder = new Moder(); // 设置模式者

        moder.setMode(); // 设置模式
        moder.showMode(); // 显示模式

        // 循环玩多次
        for (int i = 0; i < moder.playTotalCount; i++) {
            threeDoor.setPrize(); // 设置门后奖品
            player.choiceDoor(); // 玩家选择一个门
            // 主持人打开一扇是山羊的门
            presenter.openFirstGoatPosition(threeDoor.threeDoor, player.firstChoicePosition);
            if (moder.mode.equals("A")) player.changeDoor(presenter.firstGoatPosition); // 玩家换另外一扇门
            // 计分
            scorer.score(threeDoor.threeDoor, player.lastChoicePosition);
        }

        scorer.statistics(); // 统计获得汽车的概率
    }
}
