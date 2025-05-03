package net.ambariya.journalApp.service;

import net.ambariya.journalApp.entity.JournalEntry;
import net.ambariya.journalApp.entity.User;
import net.ambariya.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry, String username) {
        User user = userService.findByUserName(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveEntry(user);
    }
    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }
    public List<JournalEntry> getAllJournalEntry(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> getJournalEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    public void deleteJournalEntryById(ObjectId id, String username){
        User user = userService.findByUserName(username);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);

    }

}
