package util.evaluator;

import pojo.Individual;

import java.util.List;

/**
 * Created by wansenxu@163.com on 2020/11/5
 */
public class F1 implements Evaluator<Individual> {
    private static F1 f1 = new F1();
    private F1(){

    }
    public static F1 getF1(){
        return f1;
    }
    private final double bestKnown= 0;
    @Override
    public void evaluate(Individual individual) {
        double temp = 0;
        List<Double> solution = individual.getSolution();
        for (int i = 0; i < solution.size(); i++){
            temp += solution.get(i)*solution.get(i);
        }
        individual.setFitness(temp-bestKnown);
    }

    @Override
    public double getBestKnow() {
        return bestKnown;
    }
}
