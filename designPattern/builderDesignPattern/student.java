package designPattern.builderDesignPattern;

import java.util.ArrayList;
import java.util.List;
class Student{
    private int roll;
    private String name;
    private String city;
    private List<String>subjects;
    public Student(Studentbuilder studentbuilder)
    {
        roll=studentbuilder.roll;
        name=studentbuilder.name;
        subjects=studentbuilder.subjects;
    }
}

abstract class Studentbuilder{
    public int roll; public String name; public List<String>subjects;

    public Studentbuilder setRoll(int roll){ this.roll=roll; return this;}
    public Studentbuilder setName(String name){ this.name=name; return this;}
    abstract public Studentbuilder setSubjects();
    public Student build(){return new Student(this);}
}

class MbaStudentBuilder extends Studentbuilder {
    @Override
    public Studentbuilder setSubjects() {
        List<String> subjects = new ArrayList<>();subjects.add("economics");subjects.add("microEco");
        return this;
    }
}
class EnginnerStudentBuilder extends Studentbuilder {
    @Override
    public Studentbuilder setSubjects() {
        List<String> subjects = new ArrayList<>();subjects.add("daa");subjects.add("cpp");
        return this;
    }
}

class StudentDirector{
    public Student createMbaStudent()
    {
        Studentbuilder mbaStudent =new MbaStudentBuilder().setRoll(1).setName("abc").setSubjects();
        return mbaStudent.build();
    }

    public Student createEngineerStudent()
    {
        Studentbuilder enginnerStudent =new EnginnerStudentBuilder().setRoll(12).setName("absdcc").setSubjects();
        return enginnerStudent.build();
    }
}

public class student {
    public static void main(String[] args)
    {
        StudentDirector director=new StudentDirector();
        Student enginner=director.createEngineerStudent();
    }
}
