package util.evaluator;

import pojo.Individual;

import java.util.List;

/**
 * Created by wansenxu@163.com on 2020/11/5
 */
public class F5 implements Evaluator<Individual> {
    private static F5 f5 = new F5();
    private F5(){

    }
    public static F5 getF5(){
        return f5;
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
        for (int i=1;i<solution.size();i++){
            sum+= 100*Math.pow((solution.get(i)-Math.pow(solution.get(i-1),2)),2)+Math.pow(solution.get(i-1)-1,2);
        }
        individual.setFitness(sum -bestKnown);
    }
}
