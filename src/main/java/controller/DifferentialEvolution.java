package controller;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import pojo.Individual;
import util.evaluator.Evaluator;
import util.evaluator.EvaluatorFactory;
import util.evolver.CrossoverType;
import util.evolver.DifferentialEvolver;
import util.evolver.Evolver;
import util.evolver.MutateType;

import java.awt.event.WindowFocusListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DifferentialEvolution implements Runnable {
    /**
     * @program: MultiThread
     * @description: 代理差分演化算法对不同策略的运行
     * @author: 许万森
     * @create: 2020-10-21 21:19
     **/
    private MutateType mutateType;
    private Integer differentialVectorNo;
    private CrossoverType crossoverType;

    private Integer runningCount;
    private Integer evolutionCount;

    private CountDownLatch countDownLatch;

    //test segment ----------------------------
//    private List<Data> datas = new ArrayList<>();

    //-----------------------------------------


    public DifferentialEvolution() {
    }

    public DifferentialEvolution(MutateType mutateType, int differentialVectorNo, CrossoverType crossoverType) {
        this.mutateType = mutateType;
        this.differentialVectorNo = differentialVectorNo;
        this.crossoverType = crossoverType;
    }

    public DifferentialEvolution(MutateType mutateType, int differentialVectorNo, CrossoverType crossoverType, int runningCount, int evolutionCount, CountDownLatch countDownLatch) {
        this.mutateType = mutateType;
        this.differentialVectorNo = differentialVectorNo;
        this.crossoverType = crossoverType;
        this.runningCount = runningCount;
        this.evolutionCount = evolutionCount;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public void run() {
        Thread.currentThread().setName(mutateType + "-" + differentialVectorNo + "-" + crossoverType);
        String[] function = {"f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "f10", "f11", "f12", "f13"};
        StringBuilder result= new StringBuilder();
        result.append(mutateType + "-" + differentialVectorNo + "-" + crossoverType);
        Individual bestIndividual = null;
        try {



            FileUtils.write(new File("fitness/" + mutateType + "-" + differentialVectorNo + "-" + crossoverType + ".txt"), "函数\t" + mutateType + "/" + differentialVectorNo + "/" + crossoverType + "\n", "UTF-8");
            FileUtils.write(new File("countSuccess/" + mutateType + "-" + differentialVectorNo + "-" + crossoverType + ".txt"), "函数" + "\t" + mutateType + "/" + differentialVectorNo + "/" + crossoverType + "\n", "UTF-8");
            for (int i = 0; i < function.length; i++) {
                System.out.println(Thread.currentThread().getName() + "函数" + function[i] + "开始测试:");

                //记录30次独立运行得出的最优适应的和
                double sum = 0;

                //记录在独立运行中达到精度范围内的次数
                int successCount = 0;

                //获取函数评价器
                Evaluator<Individual> evaluator = EvaluatorFactory.getEvaluator(function[i]);

                FileUtils.write(new File("gapPerGeneration/" + mutateType + "-" + differentialVectorNo + "-" + crossoverType + "/" + evaluator.getClass().getSimpleName() + ".txt"), mutateType + "/" + differentialVectorNo + "/" + crossoverType + "\n", "UTF-8");

                //获取符合当前评价函数的取值范围
                Double[] range = new Double[2];

                String[] str_range = FileUtils.readFileToString(new File("function/" + function[i] + ".txt"), "UTF-8").split("\t");
                range[0] = new Double(str_range[0]);
                range[1] = new Double(str_range[1]);


                //独立运行runningCount次 累加每代适应度相对于函数最优值的偏差
                List<Double> sum_gap = new ArrayList<>();
                for (int j = 0; j < evolutionCount; j++) {
                    sum_gap.add(0.0);
                }
                //独立运行runningCount次
                for (int j = 0; j < runningCount; j++) {
                    List<Individual> pop = new ArrayList<>();
                    List<Individual> tmpPop = null;
                    //配置进化器DE------------------------
                    Evolver DE = new DifferentialEvolver(mutateType, differentialVectorNo, crossoverType);
                    DE.setPOPSIZE(100);
                    DE.setNvars(30);
                    DE.setF(0.5);
                    DE.setLambda(0.5);
                    DE.setRate_crossover(0.9);
                    DE.setLowerBound(range[0]);
                    DE.setUpperBound(range[1]);

                    //----------差分演化------------
                    DE.initPop(pop);
                    for (int k = 0; k < evolutionCount; k++) {
                        DE.evaluate(pop, evaluator);
                        bestIndividual = DE.getBestIndividual(pop);
                        double tmp = sum_gap.get(k);
                        tmp += Math.abs(bestIndividual.getFitness());
                        sum_gap.set(k, tmp);
                        tmpPop = DE.mutate(pop);
                        DE.crossover(pop, tmpPop);
                        DE.evaluate(tmpPop, evaluator);
                        DE.select(pop, tmpPop);
                    }
                    //----------------------------

                    bestIndividual = DE.getBestIndividual(pop);
                    System.out.println(Thread.currentThread().getName() + "第" + (j + 1) + "次最优个体的适应度相对于最优值的偏差：" + bestIndividual.getFitness());
                    sum += bestIndividual.getFitness();
                    if (Math.abs(bestIndividual.getFitness()) < Math.pow(10, -2)) {
                        successCount++;
                    }
                }
                double avgByRunningCount = sum / runningCount;
                System.out.println(Thread.currentThread().getName() + runningCount + "个最优个体的平均适应度：" + avgByRunningCount);

                for (int j = 0; j < evolutionCount; j++) {
                    double tmp = sum_gap.get(j) / runningCount;
                    FileUtils.write(new File("gapPerGeneration/" + mutateType + "-" + differentialVectorNo + "-" + crossoverType + "/" + function[i] + ".txt"), (j + 1) + "\t" + tmp + "\n", "UTF-8", true);
                }
                result.append("\t" + Math.abs(avgByRunningCount));
                FileUtils.write(new File("fitness/" + mutateType + "-" + differentialVectorNo + "-" + crossoverType + ".txt"), function[i] + "\t" + Math.abs(avgByRunningCount) + "\n", "UTF-8", true);
                FileUtils.write(new File("countSuccess/" + mutateType + "-" + differentialVectorNo + "-" + crossoverType + ".txt"), function[i] + "\t" + successCount + "\n", "UTF-8", true);
            }
            // 输出值excel
            File file = new File("result.xls");
            FileInputStream fileInputStream = FileUtils.openInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheet = workbook.getSheet("fitness");
            Iterator<Row> iterator = sheet.iterator();
            short from = 0;
            while(iterator.hasNext()){
                from++;
                Iterator<Cell> iterator1 = iterator.next().iterator();
                while (iterator1.hasNext()){
                    System.out.print(iterator1.next().getStringCellValue());
                }
            }
            HSSFRow row = sheet.createRow(from);
            String[] charArray = result.toString().split("\t");
            short lastCellNum = row.getLastCellNum();
            for (int i = 0; i < charArray.length; i++) {
                HSSFCell cell = row.createCell(++lastCellNum);
                cell.setCellValue(charArray[i]);

                if(i>0) {
                    if (new Double(charArray[i]) < Math.pow(10, -2)) {
                        CellStyle cellStyle = workbook.createCellStyle();
                        Font font = workbook.createFont();
                        font.setColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
                        cellStyle.setFont(font);
                        cell.setCellStyle(cellStyle);
                    }
                }
            }
            FileOutputStream fileOutputStream = FileUtils.openOutputStream(file);
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }


    public static void main(String[] args) {


        System.out.println("设置线程池的大小：");
        Scanner scanner = new Scanner(System.in);
        int ThreadAmount = scanner.nextInt();
        System.out.println("设置每种策略的独立运行次数：");
        int runningCount = scanner.nextInt();
        System.out.println("设置差分演化算法的演化次数：");
        int evolutionCount = scanner.nextInt();

        CountDownLatch countDownLatch = new CountDownLatch(10);
        long begin = System.currentTimeMillis();


        FileOutputStream fileOutputStream= null;
        try {
            File file = new File("result.xls");
            fileOutputStream = FileUtils.openOutputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet1 = workbook.createSheet("fitness");
            short from = 0;
            HSSFRow row = sheet1.createRow(from);
            String[] str={"进化策略","F1","F2","F3","F4","F5","F6","F7","F8","F9","F10","F11","F12","F13"};
            short lastCellNum = row.getLastCellNum();
            for (int i = 0; i < str.length; i++) {
                HSSFCell cell = row.createCell(++lastCellNum);
                cell.setCellValue(str[i]);
            }
            workbook.write(fileOutputStream);
            fileOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        ExecutorService pool = Executors.newFixedThreadPool(ThreadAmount);
//      每个线程代表一种策略线程测试
        DifferentialEvolution rand_1_bin = new DifferentialEvolution(MutateType.RAND, 1, CrossoverType.BIN, runningCount, evolutionCount, countDownLatch);
        DifferentialEvolution rand_1_exp = new DifferentialEvolution(MutateType.RAND, 1, CrossoverType.EXP, runningCount, evolutionCount, countDownLatch);
        DifferentialEvolution best_1_bin = new DifferentialEvolution(MutateType.BEST, 1, CrossoverType.BIN, runningCount, evolutionCount, countDownLatch);
        DifferentialEvolution best_1_exp = new DifferentialEvolution(MutateType.BEST, 1, CrossoverType.EXP, runningCount, evolutionCount, countDownLatch);
        DifferentialEvolution rand_2_bin = new DifferentialEvolution(MutateType.RAND, 2, CrossoverType.BIN, runningCount, evolutionCount, countDownLatch);
        DifferentialEvolution rand_2_exp = new DifferentialEvolution(MutateType.RAND, 2, CrossoverType.EXP, runningCount, evolutionCount, countDownLatch);
        DifferentialEvolution best_2_bin = new DifferentialEvolution(MutateType.BEST, 2, CrossoverType.BIN, runningCount, evolutionCount, countDownLatch);
        DifferentialEvolution best_2_exp = new DifferentialEvolution(MutateType.BEST, 2, CrossoverType.EXP, runningCount, evolutionCount, countDownLatch);
        DifferentialEvolution randTobest_1_bin = new DifferentialEvolution(MutateType.RANDtoBest, 1, CrossoverType.BIN, runningCount, evolutionCount, countDownLatch);
        DifferentialEvolution randTobest_1_exp = new DifferentialEvolution(MutateType.RANDtoBest, 1, CrossoverType.EXP, runningCount, evolutionCount, countDownLatch);
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

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        long timeSpent = end - begin;
        System.out.println("总共运行：" + Math.ceil(timeSpent / 1000.0) + "秒");
        pool.shutdown();
    }
}
