package util.evolver;

import pojo.Individual;
import util.evaluator.Evaluator;

import java.util.List;

/**
 * Created by wansenxu@163.com on 2020/11/4
 */
public interface Evolvable {
    void initPop(List<Individual> pop);
    Individual getBestIndividual(List<Individual> pop);
    void evaluate(List<Individual> pop, Evaluator evaluator);
    List<Individual> mutate(List<Individual> pop);
    void select(List<Individual>... pops);
    void crossover(List<Individual>... pops);

}
