package org.example.introspringboot.api;

import org.example.introspringboot.api.dto.MessageResponse;
import org.example.introspringboot.api.dto.UrbanMythRequest;
import org.example.introspringboot.service.UrbanMythService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/urbanmyths")
public class UrbanMythRestController {

    @Autowired
    UrbanMythService urbanMythService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('PERMISSION_GET_MYTHS')")
    public ResponseEntity<?> getUrbanMyths(){
        var myths = urbanMythService.getAllUrbanMyths();
        return ResponseEntity.status(200).body(myths);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('PERMISSION_CREATE_MYTH')")
    public ResponseEntity<?> createUrbanMyth(@RequestBody UrbanMythRequest urbanMythDTO){
        urbanMythService.createUrbanMyth(urbanMythDTO);
        var response = new MessageResponse("The myth was created successfully");
        return ResponseEntity.status(200).body(response);
    }
}
