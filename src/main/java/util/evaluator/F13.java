package util.evaluator;

import pojo.Individual;

import java.util.List;

/**
 * Created by wansenxu@163.com on 2020/11/5
 */
public class F13 implements Evaluator<Individual> {
    private static F13 f13 = new F13();
    private F13(){

    }
    public static F13 getF13(){
        return f13;
    }
    private final double bestKnown= 0;

    @Override
    public double getBestKnow() {
        return bestKnown;
    }
    @Override
    public void evaluate(Individual individual) {
        List<Double> solution = individual.getSolution();
        double sum=0;
        double tmp1=0;
        for(int i=0;i<solution.size()-1;i++) {
            tmp1+= Math.pow((solution.get(i)-1),2)*(1+Math.pow(Math.sin(3*Math.PI*solution.get(i+1)),2));
        }
        double tmp2=0;
        for(int i=0;i<solution.size();i++) {
            tmp2+=U(solution.get(i),5,100,4);
        }
        sum = (0.1)*(Math.pow(Math.sin(Math.PI*3*solution.get(0)),2)+tmp1+Math.pow(solution.get(solution.size()-1),2)*(1+Math.pow(Math.sin(2*Math.PI*solution.get(solution.size()-1)),2)))+tmp2;
        individual.setFitness(sum -bestKnown);
    }
    private double U(double x,double a,double k,double m){
        if(x>a){
            return k*Math.pow((x-a),m);
        }else if(x>=-a&&x<=a){
            return 0;
        }else{
            return k*Math.pow((-x-a),m);
        }
    }
}
