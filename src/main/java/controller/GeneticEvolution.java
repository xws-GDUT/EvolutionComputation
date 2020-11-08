package controller;

import org.apache.commons.io.FileUtils;
import pojo.Individual;
import util.evaluator.Evaluator;
import util.evaluator.EvaluatorFactory;
import util.evolver.Evolver;
import util.evolver.GeneticEvolver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wansenxu@163.com on 2020/11/8
 */
public class GeneticEvolution {
    public static void main(String[] args) throws IOException {
        String[] function = {"F1"};


        List<Individual> pop = new ArrayList<>();
        for (int i = 0; i < function.length; i++) {
            List<Individual> bestIndividuals = new ArrayList<Individual>();

            FileUtils.write(new File("bestIndividuals.txt"),"","UTF-8");
            String[] str_range= FileUtils.readFileToString(new File("function/f1.txt"), "UTF-8").split("\t");

            Double[] range = new Double[2];
            range[0]=new Double(str_range[0]);
            range[1]=new Double(str_range[1]);

            //配置函数评价器Evaluator
            Evaluator<Individual> evaluator = EvaluatorFactory.getEvaluator(function[i]);

            //配置进化器GE
            Evolver GE= new GeneticEvolver();
            GE.setPOPSIZE(100);
            GE.setNvars(30);
            GE.setRate_crossover(0.85);
            GE.setRate_mutate(0.02);
            GE.setLowerBound(range[0]);
            GE.setUpperBound(range[1]);


            int count=0; //记录代数
            StringBuilder str = new StringBuilder();
            int evolutionCount=10000;
            for(int j = 0; j<=evolutionCount; j++){
                if(j==0){
                    //种群初始化
                    GE.initPop(pop);
                }else{
                    //种群演化
                    List<Individual> tmpPop = null;
                    GE.select(pop,tmpPop);
                    GE.crossover(pop);
                    GE.mutate(pop);
                }
                GE.evaluate(pop,evaluator);
                bestIndividuals.add(GE.getBestIndividual(pop));//存放每一代种群的最佳个体
                System.out.println(GE.getBestIndividual(bestIndividuals).getFitness());
                str.append(++count+"\t"+GE.getBestIndividual(bestIndividuals).getFitness()+"\n");
            }
            FileUtils.write(new File("bestIndividuals.txt"),str,"UTF-8",true);
            FileUtils.write(new File("bestIndividualPerGeneration.txt"),"","UTF-8");
            StringBuilder str_2 = new StringBuilder();
            int tmp= 0;
            for (Individual individual:bestIndividuals) {
                str_2.append(++tmp+"\t"+individual.getFitness()+"\n");
            }
            FileUtils.write(new File("bestIndividualPerGeneration.txt"),str_2,"UTF-8",true);
        }



    }
}
