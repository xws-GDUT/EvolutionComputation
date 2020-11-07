package util.evaluator;

import pojo.Individual;

import java.util.List;

/**
 * Created by wansenxu@163.com on 2020/11/5
 */
public class F11 implements Evaluator<Individual> {
    private final double bestKnown= 0;

    @Override
    public double getBestKnow() {
        return bestKnown;
    }
    @Override
    public void evaluate(Individual individual) {
        double sum;
        double sum_plus=0;
        double sum_multiply=1;
        List<Double> solution = individual.getSolution();
        for (int i = 0; i < solution.size(); i++){
            sum_plus += Math.pow(solution.get(i),2);
            sum_multiply *= Math.cos(solution.get(i)/Math.sqrt(i+1));
        }
        sum = (1/4000)*sum_plus - sum_multiply +1;
        individual.setFitness(sum -bestKnown);
    }
}
