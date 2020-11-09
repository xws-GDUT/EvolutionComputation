package util.evaluator;

import pojo.Individual;

import java.util.List;

/**
 * Created by wansenxu@163.com on 2020/11/5
 */
public class F12 implements Evaluator<Individual> {
    private static F12 f12 = new F12();
    private F12(){

    }
    public static F12 getF12(){
        return f12;
    }
    private final double bestKnown= 0;

    @Override
    public double getBestKnow() {
        return bestKnown;
    }
    @Override
    public void evaluate(Individual individual) {
        double sum=0;
        double tmp1=0;
        List<Double> solution = individual.getSolution();
        for (int i=0;i<solution.size();i++){
            tmp1+=U(solution.get(i),10,100,4);
        }
        double tmp2=0;
        for(int i=0;i<solution.size()-1;i++) {
            tmp2+=Math.pow(Y(solution.get(i))-1,2)*(1+10*Math.pow((Math.sin(Math.PI*Y(solution.get(i+1)))),2));
        }
        sum =Math.PI/solution.size()*(10*Math.pow(Math.sin(Math.PI*Y(solution.get(0))),2)+tmp2+Math.pow(Y(solution.get(solution.size()-1))-1,2))+tmp1;
        individual.setFitness(sum-bestKnown);
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
    private double Y(double x){
        return 1+1/4.0*(x+1);
    }
}
