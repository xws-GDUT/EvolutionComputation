package util.evolver;

import Paraments.Parament;
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
    //取值范围
    private Double upperBound;
    private Double lowerBound;

    public Evolver() {
    }

    public Evolver(Double upperBound, Double lowerBound) {
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
        for (int i = 0; i < Parament.POPSIZE; i++) {
            Individual individual = new Individual();
            List<Double> solution= new ArrayList<Double>();
            for(int k=0;k<Parament.Nvars;k++){
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
            bestIndividual= (Individual) pop.get(0).clone();
            for(int j=1;j<pop.size();j++){
                if(pop.get(j).getFitness()<bestIndividual.getFitness()){
                    bestIndividual= (Individual) pop.get(j).clone();
                }
            }
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


}
