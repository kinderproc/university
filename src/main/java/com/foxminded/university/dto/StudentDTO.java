package com.foxminded.university.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.foxminded.university.validation.annotation.GroupCapacityConstraint;

@GroupCapacityConstraint(template = "{group.capacity.limit}")
public class StudentDTO {
    private int id;
    @Size(max = 30, message = "The name is too long")
    @NotBlank(message = "Enter the student name")
    private String name;

    @Size(max = 30, message = "The surname is too long")
    @NotBlank(message = "Enter the student surname")
    private String surname;

    @Min(18)
    @Max(70)
    private String age;

    @Pattern(regexp = "(^\\+7|8)[0-9]{10,11}", message = "phone must starts from '+7' or '8' and contain 11 or 12 numbers")
    private String phone;

    private int groupId;

    private String groupName;

    private boolean deleted;

    public StudentDTO() {
    }

    public StudentDTO(int id, String name, String surname, String age, String phone, int groupId, String groupName,
        boolean deleted) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.phone = phone;
        this.groupId = groupId;
        this.deleted = deleted;
        this.groupName = groupName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((age == null) ? 0 : age.hashCode());
        result = prime * result + (deleted ? 1231 : 1237);
        result = prime * result + groupId;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StudentDTO other = (StudentDTO) obj;
        if (!age.equals(other.age))
            return false;
        if (deleted != other.deleted)
            return false;
        if (groupId != other.groupId)
            return false;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        if (surname == null) {
            if (other.surname != null)
                return false;
        } else if (!surname.equals(other.surname))
            return false;
        return true;
    }
}
