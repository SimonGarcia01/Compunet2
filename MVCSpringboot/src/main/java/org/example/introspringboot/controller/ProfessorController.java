package org.example.introspringboot.controller;

import org.example.introspringboot.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("professors")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

    @GetMapping("/{id}")
    public String getProfessorInfo(@PathVariable("id") Integer id, Model model){
        var professor = professorService.findById(id);
        professor.ifPresent(value -> model.addAttribute("professor", value));
        return "professor/professor-details";
    }
}
