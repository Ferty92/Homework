package ru.sberbank.homework.Polushin.task_02.resources;

import java.util.Objects;
import java.util.Set;

public class Second {
    private Integer i;
    private long l;
    private double d;
    private String string;
    private Set<Integer> set;
    
    public Second(Integer i, long l, double d, String string, Set<Integer> set) {
        
        this.i = i;
        this.l = l;
        this.d = d;
        this.string = string;
        this.set = set;
    }
    
    
    public Second() {
    
    }
    
    public Integer getI() {
        return i;
    }
    
    public void setI(Integer i) {
        this.i = i;
    }
    
    public long getL() {
        return l;
    }
    
    public void setL(long l) {
        this.l = l;
    }
    
    public double getD() {
        return d;
    }
    
    public void setD(double d) {
        this.d = d;
    }
    
    public String getString() {
        return string;
    }
    
    public void setString(String string) {
        this.string = string;
    }
    
    public Set<Integer> getSet() {
        return set;
    }
    
    public void setSet(Set<Integer> set) {
        this.set = set;
    }
    
    
    @Override
    public String toString() {
        return "Second{" +
                "i=" + i +
                ", l=" + l +
                ", d=" + d +
                ", string='" + string + '\'' +
                ", set=" + set +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Second second = (Second) o;
        return l == second.l &&
                Double.compare(second.d, d) == 0 &&
                Objects.equals(i, second.i) &&
                Objects.equals(string, second.string) &&
                Objects.equals(set, second.set);
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(i, l, d, string, set);
    }
    
}