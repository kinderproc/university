package com.foxminded.university.repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.foxminded.university.dto.GroupDTO;
import com.foxminded.university.dto.StudentDTO;
import com.foxminded.university.model.Department;
import com.foxminded.university.model.Faculty;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Lection;
import com.foxminded.university.model.Position;
import com.foxminded.university.model.Room;
import com.foxminded.university.model.Schedule;
import com.foxminded.university.model.ScheduleItem;
import com.foxminded.university.model.Student;
import com.foxminded.university.model.Subject;
import com.foxminded.university.model.Teacher;
import com.foxminded.university.model.University;

public class TestRepository {
    public static final String IFORMATION_TECHNOLOGIES = "Information technologies";
    public static final String COMPUTER_SCIENCE = "Computer science";
    public static final String MATRIX_HACKING = "Matrix hacking";
    public static final String MARKSIZM = "Marksizm";
    public static final String LENINIZM = "Leninizm";
    public static final String GENIUS_GENIUSES = "Genius geniuses";
    public static final String DUMMY_DUMMIES = "Dummy dummies";

    static final DateFormat FMT_DATE = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
    static final DateFormat FMT_TIME = new SimpleDateFormat("hh:mm", Locale.ENGLISH);

    public static Date firstOfJuly() throws ParseException {
        return FMT_DATE.parse("01.07.2019");
    }

    public static Date secondOfJuly() throws ParseException {
        return FMT_DATE.parse("02.07.2019");
    }

    public static Date thirdOfJuly() throws ParseException {
        return FMT_DATE.parse("03.07.2019");
    }

    public static Date ninthOfJuly() throws ParseException {
        return FMT_DATE.parse("09.07.2019");
    }

    public static Date tenthOfJuly() throws ParseException {
        return FMT_DATE.parse("10.07.2019");
    }

    public static Date twelfthOfJuly() throws ParseException {
        return FMT_DATE.parse("12.07.2019");
    }

    public static Date twentyNinthOfJuly() throws ParseException {
        return FMT_DATE.parse("29.07.2019");
    }

    public static Date thirtiethOfJuly() throws ParseException {
        return FMT_DATE.parse("30.07.2019");
    }

    public static Date thirtyFirstOfJuly() throws ParseException {
        return FMT_DATE.parse("31.07.2019");
    }

    public static List<ScheduleItem> getJulySchedule() {
        List<ScheduleItem> scheduleItems = new ArrayList<>();
        try {
            scheduleItems.add(new ScheduleItem() {
                {
                    Date date = firstOfJuly();
                    setDate(date);
                    setLection(getLections().get(0));
                    setRoom(getRooms().get(101));
                    setSubject(new Subject());
                    setTeacher(getJohnSmith());
                    addGroup(getDummiesGroup());
                }
            });

            scheduleItems.add(new ScheduleItem() {
                {
                    Date date = secondOfJuly();
                    setDate(date);
                    setLection(getLections().get(1));
                    setRoom(getRooms().get(102));
                    setSubject(new Subject());
                    setTeacher(getJohnSmith());
                    addGroup(getDummiesGroup());
                }
            });

            scheduleItems.add(new ScheduleItem() {
                {
                    Date date = thirdOfJuly();
                    setDate(date);
                    setLection(getLections().get(2));
                    setRoom(getRooms().get(103));
                    setSubject(new Subject());
                    setTeacher(getJohnSmith());
                    addGroup(getDummiesGroup());
                }
            });

            scheduleItems.add(new ScheduleItem() {
                {
                    Date date = tenthOfJuly();
                    setDate(date);
                    setLection(getLections().get(3));
                    setRoom(getRooms().get(201));
                    setSubject(new Subject());
                    setTeacher(getJohnSmith());
                    addGroup(getDummiesGroup());
                }
            });

            scheduleItems.add(new ScheduleItem() {
                {
                    Date date = twelfthOfJuly();
                    setDate(date);
                    setLection(getLections().get(4));
                    setRoom(getRooms().get(202));
                    setSubject(new Subject());
                    setTeacher(getJohnSmith());
                    addGroup(getDummiesGroup());
                }
            });

            scheduleItems.add(new ScheduleItem() {
                {
                    Date date = thirtiethOfJuly();
                    setDate(date);
                    setLection(getLections().get(5));
                    setRoom(getRooms().get(203));
                    setSubject(new Subject());
                    setTeacher(getJohnSmith());
                    addGroup(getDummiesGroup());
                }
            });

            scheduleItems.add(new ScheduleItem() {
                {
                    Date date = thirtyFirstOfJuly();
                    setDate(date);
                    setLection(getLections().get(6));
                    setRoom(getRooms().get(301));
                    setSubject(new Subject());
                    setTeacher(getJohnSmith());
                    addGroup(getDummiesGroup());
                }
            });

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            scheduleItems.clear();
        }

        return scheduleItems;
    }

