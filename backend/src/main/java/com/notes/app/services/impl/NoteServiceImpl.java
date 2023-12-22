package com.notes.app.services.impl;

import com.notes.app.models.NoteModel;
import com.notes.app.repositories.INoteRepository;
import com.notes.app.services.INoteService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoteServiceImpl implements INoteService {

    private final INoteRepository noteRepository;

    public NoteServiceImpl(INoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<NoteModel> getAllNotes() {
        return noteRepository.findAll();
    }

    public Optional<NoteModel> getNoteById(Long noteId) {
        return noteRepository.findById(noteId);
    }

    @Override
    public NoteModel createNote(NoteModel note) {
        return noteRepository.save(note);
    }

    @Override
    public NoteModel updateNote(NoteModel note) {
        return noteRepository.save(note);
    }

    @Override
    public void deleteNote(Long idNote) {
        noteRepository.deleteById((idNote));
    }

    @Override
    public Optional<List<NoteModel>> findByCategoriesName(String categories) {
        return noteRepository.findByCategories_NameCategory(categories);
    }

    @Override
    public Optional<List<NoteModel>> findByArchivedNote(boolean isArchived) {
        return noteRepository.findByIsArchived(isArchived);
    }

    @Override
    public Optional<List<NoteModel>> findByCategoriesNameAndArchived(String categories, boolean isArchived) {
        return noteRepository.findByCategories_NameCategoryAndIsArchived(categories, isArchived);
    }


}
