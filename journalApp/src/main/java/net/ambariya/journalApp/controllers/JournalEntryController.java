package net.ambariya.journalApp.controllers;

import net.ambariya.journalApp.entity.JournalEntry;
import net.ambariya.journalApp.entity.User;
import net.ambariya.journalApp.service.JournalEntryService;
import net.ambariya.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService ;

    @GetMapping("{username}")
    public ResponseEntity<?> getAllJournalEntriesByUser(@PathVariable String username){
        User user = userService.findByUserName(username);
        List <JournalEntry> all =  user.getJournalEntries();
        if(!all.isEmpty()){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String username){

        try{
            journalEntryService.saveEntry(myEntry,username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
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
    @DeleteMapping("/id/{username}/{myid}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myid,@PathVariable String username){
        journalEntryService.deleteJournalEntryById(myid,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{username}/{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId id,
                                                @RequestBody JournalEntry newEntry,
                                                @PathVariable String username){
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