    public static List<ScheduleItem> getJulyScheduleFrom1stTo9th() throws ParseException {
        List<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>() {
            {
                add(new ScheduleItem() {
                    {
                        Date date = firstOfJuly();
                        setDate(date);
                        setLection(getLections().get(0));
                        setRoom(getRooms().get(101));
                        setSubject(new Subject());
                        setTeacher(getJohnSmith());
                        addGroup(getDummiesGroup());
                    }
                });

                add(new ScheduleItem() {
                    {
                        Date date = secondOfJuly();
                        setDate(date);
                        setLection(getLections().get(1));
                        setRoom(getRooms().get(102));
                        setSubject(new Subject());
                        setTeacher(getJohnSmith());
                        addGroup(getDummiesGroup());
                    }
                });

                add(new ScheduleItem() {
                    {
                        Date date = thirdOfJuly();
                        setDate(date);
                        setLection(getLections().get(2));
                        setRoom(getRooms().get(103));
                        setSubject(new Subject());
                        setTeacher(getJohnSmith());
                        addGroup(getDummiesGroup());
                    }
                });
            }
        };

        return scheduleItems;
    }

    public static List<ScheduleItem> getJulyScheduleFrom10To12() throws ParseException {
        List<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>() {
            {
                add(new ScheduleItem() {
                    {
                        Date date = tenthOfJuly();
                        setDate(date);
                        setLection(getLections().get(3));
                        setRoom(getRooms().get(201));
                        setSubject(new Subject());
                        setTeacher(getJohnSmith());
                        addGroup(getDummiesGroup());
                    }
                });

                add(new ScheduleItem() {
                    {
                        Date date = twelfthOfJuly();
                        setDate(date);
                        setLection(getLections().get(4));
                        setRoom(getRooms().get(202));
                        setSubject(new Subject());
                        setTeacher(getJohnSmith());
                        addGroup(getDummiesGroup());
                    }
                });
            }
        };

        return scheduleItems;
    }

    public static List<ScheduleItem> getJulyScheduleFrom30To31() throws ParseException {
        List<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>() {
            {
                add(new ScheduleItem() {
                    {
                        Date date = thirtiethOfJuly();
                        setDate(date);
                        setLection(getLections().get(5));
                        setRoom(getRooms().get(203));
                        setSubject(new Subject());
                        setTeacher(getJohnSmith());
                        addGroup(getDummiesGroup());
                    }
                });

                add(new ScheduleItem() {
                    {
                        Date date = thirtyFirstOfJuly();
                        setDate(date);
                        setLection(getLections().get(6));
                        setRoom(getRooms().get(301));
                        setSubject(new Subject());
                        setTeacher(getJohnSmith());
                        addGroup(getDummiesGroup());
                    }
                });
            }
        };

        return scheduleItems;
    }

    public static Teacher getJohnSmith() {
        Teacher johnSmith = new Teacher();
        johnSmith.setAge(59);
        johnSmith.setName("John");
        johnSmith.setSurname("Smith");
        johnSmith.setPhone("+77777777777");
        johnSmith.setPosition(Position.PROFESSOR);
        johnSmith.setSubjects(new ArrayList<Subject>() {
            {
                add(getSubjects().get(COMPUTER_SCIENCE));
                add(getSubjects().get(MATRIX_HACKING));
            }
        });

        return johnSmith;
    }

    public static Teacher getValentinaOctjabr() {
        Teacher valentinaOctjabr = new Teacher();
        valentinaOctjabr.setAge(74);
        valentinaOctjabr.setName("Valentina");
        valentinaOctjabr.setSurname("Octjabr");
        valentinaOctjabr.setPhone("+77777777770");
        valentinaOctjabr.setPosition(Position.MASTER);
        valentinaOctjabr.setSubjects(new ArrayList<Subject>() {
            {
                add(getSubjects().get(MARKSIZM));
                add(getSubjects().get(LENINIZM));
            }
        });

        return valentinaOctjabr;
    }

    public static Student getJordanniJovanovich() {
        Student jordanniJovanovich = new Student();
        jordanniJovanovich.setAge(37);
        jordanniJovanovich.setName("Jordanni");
        jordanniJovanovich.setSurname("Jovanovich");
        jordanniJovanovich.setPhone("+78888888888");

        return jordanniJovanovich;
    }

