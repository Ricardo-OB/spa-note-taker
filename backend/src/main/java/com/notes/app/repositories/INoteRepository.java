package com.notes.app.repositories;

import com.notes.app.models.NoteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface INoteRepository extends JpaRepository<NoteModel, Long> {

    Optional<List<NoteModel>> findByIsArchived(boolean isArchived);
    Optional<List<NoteModel>> findByCategories_NameCategoryAndIsArchived(String categoryName, boolean isArchived);

    Optional<List<NoteModel>> findByCategories_NameCategory(String categoryName);
}
