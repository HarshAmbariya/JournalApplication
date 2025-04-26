package net.ambariya.journalApp.controllers;

import net.ambariya.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long,JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }
    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry journalEntry){
        journalEntries.put(journalEntry.getId(), journalEntry);
        return true;
    }
    @GetMapping("/id/{myid}")
    public JournalEntry getJournalEntryById(@PathVariable Long myid){
        return journalEntries.get(myid);
    }
    @DeleteMapping("/id/{myid}")
    public JournalEntry deleteJournalEntryById(@PathVariable Long myid){
        return journalEntries.remove(myid);
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateJournalEntry(@PathVariable Long id, @RequestBody JournalEntry journalEntry){
        return journalEntries.put(id, journalEntry);
    }

}
