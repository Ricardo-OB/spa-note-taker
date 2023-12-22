package com.notes.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.notes.app.models.CategoryModel;

public interface ICategoryRepository extends JpaRepository<CategoryModel, Long> {

}
