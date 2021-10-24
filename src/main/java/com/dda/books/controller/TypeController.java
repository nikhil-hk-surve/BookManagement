package com.dda.books.controller;

import com.dda.books.model.Type;
import com.dda.books.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/type")
public class TypeController {

    @Autowired
    TypeService typeService;

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getType(@PathVariable Long id)
    {
        Optional<Type> optionaltype= typeService.getType(id);
        return optionaltype.isPresent()?ResponseEntity.of(optionaltype):ResponseEntity.ok("No type with this ID Available");
    }

    @GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllTypes()
    {
        List<Type> types=typeService.getAllTypes();
        return types.size()>0?ResponseEntity.ok(types):ResponseEntity.ok("No types Available");
    }

    //@RequestMapping(value="/save", method = RequestMethod.POST)
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Type save(@RequestBody Type type)
    {
        return typeService.save(type);

    }

    @PostMapping(value = "/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Long id)
    {
        typeService.delete(id);

    }

    @PostMapping(value = "/deleteAll", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteAll()
    {
        typeService.deleteAll();

    }

}
