package util.evaluator;

import pojo.Individual;

import java.util.List;

/**
 * Created by wansenxu@163.com on 2020/11/5
 */
public class F3 implements Evaluator<Individual> {
    private final double bestKnown= 0;

    @Override
    public void evaluate(Individual individual) {
        double sum=0;
        List<Double> solution = individual.getSolution();
        for (int i = 0; i < solution.size(); i++){
            double sub_sum=0;
            for(int j=0;j<i;j++){
                sub_sum+=solution.get(j);
            }
            sum+=Math.pow(sub_sum,2);
        }
        individual.setFitness(sum-bestKnown);
    }

    @Override
    public double getBestKnow() {
        return bestKnown;
    }
}
