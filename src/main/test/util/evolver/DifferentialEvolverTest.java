package util.evolver;

import org.junit.Assert;
import pojo.Individual;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by wansenxu@163.com on 2020/11/11
 */
class DifferentialEvolverTest {


    @org.junit.jupiter.api.Test
    void select() {


    }

    @org.junit.jupiter.api.Test
    void crossover() {
        for (int i = 0; i < 10; i++) {
            List<Individual> pop= new ArrayList<>();
            List<Individual> target = new ArrayList<>();
            Individual individual1 = new Individual();
            List<Double> solution1 = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                solution1.add((double) j);
            }
            individual1.setSolution(solution1);
            pop.add(individual1);
            Individual individual2 = new Individual();
            List<Double> solution2 = new ArrayList<>();
            for (int j = 10; j < 20; j++) {
                solution2.add((double) j);
            }
            individual2.setSolution(solution2);
            target.add(individual2);

            DifferentialEvolver differentialEvolver = new DifferentialEvolver();
            differentialEvolver.setCrossoverType("bin");
            differentialEvolver.setRate_crossover(0);
            differentialEvolver.crossover(pop,target);
            System.out.println(target.get(0).getSolution());
            boolean flag=false;
            for (int i1 = 0; i1 < solution1.size(); i1++) {
                if(solution2.get(i1)>9.0){
                    flag=true;
                    break;
                }
            }
            Assert.assertTrue(flag);
        }
    }


}