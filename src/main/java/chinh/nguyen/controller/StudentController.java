package chinh.nguyen.controller;

import chinh.nguyen.dto.response.ResponseMessage;
import chinh.nguyen.model.Student;
import chinh.nguyen.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private IStudentService studentService;
    //API SHOW LIST
    @GetMapping
    public ResponseEntity<?> showListStudent(){
        return new ResponseEntity<>(studentService.findAll(), HttpStatus.OK);
    }
    //API CREATE
    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student){
        studentService.save(student);
        return new ResponseEntity<>(new ResponseMessage("create_success"), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> detailStudent(@PathVariable Long id){
        if (!studentService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentService.findById(id).get(), HttpStatus.OK);
    }
    //API UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student){
        if (!studentService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Student student1 = studentService.findById(id).get();
        student.setId(student1.getId());
        studentService.save(student);
        return new ResponseEntity<>(new ResponseMessage("update success !"), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){
        if (!studentService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        studentService.deleteById(id);
        return new ResponseEntity<>("delete success !", HttpStatus.OK);
    }
    // API PAGE STUDENT
    @GetMapping("/page")
    public ResponseEntity<?> showPageStudent(@PageableDefault(size=3, sort="name", direction = Sort.Direction.ASC) Pageable pageable){
        return new ResponseEntity<>(studentService.findAll(pageable), HttpStatus.OK);
    }

    //API SEARCH CÁCH 1 --> Dùng @PathVariable
    @GetMapping("/search/{name}")
    public ResponseEntity<?> showListSearchContaining(@PathVariable String name){
        return new ResponseEntity<>(studentService.findByNameContaining(name), HttpStatus.OK);
    }
    // API SEARCH CÁCH 2 --> Dùng @Requestparam
    @GetMapping("/search")
    public ResponseEntity<?> showListSearchWithQuery(@RequestParam("name") String name){
        return new ResponseEntity<>(studentService.findByNameFull(name), HttpStatus.OK);
    }
}
