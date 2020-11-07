package util.evaluator;

import pojo.Individual;

import java.util.List;

/**
 * Created by wansenxu@163.com on 2020/11/5
 */
public class F10 implements Evaluator<Individual> {
    private final double bestKnown= 0;

    @Override
    public double getBestKnow() {
        return bestKnown;
    }
    @Override
    public void evaluate(Individual individual) {
        double sum;
        double tmp1=0;
        double tmp2=0;
        List<Double> solution = individual.getSolution();
        for (int i = 0; i < solution.size(); i++){
            tmp1 += Math.pow(solution.get(i),2);
            tmp2 += Math.cos(2*Math.PI*solution.get(i));
        }
        sum= -20*Math.exp(-0.2*Math.sqrt(tmp1/solution.size()))-Math.exp(tmp2/solution.size())+20+Math.exp(1);
        individual.setFitness(sum -bestKnown);
    }
}
