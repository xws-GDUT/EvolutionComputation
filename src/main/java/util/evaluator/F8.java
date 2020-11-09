package util.evaluator;

import pojo.Individual;

import java.util.List;

/**
 * Created by wansenxu@163.com on 2020/11/5
 */
public class F8 implements Evaluator<Individual> {
    private static F8 f8 = new F8();
    private F8(){

    }
    public static F8 getF8(){
        return f8;
    }
    private final double bestKnown= -12569.5;
    @Override
    public void evaluate(Individual individual) {
        double sum=0;
        List<Double> solution = individual.getSolution();
        for (int i=0;i<solution.size();i++){
            sum-= (solution.get(i))*Math.sin(Math.sqrt(Math.abs(solution.get(i))));
        }
        individual.setFitness(sum-bestKnown);
    }
    @Override
    public double getBestKnow() {
        return bestKnown;
    }
}
