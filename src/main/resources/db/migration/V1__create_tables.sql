CREATE TABLE "subjects" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar,
  "department_id" int
);

CREATE TABLE "teachers" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar,
  "surname" varchar,
  "age" int,
  "phone" varchar,
  "position" varchar,
  "department_id" int
);

CREATE TABLE "teachers_subjects" (
  "teacher_id" int,
  "subject_id" int
);

CREATE TABLE "departments" (
  "id" SERIAL PRIMARY KEY,
  "faculty_id" int,
  "name" varchar
);

CREATE TABLE "groups" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar,
  "department_id" int
);

CREATE TABLE "students" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar,
  "surname" varchar,
  "age" int,
  "phone" varchar,
  "group_id" int
);

CREATE TABLE "faculties" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar
);

CREATE TABLE "rooms" (
  "id" SERIAL PRIMARY KEY,
  "number" int,
  "seats" int
);

CREATE TABLE "lections" (
  "id" SERIAL PRIMARY KEY,
  "number" int,
  "from" date,
  "to" date
);

CREATE TABLE "schedule_items_groups" (
  "schedule_item_id" int,
  "group_id" int
);

CREATE TABLE "schedule_items" (
  "id" SERIAL PRIMARY KEY,
  "lection_id" int,
  "room_id" int,
  "teacher_id" int,
  "subject_id" int,
  "date" date
);

ALTER TABLE "subjects" ADD FOREIGN KEY ("department_id") REFERENCES "departments" ("id");

ALTER TABLE "teachers" ADD FOREIGN KEY ("department_id") REFERENCES "departments" ("id");

ALTER TABLE "teachers_subjects" ADD FOREIGN KEY ("teacher_id") REFERENCES "teachers" ("id");

ALTER TABLE "teachers_subjects" ADD FOREIGN KEY ("subject_id") REFERENCES "subjects" ("id");

ALTER TABLE "departments" ADD FOREIGN KEY ("faculty_id") REFERENCES "faculties" ("id");

ALTER TABLE "groups" ADD FOREIGN KEY ("department_id") REFERENCES "departments" ("id");

ALTER TABLE "students" ADD FOREIGN KEY ("group_id") REFERENCES "groups" ("id");

ALTER TABLE "schedule_items_groups" ADD FOREIGN KEY ("schedule_item_id") REFERENCES "schedule_items" ("id");

ALTER TABLE "schedule_items_groups" ADD FOREIGN KEY ("group_id") REFERENCES "groups" ("id");

ALTER TABLE "schedule_items" ADD FOREIGN KEY ("lection_id") REFERENCES "lections" ("id");

ALTER TABLE "schedule_items" ADD FOREIGN KEY ("room_id") REFERENCES "rooms" ("id");

ALTER TABLE "schedule_items" ADD FOREIGN KEY ("teacher_id") REFERENCES "teachers" ("id");

ALTER TABLE "schedule_items" ADD FOREIGN KEY ("subject_id") REFERENCES "subjects" ("id");

