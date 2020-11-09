package util.evaluator;

import pojo.Individual;

import java.util.List;

/**
 * Created by wansenxu@163.com on 2020/11/5
 */
public class F6 implements Evaluator<Individual> {
    private static F6 f6 = new F6();
    private F6(){

    }
    public static F6 getF6(){
        return f6;
    }
    private final double bestKnown= 0;

    @Override
    public double getBestKnow() {
        return bestKnown;
    }
    @Override
    public void evaluate(Individual individual) {
        double sum=0;
        List<Double> solution = individual.getSolution();
        for (int i=0;i<solution.size();i++){
            sum+=Math.pow(Math.floor(solution.get(i)+0.5),2);
        }
        individual.setFitness(sum -bestKnown);
    }
}
