package com.example.onlinecourses.model;


import lombok.*;
@Setter @Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private String name;
    private int age;
    private int experience;
    private double hourlyRate;
    private int id;


    public Teacher(Teacher teacher) {
        this.name = teacher.getName();
        this.age = teacher.getAge();
        this.experience = teacher.getExperience();
        this.hourlyRate = teacher.getHourlyRate();
        this.id = teacher.getId();
    }


    public void restoreState(Teacher teacher) {
        teacher.setName(this.name);
        teacher.setAge(this.age);
        teacher.setExperience(this.experience);
        teacher.setHourlyRate(this.hourlyRate);
        teacher.setId(this.id);
    }
}
