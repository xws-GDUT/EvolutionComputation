package util.evaluator;

import pojo.Individual;

import java.util.List;

/**
 * Created by wansenxu@163.com on 2020/11/5
 */
public class F4 implements Evaluator<Individual> {
    private static F4 f4 = new F4();
    private F4(){

    }
    public static F4 getF4(){
        return f4;
    }
    private final double bestKnown= 0;

    @Override
    public double getBestKnow() {
        return bestKnown;
    }
    @Override
    public void evaluate(Individual individual) {
        double max;
        List<Double> solution = individual.getSolution();
        max=Math.abs(solution.get(0));
        for (int i = 1; i < solution.size(); i++){
            if(max<Math.abs(solution.get(i))){
                max=Math.abs(solution.get(i));
            }
        }
        individual.setFitness(max-bestKnown);
    }
}
