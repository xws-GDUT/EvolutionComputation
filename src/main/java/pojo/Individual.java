package pojo;

import java.util.List;

public class Individual implements Cloneable{
    private List<Double> solution;
    private double fitness;

    public Individual() {
    }

    public List<Double> getSolution() {
        return solution;
    }

    public void setSolution(List<Double> solution) {
        this.solution = solution;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }


    @Override
    public String toString() {
        return "Individual{" +
                "solution=" + solution +
                ", fitness=" + fitness +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

