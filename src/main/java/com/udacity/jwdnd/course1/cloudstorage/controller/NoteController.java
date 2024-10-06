package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("note")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(
            Authentication authentication, @ModelAttribute("newFile") FileForm newFile, @ModelAttribute("newNote") NoteForm newNote,
            @ModelAttribute("newCredential") CredentialForm newCredential, Model model) {
        populateModelWithNotes(authentication, model);
        return "home";
    }

    private Integer getUserId(Authentication authentication) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        return user.getUserId();
    }

    @PostMapping("add-note")
    public String newNote(
            Authentication authentication, @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newNote") NoteForm newNote, @ModelAttribute("newCredential") CredentialForm newCredential,
            Model model) {
        String userName = authentication.getName();
        String newTitle = newNote.getTitle();
        String noteIdStr = newNote.getNoteId();
        String newDescription = newNote.getDescription();

        if (noteIdStr.isEmpty()) {
            noteService.addNote(newTitle, newDescription, userName);
        } else {
            Note existingNote = getNoteById(noteIdStr);
            noteService.updateNote(existingNote.getNoteId(), newTitle, newDescription);
        }

        populateModelWithNotes(authentication, model);
        model.addAttribute("result", "success");

        return "result";
    }

    @GetMapping(value = "/get-note/{noteId}")
    public Note getNoteById(@PathVariable String noteIdStr) {
        return noteService.getNote(Integer.parseInt(noteIdStr));
    }

    @GetMapping(value = "/delete-note/{noteId}")
    public String deleteNote(
            Authentication authentication, @PathVariable Integer noteId, @ModelAttribute("newNote") NoteForm newNote,
            @ModelAttribute("newFile") FileForm newFile, @ModelAttribute("newCredential") CredentialForm newCredential,
            Model model) {
        noteService.deleteNote(noteId);
        populateModelWithNotes(authentication, model);
        model.addAttribute("result", "success");

        return "result";
    }

    // New method to populate model with notes
    private void populateModelWithNotes(Authentication authentication, Model model) {
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", noteService.getNoteListings(userId));
    }
}
