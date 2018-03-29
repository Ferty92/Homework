package ru.sberbank.homework.Polushin.task_02.resources;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class First {
    private Integer i;
    private int l;
    private double d;
    private String string;
    private List<Integer> list;
    private Set<String> set;
    
    public First() {
    
    }
    
    public First(Integer i, int l, double d, String string, List<Integer> list, Set<String> set) {
        
        this.i = i;
        this.l = l;
        this.d = d;
        this.string = string;
        this.list = list;
        this.set = set;
    }
    
    
    public Integer getI() {
        return i;
    }
    
    public void setI(Integer i) {
        this.i = i;
    }
    
    public int getL() {
        return l;
    }
    
    public void setL(int l) {
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
    
    public List<Integer> getList() {
        return list;
    }
    
    public void setList(List<Integer> list) {
        this.list = list;
    }
    
    public Set<String> getSet() {
        return set;
    }
    
    public void setSet(Set<String> set) {
        this.set = set;
    }
    
    @Override
    public String toString() {
        return "First{" +
                "i=" + i +
                ", l=" + l +
                ", d=" + d +
                ", string='" + string + '\'' +
                ", list=" + list +
                ", set=" + set +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        First first = (First) o;
        return l == first.l &&
                Double.compare(first.d, d) == 0 &&
                Objects.equals(i, first.i) &&
                Objects.equals(string, first.string) &&
                Objects.equals(list, first.list) &&
                Objects.equals(set, first.set);
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(i, l, d, string, list, set);
    }
}