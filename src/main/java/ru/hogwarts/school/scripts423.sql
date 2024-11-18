SELECT
    student.name AS StudentName,
    student.age AS StudentAge,
    faculty.name AS FacultyName
FROM
    Student
JOIN
    faculty ON student.faculty_id = faculty.id;


SELECT
    student.name AS StudentName,
    student.age AS StudentAge,
    faculty.name AS FacultyName
FROM
    student
JOIN
    faculty ON student.faculty_id = faculty.id
JOIN
    avatar ON student.id = avatar.student_id;
