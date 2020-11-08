package util.evaluator;

/**
 * Created by wansenxu@163.com on 2020/11/5
 */
public class EvaluatorFactory {
    public static Evaluator getEvaluator(String function){
        Evaluator evaluator=null;
        if("f1".equals(function)){
            evaluator = new F1();
        }else if("f2".equals(function)){
            evaluator = new F2();
        }else if("f3".equals(function)){
            evaluator = new F3();
        }else if("f4".equals(function)){
            evaluator = new F4();
        }else if("f5".equals(function)){
            evaluator = new F5();
        }else if("f6".equals(function)){
            evaluator = new F6();
        }else if("f7".equals(function)){
            evaluator = new F7();
        }else if("f8".equals(function)){
            evaluator = new F8();
        }else if("f9".equals(function)){
            evaluator = new F9();
        }else if("f10".equals(function)){
            evaluator = new F10();
        }else if("f11".equals(function)){
            evaluator = new F11();
        }else if("f12".equals(function)){
            evaluator = new F12();
        }else if("f13".equals(function)){
            evaluator = new F13();
        }
        return evaluator;
    }
}
