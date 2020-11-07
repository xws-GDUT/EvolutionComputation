package util.evolver;

import pojo.Individual;
import util.evaluator.Evaluator;

import java.util.List;

public class GeneticAlgorithm extends Evolver {
    @Override
    public void initPop(List<Individual> pop) {

    }

    @Override
    public Individual getBestIndividual(List<Individual> pop) {
        return null;
    }

    @Override
    public void evaluate(List<Individual> pop, Evaluator evaluator) {

    }

    @Override
    public List<Individual> mutate(List<Individual> pop) {
        return null;
    }

    @Override
    public void select(List<Individual>... pops) {

    }

    @Override
    public void crossover(List<Individual>... pops) {

    }
    /**
     * @program: EC
     *
     * @description: 遗传算法进化器
     *
     * @author: 许万森
     *
     * @create: 2020-11-07 23:44
     **/
}
