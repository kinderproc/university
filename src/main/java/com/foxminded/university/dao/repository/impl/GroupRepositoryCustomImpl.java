package com.foxminded.university.dao.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.foxminded.university.dao.repository.GroupRepositoryCustom;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Group_;
import com.foxminded.university.model.Student;
import com.foxminded.university.model.Student_;

@Repository
@Transactional
public class GroupRepositoryCustomImpl implements GroupRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Group> findAllForStudent(int studentId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Group> groupQuery = cb.createQuery(Group.class);

        Root<Group> rootGroup = groupQuery.from(Group.class);

        Subquery<Student> studentSubquery = groupQuery.subquery(Student.class);
        Root<Student> rootStudent = studentSubquery.from(Student.class);

        studentSubquery.select(rootStudent).where(
            cb.equal(rootStudent.get(Student_.group).get(Group_.id), rootGroup.get(Group_.id)),
            cb.equal(rootStudent.get(Student_.id), studentId));

        Predicate groupIsNotDeleted = cb.equal(rootGroup.get(Group_.deleted), false);
        Predicate studentBelongsToTheGroup = cb.exists(studentSubquery);
        Predicate summaryPredicate = cb.or(groupIsNotDeleted, studentBelongsToTheGroup);
        groupQuery.select(rootGroup).where(summaryPredicate);

        return em.createQuery(groupQuery).getResultList();
    }
}
