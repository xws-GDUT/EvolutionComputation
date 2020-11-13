package util.evolver;

import pojo.Individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @program: EC
 *
 * @description: 差分演化算法进化器
 *
 * @author: 许万森
 *
 * @create: 2020-11-07 23:44
 **/
public class DifferentialEvolver extends Evolver {
    //进化策略
    private String mutateType;
    private Integer differentialVectorNo;
    private String crossoverType;

    public DifferentialEvolver() {
    }

    public DifferentialEvolver(String mutateType, Integer differentialVectorNo, String crossoverType) {
        this.mutateType = mutateType;
        this.differentialVectorNo = differentialVectorNo;
        this.crossoverType = crossoverType;
    }

    @Override
    public List<Individual> mutate(List<Individual> pop) {
        List<Individual> mutatedPop = new ArrayList<Individual>();
        if("rand".equals(mutateType)) {//基于随机个体
            if (differentialVectorNo == 1) {//单向量差
                for (int i = 0; i < pop.size(); i++) {
                    Individual individual =new Individual();
                    List<Double> solution = new ArrayList<Double>();
                    //获取三个随机数 s.t. 三个随机数与i两两不等
                    List<Integer> indexes = new ArrayList<>();
                    indexes.add(i);
                    while(indexes.size()<=4){
                        Integer index = new Random().nextInt(100);
                        if(!indexes.contains(index)){
                            indexes.add(index);
                        }
                    }
                    for (int j = 0; j < super.getNvars(); j++) {
                        double value= pop.get(indexes.get(1)).getSolution().get(j) + super.getF() * (pop.get(indexes.get(2)).getSolution().get(j) - pop.get(indexes.get(3)).getSolution().get(j));
                        value=handleOutOfBoundary(value,super.getLowerBound(),super.getUpperBound());
                        solution.add(value);
                    }
                    individual.setSolution(solution);
                    mutatedPop.add(individual);
                }
            } else if(differentialVectorNo == 2){//双向量差
                for (int i = 0; i < pop.size(); i++) {
                    Individual individual = new Individual();
                    List<Double> solution = new ArrayList<Double>();
                    //获得五个在[0,99]且两两不等的五个数
                    List<Integer> indexes = new ArrayList<>();
                    indexes.add(i);
                    while(indexes.size()<=6){
                        Integer index = new Random().nextInt(100);
                        if(!indexes.contains(index)){
                            indexes.add(index);
                        }
                    }
                    for (int j = 0; j < super.getNvars(); j++) {
                        double value=pop.get(indexes.get(1)).getSolution().get(j) + super.getF() * (pop.get(indexes.get(2)).getSolution().get(j)+pop.get(indexes.get(3)).getSolution().get(j) - pop.get(indexes.get(4)).getSolution().get(j)- pop.get(indexes.get(5)).getSolution().get(j));
                        value=handleOutOfBoundary(value,super.getLowerBound(),super.getUpperBound());
                        solution.add(value);
                    }
                    individual.setSolution(solution);
                    mutatedPop.add(individual);
                }
            }else{
                System.out.println("仅能选择单向量差或双向量差");
                System.exit(0);
            }
        }else if("best".equals(mutateType)){
            Individual bestIndividual = getBestIndividual(pop);
            if (differentialVectorNo == 1) {//单向量差
                for (int i = 0; i < pop.size(); i++) {
                    Individual individual = new Individual();
                    List<Double> solution = new ArrayList<Double>();
                    List<Integer> indexes = new ArrayList<>();
                    indexes.add(i);
                    while(indexes.size()<=3){
                        Integer index = new Random().nextInt(100);
                        if(!indexes.contains(index)){
                            indexes.add(index);
                        }
                    }
                    for (int j = 0; j < super.getNvars(); j++) {
                        double value = bestIndividual.getSolution().get(j) + super.getF() * (pop.get(indexes.get(1)).getSolution().get(j) - pop.get(indexes.get(2)).getSolution().get(j));
                        value=handleOutOfBoundary(value,super.getLowerBound(),super.getUpperBound());
                        solution.add(value);
                    }
                    individual.setSolution(solution);
                    mutatedPop.add(individual);
                }
            } else if(differentialVectorNo == 2){//双向量差
                for (int i = 0; i < pop.size(); i++) {
                    Individual individual = new Individual();
                    List<Double> solution = new ArrayList<Double>();
                    List<Integer> indexes = new ArrayList<>();
                    indexes.add(i);
                    while(indexes.size()<=5){
                        Integer index = new Random().nextInt(100);
                        if(!indexes.contains(index)){
                            indexes.add(index);
                        }
                    }
                    for (int j = 0; j < super.getNvars(); j++) {
                        double value =bestIndividual.getSolution().get(j) + super.getF() * (pop.get(indexes.get(1)).getSolution().get(j)+pop.get(indexes.get(2)).getSolution().get(j) - pop.get(indexes.get(3)).getSolution().get(j)- pop.get(indexes.get(4)).getSolution().get(j));
                        value=handleOutOfBoundary(value,super.getLowerBound(),super.getUpperBound());
                        solution.add(value);
                    }
                    individual.setSolution(solution);
                    mutatedPop.add(individual);
                }
            }else{
                System.out.println("仅能选择单向量差或双向量差");
                System.exit(0);
            }
        }else if("randToBest".equals(mutateType)){ //randToBest均采用单差分向量
            Individual bestIndividual = getBestIndividual(pop);
            for (int i = 0; i < pop.size(); i++) {
                Individual individual = new Individual();
                List<Double> solution = new ArrayList<Double>();
                List<Integer> indexes = new ArrayList<>();
                indexes.add(i);
                while(indexes.size()<=3){
                    Integer index = new Random().nextInt(100);
                    if(!indexes.contains(index)){
                        indexes.add(index);
                    }
                }
                for (int j = 0; j < super.getNvars(); j++) {
                    double value = pop.get(i).getSolution().get(j) + super.getLambda()*(bestIndividual.getSolution().get(j)-pop.get(i).getSolution().get(j))+ super.getF() * (pop.get(indexes.get(1)).getSolution().get(j) - pop.get(indexes.get(2)).getSolution().get(j));
                    value=handleOutOfBoundary(value,super.getLowerBound(),super.getUpperBound());
                    solution.add(value);
                }
                individual.setSolution(solution);
                mutatedPop.add(individual);
            }
        }else{
            System.out.println("仅能选择基于随机个体(rand)或基于最优个体(best)");
            System.exit(0);
        }
        return mutatedPop;
    }



