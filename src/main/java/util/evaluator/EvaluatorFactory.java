package util.evaluator;

/**
 * Created by wansenxu@163.com on 2020/11/5
 */
public class EvaluatorFactory {
    public static Evaluator getEvaluator(String function){
        Evaluator evaluator=null;
        if("F1".equals(function)){
            evaluator = new F1();
        }else if("F2".equals(function)){
            evaluator = new F2();
        }else if("F3".equals(function)){
            evaluator = new F3();
        }else if("F4".equals(function)){
            evaluator = new F4();
        }else if("F5".equals(function)){
            evaluator = new F5();
        }else if("F6".equals(function)){
            evaluator = new F6();
        }else if("F7".equals(function)){
            evaluator = new F7();
        }else if("F8".equals(function)){
            evaluator = new F8();
        }else if("F9".equals(function)){
            evaluator = new F9();
        }else if("F10".equals(function)){
            evaluator = new F10();
        }else if("F11".equals(function)){
            evaluator = new F11();
        }else if("F12".equals(function)){
            evaluator = new F12();
        }else if("F13".equals(function)){
            evaluator = new F13();
        }
        return evaluator;
    }
}
