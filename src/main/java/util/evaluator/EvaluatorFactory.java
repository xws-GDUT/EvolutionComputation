package util.evaluator;

/**
 * Created by wansenxu@163.com on 2020/11/5
 */
public class EvaluatorFactory {
    public static Evaluator getEvaluator(String function){
        Evaluator evaluator=null;
        if("f1".equalsIgnoreCase(function)){
            evaluator = F1.getF1();
        }else if("f2".equalsIgnoreCase(function)){
            evaluator = F2.getF2();
        }else if("f3".equalsIgnoreCase(function)){
            evaluator = F3.getF3();
        }else if("f4".equalsIgnoreCase(function)){
            evaluator = F4.getF4();
        }else if("f5".equalsIgnoreCase(function)){
            evaluator = F5.getF5();
        }else if("f6".equalsIgnoreCase(function)){
            evaluator = F6.getF6();
        }else if("f7".equalsIgnoreCase(function)){
            evaluator = F7.getF7();
        }else if("f8".equalsIgnoreCase(function)){
            evaluator = F8.getF8();
        }else if("f9".equalsIgnoreCase(function)){
            evaluator = F9.getF9();
        }else if("f10".equalsIgnoreCase(function)){
            evaluator = F10.getF10();
        }else if("f11".equalsIgnoreCase(function)){
            evaluator = F11.getF11();
        }else if("f12".equalsIgnoreCase(function)){
            evaluator = F12.getF12();
        }else if("f13".equalsIgnoreCase(function)){
            evaluator = F13.getF13();
        }
        return evaluator;
    }
}