    public static Student getJustinBieber() {
        Student justinBieber = new Student();
        justinBieber.setAge(25);
        justinBieber.setName("Justin");
        justinBieber.setSurname("Bieber");
        justinBieber.setPhone("+78888883888");

        return justinBieber;
    }

    public static Student getLinusTorvalds() {
        Student linusTorvalds = new Student();
        linusTorvalds.setAge(50);
        linusTorvalds.setName("Linus");
        linusTorvalds.setSurname("Torvalds");
        linusTorvalds.setPhone("+78888881888");

        return linusTorvalds;
    }

    public static Student getNikolaTesla() {
        Student nikolaTesla = new Student();
        nikolaTesla.setAge(163);
        nikolaTesla.setName("Nikola");
        nikolaTesla.setSurname("Tesla");
        nikolaTesla.setPhone("+78828881888");

        return nikolaTesla;
    }

    public static Group getDummiesGroup() {
        Group group = new Group();
        group.setId(1);
        group.setName("Dummies");
        return group;
    }

    public static Group getGeniusesGroup() {
        Group group = new Group();
        group.setId(2);
        group.setName("Geniuses");
        return group;
    }

    public static Group getEmptyGroup() {
        Group group = new Group();

        return group;
    }

    public static List<Lection> getLections() throws ParseException {
        List<Lection> result = new ArrayList<>();
        result.add(new Lection() {
            {
                setNumber(1);
                setFrom(FMT_TIME.parse("09:00"));
                setTo(FMT_TIME.parse("10:20"));
            }
        });
        result.add(new Lection() {
            {
                setNumber(2);
                setFrom(FMT_TIME.parse("10:30"));
                setTo(FMT_TIME.parse("11:50"));
            }
        });
        result.add(new Lection() {
            {
                setNumber(3);
                setFrom(FMT_TIME.parse("12:10"));
                setTo(FMT_TIME.parse("13:30"));
            }
        });
        result.add(new Lection() {
            {
                setNumber(4);
                setFrom(FMT_TIME.parse("13:40"));
                setTo(FMT_TIME.parse("15:00"));
            }
        });
        result.add(new Lection() {
            {
                setNumber(5);
                setFrom(FMT_TIME.parse("15:10"));
                setTo(FMT_TIME.parse("16:30"));
            }
        });
        result.add(new Lection() {
            {
                setNumber(6);
                setFrom(FMT_TIME.parse("16:40"));
                setTo(FMT_TIME.parse("18:00"));
            }
        });
        result.add(new Lection() {
            {
                setNumber(7);
                setFrom(FMT_TIME.parse("18:10"));
                setTo(FMT_TIME.parse("19:30"));
            }
        });
        result.add(new Lection() {
            {
                setNumber(8);
                setFrom(FMT_TIME.parse("19:40"));
                setTo(FMT_TIME.parse("21:00"));
            }
        });
        return result;
    }

    public static Map<Integer, Room> getRooms() {
        Map<Integer, Room> result = new HashMap<>();
        Room room = new Room();
        room.setNumber(101);
        room.setSeats(20);
        result.put(101, room);
        room = new Room();
        room.setNumber(102);
        room.setSeats(20);
        result.put(102, room);
        room = new Room();
        room.setNumber(103);
        room.setSeats(20);
        result.put(103, room);
        room = new Room();
        room.setNumber(201);
        room.setSeats(20);
        result.put(201, room);
        room = new Room();
        room.setNumber(202);
        room.setSeats(20);
        result.put(202, room);
        room = new Room();
        room.setNumber(203);
        room.setSeats(20);
        result.put(203, room);
        room = new Room();
        room.setNumber(301);
        room.setSeats(20);
        result.put(301, room);
        room = new Room();
        room.setNumber(302);
        room.setSeats(20);
        result.put(302, room);
        room = new Room();
        room.setNumber(303);
        room.setSeats(20);
        result.put(303, room);
        return result;
    }

    public static Map<String, Subject> getSubjects() {
        Map<String, Subject> result = new HashMap<>();
        Subject subject = new Subject();
        subject.setName(COMPUTER_SCIENCE);
        result.put(COMPUTER_SCIENCE, subject);
        subject = new Subject();
        subject.setName(MATRIX_HACKING);
        result.put(MATRIX_HACKING, subject);
        subject = new Subject();
        subject.setName(MARKSIZM);
        result.put(MARKSIZM, subject);
        subject = new Subject();
        subject.setName(LENINIZM);
        result.put(LENINIZM, subject);
        return result;
    }

