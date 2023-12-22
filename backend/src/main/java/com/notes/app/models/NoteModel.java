package com.notes.app.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="note")
@NoArgsConstructor
public class NoteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long idNote;

    @Column(name = "title", nullable = false, length = 20)
    @Getter @Setter
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "LONGTEXT")
    @Getter @Setter
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "notesCategories",
            joinColumns = @JoinColumn(name = "id_Note", referencedColumnName = "idNote"),
            inverseJoinColumns = @JoinColumn(name = "id_Category", referencedColumnName = "idCategory")
    )
    private List<CategoryModel> categories;

    @Column(name = "isArchived", nullable = false)
    @Getter @Setter
    private boolean isArchived = false;

    @Column(name = "createdAt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Getter @Setter
    @JsonFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();;

    // Categories
    public List<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryModel> categories) {
        this.categories = categories;
    }

    public void addCategory(CategoryModel category) {
        if (this.categories == null) {
            this.categories = new ArrayList<>();
        }

        this.categories.add(category);
    }

    public void removeCategory(CategoryModel category) {
        if (this.categories != null) {
            this.categories.remove(category);
        }
    }

}


