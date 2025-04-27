package net.ambariya.journalApp.service;

import net.ambariya.journalApp.entity.JournalEntry;
import net.ambariya.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }
    public List<JournalEntry> getAllJournalEntry(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> getJournalEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    public void deleteJournalEntryById(ObjectId id){
        journalEntryRepository.deleteById(id);
    }

}
