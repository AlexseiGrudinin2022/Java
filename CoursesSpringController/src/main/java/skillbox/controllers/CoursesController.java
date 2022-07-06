package skillbox.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import skillbox.database.DataBase;
import skillbox.model.Course;

@RestController
@RequestMapping("/courses")
public class CoursesController {

    @Autowired
    private DataBase crud;


    private Course getCourseById(int id) {
        return crud.findById(id).orElse(null);
    }


    //показать все курсы
    @GetMapping()
    public ModelAndView index() {

        var coursesList = crud.findAll();
        return new ModelAndView("index")
                .addObject("courses", coursesList)
                .addObject("count", (long) coursesList.size());
    }

    /////////////////////////////////////////////////////////////
    //показать 1 курс
    @GetMapping("/{id}")
    public ModelAndView getOneCourse(@PathVariable(value = "id", required = false) int id) {
        return new ModelAndView("show").addObject("course", getCourseById(id));
    }

    /////////////////////////////////////////////////////////////
    //отправить на редактирование
    @GetMapping("edit/{id}")
    public ModelAndView editOneCourse(@PathVariable(value = "id", required = false) int id) {
        return new ModelAndView("edit").addObject("courseEdit", getCourseById(id));
    }

    @PostMapping("edit/{id}")
    public ModelAndView edit(@PathVariable int id,
                             @ModelAttribute Course courseEdit) {

        Course course = getCourseById(id);
        course.setNameCourse(courseEdit.getNameCourse());
        course.setPriceCourse(courseEdit.getPriceCourse());
        course.setLengthCourseMS(courseEdit.getLengthCourseMS());
        crud.save(course);

        return new ModelAndView("redirect:/courses");
    }

    /////////////////////////////////////////////////////////////
    //добавить новый курс
    @GetMapping("new")
    public ModelAndView newPerson(@ModelAttribute Course course) {
        return new ModelAndView("new");
    }

    @PostMapping()
    public ModelAndView add(@ModelAttribute Course course) {
        crud.save(course);
        return new ModelAndView("redirect:/courses");
    }


    //удалить 1 курс
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable int id) {
        crud.delete(getCourseById(id));
        return new ModelAndView("redirect:/courses");
    }

    //удалить все курсы
    @GetMapping("/delete-all")
    public ModelAndView delete() {
        crud.deleteAll();
        return new ModelAndView("redirect:/courses");
    }


}