    public static Department getEmptyDepartment() {
        Department department = new Department();

        return department;
    }

    public static Department getDepartment_1() {
        Department department = new Department();
        department.addGroup(getDummiesGroup());
        department.addGroup(getGeniusesGroup());
        department.addTeacher(getJohnSmith());
        department.addTeacher(getValentinaOctjabr());
        department.addSubject(getSubjects().get(COMPUTER_SCIENCE));
        department.addSubject(getSubjects().get(MATRIX_HACKING));
        department.addSubject(getSubjects().get(MARKSIZM));
        department.addSubject(getSubjects().get(LENINIZM));

        return department;
    }

    public static Department getDepartment_2() {
        Department department = new Department();
        department.addGroup(getDummiesGroup());
        department.addTeacher(getJohnSmith());
        department.addSubject(getSubjects().get(COMPUTER_SCIENCE));
        department.addSubject(getSubjects().get(MATRIX_HACKING));

        return department;
    }

    public static Faculty getEmptyFaculty() {
        Faculty faculty = new Faculty();

        return faculty;
    }

    public static Faculty getFaculty_1() {
        Faculty faculty = new Faculty();
        faculty.addDepartment(getDepartment_1());

        return faculty;
    }

    public static Faculty getFaculty_2() {
        Faculty faculty = new Faculty();

        return faculty;
    }

    public static ScheduleItem getScheduleItem_1() {
        ScheduleItem scheduleItem = new ScheduleItem();
        scheduleItem.addGroup(getDummiesGroup());
        scheduleItem.addGroup(getGeniusesGroup());
        return scheduleItem;
    }

    public static ScheduleItem getScheduleItem_2() {
        ScheduleItem scheduleItem = new ScheduleItem();
        scheduleItem.addGroup(getDummiesGroup());
        return scheduleItem;
    }

    public static ScheduleItem getEmptyScheduleItem() {
        ScheduleItem scheduleItem = new ScheduleItem();
        return scheduleItem;
    }

    public static Schedule getSchedule_1() {
        Schedule result = new Schedule();
        result.add(getScheduleItem_1());
        result.add(getScheduleItem_2());
        return result;
    }

    public static Schedule getSchedule_2() {
        Schedule result = new Schedule();
        result.add(getScheduleItem_1());
        return result;
    }

    public static Schedule getEmptySchedule() {
        Schedule result = new Schedule();
        return result;
    }

    public static Teacher getTeacher_1() {
        Teacher result = new Teacher();
        result.addSubject(getSubjects().get(COMPUTER_SCIENCE));
        result.addSubject(getSubjects().get(MARKSIZM));

        return result;
    }

    public static Teacher getTeacher_2() {
        Teacher result = new Teacher();
        result.addSubject(getSubjects().get(MATRIX_HACKING));

        return result;
    }

    public static Teacher getEmptyTeacher() {
        Teacher result = new Teacher();

        return result;
    }

    public static University getUniversity_1() {
        University result = new University();
        result.addFaculty(getFaculty_1());
        result.addFaculty(getFaculty_2());
        result.addRoom(getRooms().get(101));
        result.addRoom(getRooms().get(102));
        result.setSchedule(getSchedule_1());

        return result;
    }

    public static University getEmptyUniversity() {
        University result = new University();
        return result;
    }

    public static University getUniversity_2() {
        University result = new University();
        result.addFaculty(getFaculty_1());
        result.addRoom(getRooms().get(201));
        result.addRoom(getRooms().get(202));
        result.setSchedule(getSchedule_2());

        return result;
    }

    public static List<Group> getTestGroups() {
        return new ArrayList<Group>() {
            {
                add(dummies());
                add(geniuses());
            }
        };
    }

    public static List<Group> getTestGroupsWithId() {
        return new ArrayList<Group>() {
            {
                add(dummiesWithId());
                add(geniusesWithId());
            }
        };
    }

    public static List<Student> getTestStudents() {
        return new ArrayList<Student>() {
            {
                add(mark());
                add(michael());
                add(lorenzo());
            }
        };
    }

    public static List<Student> getTestStudentsWithId() {
        return new ArrayList<Student>() {
            {
                add(markWithId());
                add(michaelWithId());
                add(lorenzoWithId());
            }
        };
    }

    public static List<GroupDTO> getTestGroupDtos() {
        return new ArrayList<GroupDTO>() {
            {
                add(geniusesDto());
                add(dummiesDto());
            }
        };
    }