    @Override
    public void select(List<Individual>... pops) {
        List<Individual> new_pop= new ArrayList<Individual>();
        if(pops.length==1){
            //单种群选择
        }else if(pops.length ==2){
            //双种群选择
            List<Individual> pop=pops[0];
            List<Individual> crossoveredPop=pops[1];
            for (int i=0;i<pop.size();i++){
                if(pop.get(i).getFitness()>crossoveredPop.get(i).getFitness()){
                    pop.set(i,crossoveredPop.get(i));
                }
            }
        }
    }

    @Override
    public void crossover(List<Individual>... pops) {
        if(pops.length==2){
            List<Individual> pop = pops[0];
            List<Individual> tmpPop=pops[1];
            if("bin".equals(crossoverType)){//二项交叉
                for(int i=0;i<tmpPop.size();i++){
                    int popisitonOfCrossover = new Random().nextInt(pop.get(0).getSolution().size());
                    Individual tmpIndividual = tmpPop.get(i);
                    List<Double> tmpSolution = tmpIndividual.getSolution();
                    for(int j = 0; j< pop.get(0).getSolution().size(); j++){
                        double p = Math.random();
                        if( p > super.getRate_crossover() && popisitonOfCrossover != j){
                            tmpSolution.set(j,pop.get(i).getSolution().get(j));
                        }
                    }
                }
            }else if("exp".equals(crossoverType)){//指数交叉
                for(int i=0;i<tmpPop.size();i++){
                    int popisitonOfCrossover = new Random().nextInt(pop.get(0).getSolution().size());
                    Individual tmpIndividual = tmpPop.get(i);
                    List<Double> tmpSolution = tmpIndividual.getSolution();
                    for(int j=0;j<=popisitonOfCrossover;j++){
                        if(j<popisitonOfCrossover){
                            tmpSolution.set(j,pop.get(i).getSolution().get(j));
                        }
                    }
                    for(int j = popisitonOfCrossover+1; j< pop.get(0).getSolution().size(); j++) {
                        double p = Math.random();
                        if (p <= super.getRate_crossover()) {
                            continue;
                        }

                        while(j<  pop.get(0).getSolution().size()){
                            tmpSolution.set(j,pop.get(i).getSolution().get(j));
                            j++;
                        }
                    }
                }
            }else{
                System.out.println("仅能选择二项式交叉(bin)或指数交叉(exp)");
                System.exit(0);
            }
        }
    }

    public String getMutateType() {
        return mutateType;
    }

    public void setMutateType(String mutateType) {
        this.mutateType = mutateType;
    }

    public Integer getDifferentialVectorNo() {
        return differentialVectorNo;
    }

    public void setDifferentialVectorNo(Integer differentialVectorNo) {
        this.differentialVectorNo = differentialVectorNo;
    }

    public String getCrossoverType() {
        return crossoverType;
    }

    public void setCrossoverType(String crossoverType) {
        this.crossoverType = crossoverType;
    }


}
