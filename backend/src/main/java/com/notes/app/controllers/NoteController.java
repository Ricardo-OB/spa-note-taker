package com.notes.app.controllers;

import com.notes.app.models.CategoryModel;
import com.notes.app.models.NoteModel;
import com.notes.app.repositories.ICategoryRepository;
import com.notes.app.repositories.INoteRepository;
import com.notes.app.services.INoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/api/notes-spa")
public class NoteController {
    private final INoteService noteService;
    private final INoteRepository noteRepository;
    private final ICategoryRepository categoryRepository;

    public NoteController(INoteService noteService, INoteRepository noteRepository, ICategoryRepository categoryRepository) {
        this.noteService = noteService;
        this.noteRepository = noteRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public ResponseEntity<Optional<List<NoteModel>>> getAllNotes(@RequestParam(required = false) String status) {
        Optional<List<NoteModel>> notes;
        if (status != null) {
            if (status.equals("active")) {
                notes = noteService.findByArchivedNote(false);
            } else if (status.equals("archived")) {
                notes = noteService.findByArchivedNote(true);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            notes = Optional.ofNullable(noteService.getAllNotes());
        }
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{id}/")
    public ResponseEntity<NoteModel> getNoteByID(@PathVariable Long id) {
        Optional<NoteModel> note = noteService.getNoteById(id);
        if (note.isPresent()) {
            return new ResponseEntity<>(note.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{category}")
    public ResponseEntity<Optional<List<NoteModel>>> getNotesByCategory(@PathVariable String category, @RequestParam(required = false) String status) {
        Optional<List<NoteModel>> notes;
        if (status != null) {
            if (status.equals("active")) {
                notes = noteService.findByCategoriesNameAndArchived(category, false);
            } else if (status.equals("archived")) {
                notes = noteService.findByCategoriesNameAndArchived(category, true);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            notes = noteService.findByCategoriesName(category);
        }
        return ResponseEntity.ok(notes);
    }

    @PostMapping
    public ResponseEntity<NoteModel> createNote(@RequestBody NoteModel note) {
        List<CategoryModel> existingCategories = note.getCategories();
        List<CategoryModel> savedCategories = new ArrayList<>();
        for (CategoryModel category : existingCategories) {
            if (category.getIdCategory() == null) {
                savedCategories.add(categoryRepository.save(category));
            } else {
                savedCategories.add(category);
            }
        }
        note.setCategories(savedCategories);
        NoteModel savedNote = noteService.createNote(note);
        return ResponseEntity.ok(savedNote);
    }

    @PutMapping
    public ResponseEntity<NoteModel> updateNote(@RequestBody NoteModel updatedNote) {
        List<CategoryModel> existingCategories = updatedNote.getCategories();
        List<CategoryModel> savedCategories = new ArrayList<>();

        for (CategoryModel category : existingCategories) {
            if (category.getIdCategory() == null) {
                savedCategories.add(categoryRepository.save(category));
            } else {
                savedCategories.add(category);
            }
        }

        updatedNote.setCategories(savedCategories);
        noteService.updateNote(updatedNote);
        NoteModel savedNote = noteService.updateNote(updatedNote);
        return ResponseEntity.ok(savedNote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        if (!noteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/archive/{id}")
    public ResponseEntity<NoteModel> archiveNote(@PathVariable Long id) {
        Optional<NoteModel> note = noteService.getNoteById(id);
        if (note.isPresent()) {
            note.get().setArchived(!note.get().isArchived());
            noteService.updateNote(note.get());
            return ResponseEntity.ok(note.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}


