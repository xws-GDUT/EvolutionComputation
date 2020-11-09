package util.evaluator;

import pojo.Individual;

import java.util.List;

/**
 * Created by wansenxu@163.com on 2020/11/5
 */
public class F2 implements Evaluator<Individual> {
    private static F2 f2 = new F2();
    private F2(){

    }
    public static F2 getF2(){
        return f2;
    }
    private final double bestKnown= 0;
    @Override
    public void evaluate(Individual individual) {
        double sum_plus=0;
        double sum_multiply=1;
        List<Double> solution = individual.getSolution();
        for (int i = 0; i < solution.size(); i++){
            sum_plus += Math.abs(solution.get(i));
            sum_multiply*=Math.abs(solution.get(i));
        }
        individual.setFitness(sum_plus+sum_multiply-bestKnown);
    }

    @Override
    public double getBestKnow() {
        return bestKnown;
    }

}
