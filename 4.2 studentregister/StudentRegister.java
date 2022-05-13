/**
 *
 * @author sophietotterstrom
 */

import java.util.*;

public class StudentRegister {
    
    private Map<String, Student> studentsMap = new HashMap<String, Student>();
    private Map<String, Course> coursesMap = new HashMap<String, Course>();
    private Map<String, ArrayList<Attainment>> attainmentsMap = new HashMap<String, ArrayList<Attainment>>();
    
    
    
    // constructor
    public StudentRegister() 
    {
    }

    public ArrayList<Student> getStudents() 
    {
        ArrayList<String> studentNames = new ArrayList<String>();
        for (Map.Entry<String, Student> pair : this.studentsMap.entrySet()) 
        {
            studentNames.add(pair.getValue().getName());
        }
        Collections.sort(studentNames);
        
        ArrayList<Student> sorted = new ArrayList<Student>();
        
        for (String name : studentNames) 
        { 
            // loop through map and get the student object with the name
            for (Map.Entry<String, Student> pair : this.studentsMap.entrySet()) 
            {
                if (pair.getValue().getName().equalsIgnoreCase(name))
                {
                    String tempStudentNum = pair.getValue().getStudentNumber();
                    Student studentObject = this.studentsMap.get(tempStudentNum);
                    sorted.add(studentObject);
                }
            }
        }
        return sorted;
    }
    
    public ArrayList<Course> getCourses() 
    {
        ArrayList<String> courseNames = new ArrayList<String>();
        for (Map.Entry<String, Course> pair : this.coursesMap.entrySet()) 
        {
            courseNames.add(pair.getValue().getName());
        }
        Collections.sort(courseNames);
        
        ArrayList<Course> sorted = new ArrayList<Course>();
        
        for (String name : courseNames) 
        { 
            // loop through map and get the student object with the name
            for (Map.Entry<String, Course> pair : this.coursesMap.entrySet()) 
            {
                if (pair.getValue().getName().equalsIgnoreCase(name))
                {
                    String tempCourseCode = pair.getValue().getCode();
                    Course courseObject = this.coursesMap.get(tempCourseCode);
                    sorted.add(courseObject);
                }
            }
        }
        return sorted;
        
        
    }
    
    public void addStudent(Student student) 
    {
        if (this.studentsMap.containsKey(student.getStudentNumber()))
        {
            // student is already in the map
        }
        else
        {
            this.studentsMap.put(student.getStudentNumber(), student);
        }
    }
    
    public void addCourse(Course course) 
    {
        if (this.coursesMap.containsKey(course.getCode()))
        {
            // already in the map
        }
        else
        {
            this.coursesMap.put(course.getCode(), course);
        }
    }
    
    public void addAttainment(Attainment att) 
    {
        if (this.attainmentsMap.containsKey(att.getStudentNumber()))
        {
            // already in the map
            this.attainmentsMap.get(att.getStudentNumber()).add(att);
        }
        else
        {
            ArrayList <Attainment> attList = new ArrayList<Attainment>();
            attList.add(att);
            this.attainmentsMap.put(att.getStudentNumber(), attList);
        }
    }
    
    public void printStudentAttainments(String studentNumber, String order) 
    {
        
        if (this.studentsMap.containsKey(studentNumber))
        {
            // “studentName (studentNumber):” oleva otsakerivi.
            String studentName = this.studentsMap.get(studentNumber).getName();
            System.out.println(studentName + " (" + studentNumber + "):");
            
            if (order.equalsIgnoreCase("by name"))
            {
                
                ArrayList<String> courseNames = new ArrayList<String>();
                for (Attainment att : this.attainmentsMap.get(studentNumber))
                {
                    String courseCode = att.getCourseCode();
                    String courseName = this.coursesMap.get(courseCode).getName();
                    courseNames.add(courseName);
                }
                Collections.sort(courseNames);
                
                // initialize the string
                String gradeStr ="";
                
                // loop through sorted array of the names of courses
                for (String courseName : courseNames)
                {
                    // find the course object with the same name from the map
                    for (Map.Entry<String, Course> pair : this.coursesMap.entrySet()) 
                    {
                        if (pair.getValue().getName().equalsIgnoreCase(courseName))
                        {
                            String tempCourseCode = pair.getValue().getCode();
                            // Course courseObject = this.coursesMap.get(tempCourseCode);
      
                            // find the grade
                            for (Attainment atta : this.attainmentsMap.get(studentNumber))
                            {
                                if (atta.getCourseCode().equalsIgnoreCase(tempCourseCode))
                                {
                                    gradeStr = String.valueOf(atta.getGrade());
                                }
                            }
                            
                            System.out.println("  " + pair.getValue().getCode() 
                                    + " " + courseName + ": " + gradeStr );
                        }
                    }
                }
            }
            else if (order.equalsIgnoreCase("by code")) 
            {
                ArrayList<String> courseCodes = new ArrayList<String>();
                for (Attainment att : this.attainmentsMap.get(studentNumber))
                {
                    String courseCode = att.getCourseCode();
                    courseCodes.add(courseCode);
                }
                Collections.sort(courseCodes);
                
                
                // initialize the string
                String gradeStr ="";
                
                // loop through sorted array of the names of courses
                for (String courseCode : courseCodes)
                {
                    // find the course object with the same name from the map
                    for (Map.Entry<String, Course> pair : this.coursesMap.entrySet()) 
                    {
                        if (pair.getValue().getCode().equalsIgnoreCase(courseCode))
                        {
                            String courseName = pair.getValue().getName();
                            // find the grade
                            for (Attainment atta : this.attainmentsMap.get(studentNumber))
                            {
                                if (atta.getCourseCode().equalsIgnoreCase(courseCode))
                                {
                                    gradeStr = String.valueOf(atta.getGrade());
                                }
                            }
                            
                            System.out.println("  " + pair.getValue().getCode() 
                                    + " " + courseName + ": " + gradeStr );
                        }
                    }
                }
            }
            else
            {
                for (Attainment att : this.attainmentsMap.get(studentNumber))
                {
                    String courseName = this.coursesMap.get(att.getCourseCode()).getName();
                    
                    System.out.println("  " + att.getCourseCode()
                                    + " " + courseName + ": " + String.valueOf(att.getGrade()));
                }
                
            }
        }
        else
        {
            System.out.println("Unknown student number: " + studentNumber);
        }
    }
    
    public void printStudentAttainments(String studentNumber) 
    {
        
        if (this.studentsMap.containsKey(studentNumber))
        {
        	 // “studentName (studentNumber):” oleva otsakerivi.
        	String studentName = this.studentsMap.get(studentNumber).getName();
        	System.out.println(studentName + " (" + studentNumber + "):");
        	
            for (Attainment att : this.attainmentsMap.get(studentNumber))
            {
                String courseName = this.coursesMap.get(att.getCourseCode()).getName();

                System.out.println("  " + att.getCourseCode()
                                + " " + courseName + ": " + String.valueOf(att.getGrade()));
            }
        }
        else
        {
            System.out.println("Unknown student number: " + studentNumber);
        }
    }
    
}
