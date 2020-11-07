package util.evaluator;

import pojo.Individual;

import java.util.List;

/**
 * Created by wansenxu@163.com on 2020/11/5
 */
public class F9 implements Evaluator<Individual> {
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
            sum+=(Math.pow(solution.get(i),2)-10*Math.cos(2*Math.PI*solution.get(i))+10);
        }
        individual.setFitness(sum -bestKnown);
    }
}
