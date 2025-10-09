package org.example.introspringboot.api.v1;

import org.example.introspringboot.entity.Professor;
import org.example.introspringboot.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/professors")
public class ProfessorRestController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/")
    public ResponseEntity<?>  getProfessors(){

        var professors = professorService.findAll();

        return ResponseEntity.status(200).body(professors);
    }

    @PostMapping("/")
    public ResponseEntity<?> addProfessor(@RequestBody Professor professor){
        var result = professorService.save(professor);
        var response = Map.of("message", "Professor was saved successfully");
        return ResponseEntity.status(200).body(response);
    }

    //Example api/v1/professors/2
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfessor(@PathVariable("id") Integer id){
        professorService.deleteById(id);
        var response = Map.of("message", "Professor was deleted successfully");
        return ResponseEntity.status(200).body(response);
    }

}
