package controller;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("设置线程池的大小：");
        Scanner scanner = new Scanner(System.in);
        int ThreadAmount= scanner.nextInt();
        System.out.println("设置每种策略的独立运行次数：");
        int runningCount = scanner.nextInt();
        System.out.println("设置差分演化算法的演化次数：");
        int evolutionCount = scanner.nextInt();
        ExecutorService pool = Executors.newFixedThreadPool(ThreadAmount);
//      每个线程代表一种策略线程测试
        Strategy rand_1_bin = new Strategy("rand",1,"bin",runningCount,evolutionCount);
        Strategy rand_1_exp = new Strategy("rand",1,"exp",runningCount,evolutionCount);
        Strategy best_1_bin = new Strategy("best",1,"bin",runningCount,evolutionCount);
        Strategy best_1_exp = new Strategy("best",1,"exp",runningCount,evolutionCount);
        Strategy rand_2_bin = new Strategy("rand",2,"bin",runningCount,evolutionCount);
        Strategy rand_2_exp = new Strategy("rand",2,"exp",runningCount,evolutionCount);
        Strategy best_2_bin = new Strategy("best",2,"bin",runningCount,evolutionCount);
        Strategy best_2_exp = new Strategy("best",2,"exp",runningCount,evolutionCount);
        Strategy randTobest_1_bin = new Strategy("randToBest",1,"bin",runningCount,evolutionCount);
        Strategy randTobest_1_exp = new Strategy("randToBest",1,"exp",runningCount,evolutionCount);
//        new Thread(rand_1_bin).start();
        pool.execute(rand_1_bin);
        pool.execute(rand_1_exp);
        pool.execute(best_1_bin);
        pool.execute(best_1_exp);
        pool.execute(rand_2_bin);
        pool.execute(rand_2_exp);
        pool.execute(best_2_bin);
        pool.execute(best_2_exp);
        pool.execute(randTobest_1_bin);
        pool.execute(randTobest_1_exp);
        pool.shutdown();
    }

}
