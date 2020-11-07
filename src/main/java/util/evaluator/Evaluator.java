package util.evaluator;

/**
 * Created by wansenxu@163.com on 2020/11/5
 */
public interface Evaluator<E> {
    void evaluate(E e);
    double getBestKnow();
}
