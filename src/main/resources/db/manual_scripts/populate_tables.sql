with rows as (
  insert into groups
    (id, name, department_id, deleted)
  values
    (default, 'Beatles', null, FALSE)
  returning id
)
insert into students
  (id, name, surname, age, phone, group_id, deleted)
values 
  (default, 'John', 'Lennon', 79, '111111', (select id from rows), FALSE),
  (default, 'Paul', 'McCartney', 77, '222222', (select id from rows), FALSE),
  (default, 'Ringo', 'Starr', 79, '333333', (select id from rows), FALSE),
  (default, 'George', 'Harrison', 76, '444444', (select id from rows), FALSE);

with rows as (
  insert into groups
    (id, name, department_id, deleted)
  values
    (default, 'Scorpions', null, FALSE)
  returning id
)
insert into students
  (id, name, surname, age, phone, group_id, deleted)
values 
  (default, 'Klaus', 'Meine', 71, '555555', (select id from rows), FALSE),
  (default, 'Rudolf', 'Schenker', 71, '666666', (select id from rows), FALSE),
  (default, 'Matthias', 'Jabs', 63, '777777', (select id from rows), FALSE), 
  (default, 'Pawel', 'Maciwoda', 52, '888888', (select id from rows), FALSE),
  (default, 'Mikkey', 'Dee', 55, '999999', (select id from rows), FALSE);

with rows as (
  insert into groups
    (id, name, department_id, deleted)
  values
    (default, 'The Rolling Stones', null, FALSE)
  returning id
)
insert into students
  (id, name, surname, age, phone, group_id, deleted)
values 
  (default, 'Mick', 'Jagger', 76, '111110', (select id from rows), FALSE),
  (default, 'Keith', 'Richards', 75, '222220', (select id from rows), FALSE),
  (default, 'Charlie', 'Watts', 78, '333330', (select id from rows), FALSE),
  (default, 'Ronnie', 'Wood', 72, '999999', (select id from rows), FALSE);
  
