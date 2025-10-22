# Practice: REST Endpoints

Let’s put into practice what we’ve learned.  
Remember that the prefix you use to name your **endpoints** should follow **RESTful semantics**.  
Ask yourself what the **main resource** being returned is (think of the subject of the sentence).

---

## 🧩 Endpoints to Implement

### 1. Get all courses with their respective professor
- **Description:** Returns a list of all courses along with their assigned professor.
- **Pagination:** Define how many records per page.
- **Method:** `GET`
- **Suggested route:** `/api/courses/`

---

### 2. Get a course by ID with its professor and list of students
- **Description:** Returns detailed information about a specific course, including its professor and enrolled students.
- **Method:** `GET`
- **Suggested route:** `/api/courses/{id}/`

---

### 3. Search for courses by name
- **Description:** Searches for courses whose names partially match the given value.  
  *Does not include professor or student information.*
- **Pagination:** Define how many records per page.
- **Method:** `GET`
- **Suggested route:** `/api/courses/search/?name={value}`

---

### 4. Get all students enrolled in a specific course
- **Description:** Lists all students enrolled in a given course.
- **Method:** `GET`
- **Suggested route:** `/api/courses/{id}/students/`

---

### 5. Get all courses a student is enrolled in
- **Description:** Returns all courses in which a student is enrolled, identified by their student code.
- **Method:** `GET`
- **Suggested route:** `/api/students/{code}/courses/`

---

### 6. Search for students by academic program
- **Description:** Returns a list of students filtered by academic program.
- **Order:** Sorted by student code.
- **Pagination:** Define how many records per page.
- **Method:** `GET`
- **Suggested route:** `/api/students/?program={name}`

---

### 7. List all courses with the number of enrolled students
- **Description:** Displays each course along with the total number of students enrolled.
- **Method:** `GET`
- **Suggested route:** `/api/courses/enrollments/`

---

### 8. Create a new course and assign an existing professor
- **Description:** Allows creating a new course and linking it to an existing professor.
- **Method:** `POST`
- **Suggested route:** `/api/courses/`

---

### 9. Register a new student
- **Description:** Creates a new student record in the system.
- **Method:** `POST`
- **Suggested route:** `/api/students/`

---

### 10. Enroll a student in a course
- **Description:** Registers a student’s enrollment in a specific course.
- **Method:** `POST`
- **Suggested route:** `/api/enrollments/`

---

### 11. Update a student’s name, code, or academic program
- **Description:** Allows updating the basic information of a student.
- **Method:** `PUT` or `PATCH`
- **Suggested route:** `/api/students/{id}/`

---

### 12. Delete a specific enrollment by ID
- **Description:** Deletes an existing enrollment using its unique identifier.
- **Method:** `DELETE`
- **Suggested route:** `/api/enrollments/{id}/`

---