    public static List<StudentDTO> getTestStudentDtos() {
        return new ArrayList<StudentDTO>() {
            {
                add(markDto());
                add(michaelDto());
                add(lorenzoDto());
            }
        };
    }

    public static Group dummies() {
        return new Group(0, "Dummies", null);
    }

    public static Group dummiesWithId() {
        Group group = dummies();
        group.setId(1);
        return group;
    }

    public static Group geniuses() {
        return new Group(0, "Geniuses", null);
    }

    public static Group geniusesWithId() {
        Group group = geniuses();
        group.setId(2);
        return group;
    }

    public static Group superHeroes() {
        return new Group(0, "Super Heroes", null);
    }

    public static Group superHeroesWithId() {
        Group group = superHeroes();
        group.setId(3);
        return group;
    }

    public static Group newGroup() {
        return new Group(0, "Future soldiers", null);
    }

    public static Group newGroupWithId() {
        Group group = newGroup();
        group.setId(4);
        return group;
    }

    public static GroupDTO dummiesDto() {
        return new GroupDTO(1, "Dummies", false);
    }

    public static GroupDTO geniusesDto() {
        return new GroupDTO(2, "Geniuses", false);
    }

    public static GroupDTO superHeroesDto() {
        return new GroupDTO(3, "Super Heroes", false);
    }

    public static GroupDTO newGroupDto() {
        return new GroupDTO(0, "Future soldiers", false);
    }

    public static GroupDTO newGroupDtoWithId() {
        GroupDTO groupDto = newGroupDto();
        groupDto.setId(4);
        groupDto.setName("JustAnotherName");
        return groupDto;
    }

    public static Student mark() {
        return new Student(0, "Mark", "Dacascos", 55, "+11111111111", geniuses());
    }

    public static Student markWithId() {
        Student student = mark();
        student.setId(1);
        student.setGroup(geniusesWithId());
        return student;
    }

    public static Student michael() {
        return new Student(0, "Michael", "Dudikoff", 64, "+22222222222", dummies());
    }

    public static Student michaelWithId() {
        Student student = michael();
        student.setId(2);
        student.setGroup(dummiesWithId());
        return student;
    }

    public static Student lorenzo() {
        return new Student(0, "Lorenzo", "Lamas", 64, "+33333333333", dummies());
    }

    public static Student lorenzoWithId() {
        Student student = lorenzo();
        student.setId(3);
        student.setGroup(dummiesWithId());
        return student;
    }

    public static Student thor() {
        return new Student(0, "Thor", "The Thunderer", 10000, "+44444444444", superHeroes());
    }

    public static Student thorWithId() {
        Student student = thor();
        student.setId(4);
        student.setGroup(superHeroesWithId());
        return student;
    }

    public static Student hulk() {
        return new Student(0, "Hulk", "The Crusher", 3, "+55555555555", superHeroes());
    }

    public static Student hulkWithId() {
        Student student = hulk();
        student.setId(5);
        student.setGroup(superHeroesWithId());
        return student;
    }

    public static Student newStudent() {
        Student student = new Student(0, "Rokkie", "The Meat", 21, "+66666666666", null);
        student.setGroup(newGroupWithId());
        return student;
    }

    public static Student newStudentWithId() {
        Student student = newStudent();
        student.setId(6);
        student.setGroup(newGroupWithId());
        return student;
    }

    public static StudentDTO markDto() {
        return new StudentDTO(1, "Mark", "Dacascos", "55", "+11111111111", 2, geniuses().getName(), false);
    }

    public static StudentDTO michaelDto() {
        return new StudentDTO(2, "Michael", "Dudikoff", "64", "+22222222222", 1, dummies().getName(), false);
    }

    public static StudentDTO lorenzoDto() {
        return new StudentDTO(3, "Lorenzo", "Lamas", "64", "+33333333333", 1, dummies().getName(), false);
    }

    public static StudentDTO thorDto() {
        return new StudentDTO(4, "Thor", "The Thunderer", "10000", "+44444444444", 3, superHeroes().getName(), false);
    }

    public static StudentDTO hulkDto() {
        return new StudentDTO(5, "Hulk", "The Crusher", "3", "+55555555555", 3, superHeroes().getName(), false);
    }

    public static StudentDTO newStudentDto() {
        return new StudentDTO(0, "Rokkie", "The Meat", "21", "+66666666666", newGroupWithId().getId(),
            newGroupWithId().getName(), false);
    }

    public static StudentDTO newStudentDtoWithId() {
        return new StudentDTO(6, "Rokkie", "The Meat", "21", "+66666666666", newGroupWithId().getId(),
            newGroupWithId().getName(), false);
    }
}
