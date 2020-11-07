package controller;

import org.apache.commons.io.FileUtils;
import pojo.Individual;
import util.evaluator.Evaluator;
import util.evaluator.EvaluatorFactory;
import util.evolver.DifferentialEvolver;
import util.evolver.Evolver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Strategy implements Runnable {
    /**
     * @program: MultiThread
     * @description: 代理差分演化算法对不同策略的运行
     * @author: 许万森
     * @create: 2020-10-21 21:19
     **/
    private String mutateType;
    private Integer differentialVectorNo;
    private String crossoverType;

    private Integer runningCount;
    private Integer evolutionCount;

    private Double upperBound;
    private Double lowerBound;
    public Strategy() {
    }
    public Strategy(String mutateType, int differentialVectorNo, String crossoverType) {
        this.mutateType = mutateType;
        this.differentialVectorNo = differentialVectorNo;
        this.crossoverType = crossoverType;
    }

    public Strategy(String mutateType, int differentialVectorNo, String crossoverType, int runningCount, int evolutionCount) {
        this.mutateType = mutateType;
        this.differentialVectorNo = differentialVectorNo;
        this.crossoverType = crossoverType;
        this.runningCount = runningCount;
        this.evolutionCount = evolutionCount;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(mutateType + "-" + differentialVectorNo + "-" + crossoverType);
        String[] function = {"F1","F2","F3","F4","F5","F6","F7","F8","F9","F10","F11","F12","F13"};
        Individual bestIndividual = null;
        try {
            FileUtils.write(new File("fitness/" + mutateType + "-" + differentialVectorNo + "-" + crossoverType + ".txt"), "函数\t" + mutateType + "/" + differentialVectorNo + "/" + crossoverType + "\n", "UTF-8");
            FileUtils.write(new File("countSuccess/" + mutateType + "-" + differentialVectorNo + "-" + crossoverType+".txt"), "函数"+"\t"+mutateType + "/" + differentialVectorNo + "/" + crossoverType + "\n", "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < function.length; i++) {
            System.out.println(Thread.currentThread().getName() + "函数" + function[i] + "开始测试:");

            //记录30次独立运行得出的最优适应的和
            double sum = 0;

            //记录在独立运行中达到精度范围内的次数
            int successCount=0;

            //获取函数评价器
            Evaluator<Individual> evaluator = EvaluatorFactory.getEvaluator(function[i]);

            try {
                FileUtils.write(new File("gapPerGeneration/" + mutateType + "-" + differentialVectorNo + "-" + crossoverType + "/" + evaluator.getClass().getSimpleName() + ".txt"), mutateType + "/" + differentialVectorNo + "/" + crossoverType + "\n", "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //获取符合当前评价函数的取值范围
            try {
                String[] range = FileUtils.readFileToString(new File("function/" + function[i] + ".txt"), "UTF-8").split("\t");
                lowerBound=new Double(range[0]);
                upperBound=new Double(range[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //独立运行runningCount次 累加每代适应度相对于函数最优值的偏差
            List<Double> sum_gap = new ArrayList<>();
            for (int j = 0; j < evolutionCount; j++) {
                sum_gap.add(0.0);
            }
            //独立运行runningCount次
            for (int j = 0; j < runningCount; j++) {
                List<Individual> pop = new ArrayList<>();
                List<Individual> tmpPop=null;
//                Evolver DE= new DifferentialEvolver(mutateType,differentialVectorNo,crossoverType,upperBound,lowerBound);
               Evolver DE = new DifferentialEvolver(upperBound,lowerBound,mutateType,differentialVectorNo,crossoverType);
                //----------差分演化------------
                DE.initPop(pop);
                for (int k = 0; k < evolutionCount; k++) {
                    DE.evaluate(pop, evaluator);
                    bestIndividual=DE.getBestIndividual(pop);
                    double tmp = sum_gap.get(k);
                    tmp+=Math.abs(bestIndividual.getFitness());
                    sum_gap.set(k,tmp);
                    tmpPop = DE.mutate(pop);
                    DE.crossover(pop, tmpPop);
                    DE.evaluate(tmpPop, evaluator);
                    DE.select(pop, tmpPop);
                }
                //----------------------------
                bestIndividual = DE.getBestIndividual(pop);
                System.out.println(Thread.currentThread().getName() + "第" + (j + 1) + "次最优个体的适应度相对于最优值的偏差：" + bestIndividual.getFitness());
                sum += bestIndividual.getFitness();
                if(Math.abs(bestIndividual.getFitness())< Math.pow(10,-2)){
                    successCount++;
                }
            }
            double avgByRunningCount = sum / runningCount;
            System.out.println(Thread.currentThread().getName() +runningCount+ "个最优个体的平均适应度：" + avgByRunningCount);
            try {
                for (int j = 0; j < evolutionCount; j++) {
                    double tmp = sum_gap.get(j) / runningCount;
                    FileUtils.write(new File("gapPerGeneration/" + mutateType + "-" + differentialVectorNo + "-" + crossoverType +"/"+ function[i] + ".txt"), (j+1)+"\t"+tmp + "\n", "UTF-8", true);
                }
                FileUtils.write(new File("fitness/" + mutateType + "-" + differentialVectorNo + "-" + crossoverType + ".txt"),   function[i] + "\t" + Math.abs(avgByRunningCount) + "\n", "UTF-8", true);
                FileUtils.write(new File("countSuccess/" + mutateType + "-" + differentialVectorNo + "-" + crossoverType+".txt"),  function[i] + "\t" + successCount + "\n", "UTF-8", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
