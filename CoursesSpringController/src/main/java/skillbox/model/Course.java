package skillbox.model;


import javax.persistence.*;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_course")
    private int idCourse;
    @Column(name = "name_course")
    private String nameCourse;
    @Column(name = "price_course")
    private Float priceCourse;
    @Column(name = "length_coursems")
    private Long lengthCourseMS;


    public Course(String nameCourse, Float priceCourse, Long lengthCourseMS) {
        this.nameCourse = nameCourse;
        this.priceCourse = priceCourse;
        this.lengthCourseMS = lengthCourseMS;
    }

    public Course() {

    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public Float getPriceCourse() {
        return priceCourse;
    }

    public void setPriceCourse(Float priceCourse) {
        this.priceCourse = priceCourse;
    }

    public Long getLengthCourseMS() {
        return lengthCourseMS;
    }

    public void setLengthCourseMS(Long lengthCourseMS) {
        this.lengthCourseMS = lengthCourseMS;
    }

    public int getIdCourse() {
        return idCourse;
    }


    @Override
    public String toString() {
        return "Курс - " + getNameCourse() + ", длительностью - " + getLengthCourseMS() + ", стоит - " + getPriceCourse();
    }

}
