package util.evolver;

import pojo.Individual;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GeneticEvolver extends Evolver {
    /**
     * @program: EC
     * @description: 遗传算法进化器
     * @author: 许万森
     * @create: 2020-11-07 23:44
     **/

    private double rate_mutate;

    public GeneticEvolver() {
    }

    @Override
    public List<Individual> mutate(List<Individual> pop) {
        for (int i = 0; i < super.getPOPSIZE(); i++) {
            double p = Math.random();
            if (p < rate_mutate) {
                List<Double> solution = new ArrayList<Double>();
                for (int j = 0; j < super.getNvars(); j++) {
                    solution.add(super.getLowerBound() + Math.random() * (super.getUpperBound() - super.getLowerBound()));
                }
                pop.get(i).setSolution(solution);
            }
        }
        return  null;
    }

    @Override
    public void select(List<Individual>... pops) {
        if (pops.length == 2) {
            List<Individual> pop = pops[0];
            List<Individual> tmpPop = new ArrayList<Individual>();;
            //轮盘赌选择法
            Double sumReciprocalOfFit = 0.0;
            for (int i = 0; i < pop.size(); i++) {
                sumReciprocalOfFit += 1.0 / pop.get(i).getFitness();
            }
            List<Double> probability = new ArrayList<Double>();
            for (int i = 0; i < pop.size(); i++) {
                probability.add(1.0 / pop.get(i).getFitness() / sumReciprocalOfFit);
            }
            for (int i = 0; i < super.getPOPSIZE(); i++) {
                double p = Math.random();
                double sumProbability = 0;
                Iterator<Double> iterator = probability.iterator();
                int count = -1;
                while (iterator.hasNext() && sumProbability < p) {
                    sumProbability += iterator.next();
                    count++;
                }
                try {
                    tmpPop.add((Individual) pop.get(count).clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void crossover(List<Individual>... pops) {
        if (pops.length == 1) {
            List<Individual> pop = pops[0];
            int x = 0;//存放待交叉个体的下标
            int count = 0;
            for (int i = 0; i < super.getPOPSIZE(); i++) {
                double p = Math.random();
                if (p < super.getRate_crossover()) {
                    count++;
                    if (count % 2 == 0) {
                        //x,i分别是两个待交叉个体的下标
                        double tmp = 0.0;
                        int j = (int) (Math.random() * super.getNvars());
                        tmp = pop.get(x).getSolution().get(j);
                        pop.get(x).getSolution().set(j, pop.get(i).getSolution().get(j));
                        pop.get(i).getSolution().set(j, tmp);
                    } else {
                        x = i;
                    }
                }
            }
        }
    }

}
