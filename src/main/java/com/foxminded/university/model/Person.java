package com.foxminded.university.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Person {
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "age")
    private int age;
    @Column(name = "phone")
    private String phone;

    public Person() {
    }

    public Person(String name, String surname, int age, String phone) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", surname=" + surname + ", age=" + age + ", phone=" + phone + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + age;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        return result;
    }

    private boolean bothNullOrEqual(Object thisObj, Object otherObj) {
        if (thisObj == null) {
            if (otherObj != null)
                return false;
        } else if (!thisObj.equals(otherObj))
            return false;
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        return bothNullOrEqual(this.age, other.age) && bothNullOrEqual(this.name, other.name)
            && bothNullOrEqual(this.phone, other.phone) && bothNullOrEqual(this.surname, other.surname);
    }
}
