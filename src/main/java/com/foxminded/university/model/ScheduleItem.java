package com.foxminded.university.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleItem {
    private Date date;
    private Lection lection;
    private Room room;
    private Subject subject;
    private Teacher teacher;
    private List<Group> groups = new ArrayList<>();

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Lection getLection() {
        return lection;
    }

    public void setLection(Lection lection) {
        this.lection = lection;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((lection == null) ? 0 : lection.hashCode());
        result = prime * result + ((room == null) ? 0 : room.hashCode());
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
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
        if (!(obj instanceof ScheduleItem))
            return false;
        ScheduleItem other = (ScheduleItem) obj;
        return bothNullOrEqual(this.date, other.date) && bothNullOrEqual(this.lection, other.lection)
            && bothNullOrEqual(this.room, other.room) && bothNullOrEqual(this.subject, other.subject)
            && bothNullOrEqual(this.teacher, other.teacher);
    }
}
