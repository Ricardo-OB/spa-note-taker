package com.notes.app.services;

import com.notes.app.models.NoteModel;

import java.util.List;
import java.util.Optional;

public interface INoteService {

    List<NoteModel> getAllNotes();
    Optional<NoteModel> getNoteById(Long noteId);
    NoteModel createNote(NoteModel note);
    NoteModel updateNote(NoteModel note);
    void deleteNote(Long idNote);
    Optional<List<NoteModel>> findByCategoriesName(String categories);
    Optional<List<NoteModel>> findByArchivedNote(boolean archived);
    Optional<List<NoteModel>> findByCategoriesNameAndArchived(String categories, boolean archived);

}
