package util.evolver;

import pojo.Individual;
import util.evaluator.Evaluator;

import java.util.ArrayList;
import java.util.List;

public abstract class Evolver implements Evolvable{
    /**
     * @program: EC
     *
     * @description: 抽象进化器
     *
     * @author: 许万森
     *
     * @create: 2020-11-07 23:47
     **/
    private int POPSIZE;            //种群规模
    private int Nvars;              //个体维度
    private double rate_crossover;  //交叉概率
    private double rate_mutate;     //变异概率
    private double F;              //缩放因子
    private double lambda;

    //取值范围
    private Double upperBound;
    private Double lowerBound;

    public Evolver() {
    }

    public Evolver(int POPSIZE, int nvars, double rate_crossover, double rate_mutate, double f, double lambda, Double upperBound, Double lowerBound) {
        this.POPSIZE = POPSIZE;
        Nvars = nvars;
        this.rate_crossover = rate_crossover;
        this.rate_mutate = rate_mutate;
        F = f;
        this.lambda = lambda;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    public Double getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(Double upperBound) {
        this.upperBound = upperBound;
    }

    public Double getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(Double lowerBound) {
        this.lowerBound = lowerBound;
    }

    @Override
    public void initPop(List<Individual> pop) {
        if(pop==null){
            pop=new ArrayList<>();
        }
        for (int i = 0; i < POPSIZE; i++) {
            Individual individual = new Individual();
            List<Double> solution= new ArrayList<Double>();
            for(int k = 0; k< Nvars; k++){
                solution.add(lowerBound + Math.random() * ( upperBound- lowerBound));
            }
            individual.setSolution(solution);
            pop.add(individual);
        }
    }

    /**
     * 种群初始化
     * @param pop
     * @return
     */
    @Override
    public Individual getBestIndividual(List<Individual> pop) {
        Individual bestIndividual = null;
        try {
            Double minFitness = pop.get(0).getFitness();
            int k=0;
            for(int i=1;i<pop.size();i++){
                if(pop.get(i).getFitness()<minFitness){
                    k=i;
                }
            }
            bestIndividual= (Individual) pop.get(k).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bestIndividual;
    }

    /**
     * 通过评价器（Evaluator）对种群pop进行评价
     * @param pop
     * @param evaluator
     */
    @Override
    public void evaluate(List<Individual> pop, Evaluator evaluator) {
        for (int i = 0; i < pop.size(); i++) {
            evaluator.evaluate(pop.get(i));
        }
    }

    /**
     * 适应度越界处理
     * @param value
     * @param lowerBoundary
     * @param upperBoundary
     * @return
     */
    public double handleOutOfBoundary(Double value,double lowerBoundary, double upperBoundary){
        if(value<lowerBoundary ){
            value = lowerBoundary;
        }else if(value > upperBoundary) {
            value = upperBoundary;
        }else{
            value= value;
        }
        return value;
    }


    public int getPOPSIZE() {
        return POPSIZE;
    }

    public void setPOPSIZE(int POPSIZE) {
        this.POPSIZE = POPSIZE;
    }

    public int getNvars() {
        return Nvars;
    }

    public void setNvars(int nvars) {
        Nvars = nvars;
    }

    public double getRate_crossover() {
        return rate_crossover;
    }

    public void setRate_crossover(double rate_crossover) {
        this.rate_crossover = rate_crossover;
    }

    public double getF() {
        return F;
    }

    public void setF(double f) {
        F = f;
    }

    public double getLambda() {
        return lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public double getRate_mutate() {
        return rate_mutate;
    }

    public void setRate_mutate(double rate_mutate) {
        this.rate_mutate = rate_mutate;
    }
}
