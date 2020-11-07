package util.evolver;

import pojo.Individual;
import util.evaluator.Evaluator;

import java.util.List;

/**
 * Created by wansenxu@163.com on 2020/11/4
 */
public interface Evolvable {
    /**
    * @Description: 将种群pop进行初始化操作
    * @Param: 
    * @return: 
    * @Author: wansenxu@163.com
    * @Date: 2020/11/7
    */
    void initPop(List<Individual> pop);
    /**
    * @Description: 从种群pop中获取最优个体
    * @Param: 
    * @return: 
    * @Author: wansenxu@163.com
    * @Date: 2020/11/7
    */
    Individual getBestIndividual(List<Individual> pop);
    /**
    * @Description: 通过evaluator对种群pop进行评价
    * @Param: 
    * @return: 
    * @Author: wansenxu@163.com
    * @Date: 2020/11/7
    */
    void evaluate(List<Individual> pop, Evaluator evaluator);
    /**
    * @Description: 将种群pop进行变异操作
    * @Param: 
    * @return: 
    * @Author: wansenxu@163.com
    * @Date: 2020/11/7
    */
    List<Individual> mutate(List<Individual> pop);
    /**
    * @Description: 对单个或多个种群进行选择操作，选择结果存放在pops[0]  目前只完成了两个种群的变异操作，设计存在缺陷待改进
    * @Param:
    * @return:
    * @Author: wansenxu@163.com
    * @Date: 2020/11/7
    */
    void select(List<Individual>... pops);

    /**
    * @Description: 对单个或多个种群进行交叉操作，交叉结果放在pops[1]   目前只完成了两个种群的交叉操作，设计存在缺陷待改进
    * @Param:
    * @return:
    * @Author: wansenxu@163.com
    * @Date: 2020/11/7
    */
    void crossover(List<Individual>... pops);

}
