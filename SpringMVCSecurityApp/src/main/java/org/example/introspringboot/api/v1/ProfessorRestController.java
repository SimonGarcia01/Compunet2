package org.example.introspringboot.api.v1;

import org.example.introspringboot.api.v1.dto.MessageResponse;
import org.example.introspringboot.api.v1.dto.ProfessorDTO;
import org.example.introspringboot.entity.Professor;
import org.example.introspringboot.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/professors")
public class ProfessorRestController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ROLE_PROFESSOR')")
    public ResponseEntity<?>  getProfessors(){

        var professors = professorService.findAll();

        return ResponseEntity.status(200).body(professors);
    }

    //Changed the post mapping to use professor DTOs
    @PostMapping("/")
    public ResponseEntity<?> addProfessor(@RequestBody ProfessorDTO professorDTO){
        var result = professorService.save(professorDTO);
        //Now we use the MessageResponse to return the message
        //var response = Map.of("message", "Professor was saved successfully");
        var response = new MessageResponse("Professor was saved successfully");
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
