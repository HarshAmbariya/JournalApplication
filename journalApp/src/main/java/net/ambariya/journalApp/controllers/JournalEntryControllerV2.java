package net.ambariya.journalApp.controllers;

import net.ambariya.journalApp.entity.JournalEntry;
import net.ambariya.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List <JournalEntry> all =  journalEntryService.getAllJournalEntry();
        if(!all.isEmpty()){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry){
        try{
            journalEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(journalEntry);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myid){
        Optional<JournalEntry> journalEntry = journalEntryService.getJournalEntryById(myid);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/id/{myid}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myid){
        journalEntryService.deleteJournalEntryById(myid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry){
        JournalEntry old = journalEntryService.getJournalEntryById(id).orElse(null);
        if(old != null){
            old.setTitle(newEntry.getTitle() != null ? newEntry.getTitle() : old.getTitle());
            old.setDate(newEntry.getDate() != null ? newEntry.getDate() : old.getDate());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
