<template>
  <div class="container-init">
    <div class="div-button-create">
      <button @click="openCreateDialog()" class="button-new">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-lg" viewBox="0 0 16 16">
          <path fill-rule="evenodd" d="M8 2a.5.5 0 0 1 .5.5v5h5a.5.5 0 0 1 0 1h-5v5a.5.5 0 0 1-1 0v-5h-5a.5.5 0 0 1 0-1h5v-5A.5.5 0 0 1 8 2"/>
        </svg>
        New Note
      </button>
    </div>

    <div class="div-filter-categ">
      <div class="filter-text">
        <p>Filter by Category:</p>
      </div>
      <div class="filter-categ">
        <form @submit.prevent="filterByCategory" class="form-filter-1">
          <input v-model="filterCategory" type="text" id="filterCategory" required class="input-filter-categ">
          <button type="submit" class="margin-categ" id="buton-filter-categ">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
              <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
            </svg>
            Filter
          </button>
        </form>
        <button @click="clearFilter" class="margin-categ" id="buton-clear-categ">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-circle" viewBox="0 0 16 16">
            <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
            <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
          </svg>
          Clean Filter
        </button>
      </div>
    </div>

    <div class="div-filter-status">
      <div class="filter-text">
        <p>Filter by Status:</p>
      </div>
      <div class="filter-categ">
        <select v-model="selectedStatus" @change="getNotes">
          <option value="all">All Notes</option>
          <option value="active">Active</option>
          <option value="archived">Archived</option>
        </select>
      </div>
    </div>

    <div v-if="isCreateDialogVisible" class="cont-form-create">
      <form @submit.prevent="createNote" class="form-create">
        <h2>New Note</h2>
        <label for="title">Title:</label>
        <input v-model="newNote.title" type="text" required>
        <label for="description">Description:</label>
        <textarea v-model="newNote.description" required></textarea>
        <label for="categories">Categories (separated by comma):</label>
        <input v-model="newNote.categories" type="text" required placeholder="Categ1, Categ2, Categ3">
        <button type="submit" class="button-save">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-floppy" viewBox="0 0 16 16">
            <path d="M11 2H9v3h2z"/>
            <path d="M1.5 0h11.586a1.5 1.5 0 0 1 1.06.44l1.415 1.414A1.5 1.5 0 0 1 16 2.914V14.5a1.5 1.5 0 0 1-1.5 1.5h-13A1.5 1.5 0 0 1 0 14.5v-13A1.5 1.5 0 0 1 1.5 0M1 1.5v13a.5.5 0 0 0 .5.5H2v-4.5A1.5 1.5 0 0 1 3.5 9h9a1.5 1.5 0 0 1 1.5 1.5V15h.5a.5.5 0 0 0 .5-.5V2.914a.5.5 0 0 0-.146-.353l-1.415-1.415A.5.5 0 0 0 13.086 1H13v4.5A1.5 1.5 0 0 1 11.5 7h-7A1.5 1.5 0 0 1 3 5.5V1H1.5a.5.5 0 0 0-.5.5m3 4a.5.5 0 0 0 .5.5h7a.5.5 0 0 0 .5-.5V1H4zM3 15h10v-4.5a.5.5 0 0 0-.5-.5h-9a.5.5 0 0 0-.5.5z"/>
          </svg>
          Save
        </button>
      </form>
      <button @click="cancelCreate()" class="button-cancel-1">Cancel</button>
    </div>
  </div>

  <div class="container-sec">
    <ul>
      <li v-for="note in notes" :key="note.idNote" class="card-note">
        <div class="content-card-note">
          <h2>{{ note.title }}</h2>
          <p>{{ note.description }}</p>
          <ul>
            <li v-for="category in note.categories" :key="category.idCategory" class="badge-categ">
              {{ category.nameCategory }}
            </li>
          </ul>
          <p>{{ note.createdAt }}</p>
          <div class="buttons-card-note">
            <button @click="openEditDialog(note)" class="button-edit">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
                <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
              </svg>
            </button>
            <button @click="deleteNote(note.idNote)" class="button-delete">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"/>
                <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"/>
              </svg>
            </button>
            <button @click="archiveNote(note.idNote)" class="button-archived">{{ note.archived ? 'Unarchive' : 'Archive' }}</button>
          </div>
        </div>
      </li>
    </ul>

    <div v-if="isEditDialogVisible" class="cont-form-edit">
      <h2>Edit Note</h2>
      <label for="editTitle">Title:</label>
      <input v-model="editNote.title" type="text" id="editTitle" required>
      <label for="editDescription">Description:</label>
      <textarea v-model="editNote.description" id="editDescription" required></textarea>
      <label for="editCategories">Categories (separated by comma):</label>
      <input v-model="editNote.categoriesString" type="text" id="editCategories" required placeholder="Categ1, Categ2, Categ3">
      <button @click="saveEdit" class="button-save">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-floppy" viewBox="0 0 16 16">
          <path d="M11 2H9v3h2z"/>
          <path d="M1.5 0h11.586a1.5 1.5 0 0 1 1.06.44l1.415 1.414A1.5 1.5 0 0 1 16 2.914V14.5a1.5 1.5 0 0 1-1.5 1.5h-13A1.5 1.5 0 0 1 0 14.5v-13A1.5 1.5 0 0 1 1.5 0M1 1.5v13a.5.5 0 0 0 .5.5H2v-4.5A1.5 1.5 0 0 1 3.5 9h9a1.5 1.5 0 0 1 1.5 1.5V15h.5a.5.5 0 0 0 .5-.5V2.914a.5.5 0 0 0-.146-.353l-1.415-1.415A.5.5 0 0 0 13.086 1H13v4.5A1.5 1.5 0 0 1 11.5 7h-7A1.5 1.5 0 0 1 3 5.5V1H1.5a.5.5 0 0 0-.5.5m3 4a.5.5 0 0 0 .5.5h7a.5.5 0 0 0 .5-.5V1H4zM3 15h10v-4.5a.5.5 0 0 0-.5-.5h-9a.5.5 0 0 0-.5.5z"/>
        </svg>
        Save Edit
      </button>
      <button @click="cancelEdit" class="button-cancel-2">Cancel</button>
    </div>

  </div>
</template>

<script>
import { backendUrl } from '@/config.js';
import axios from 'axios';
import { ref } from 'vue';

export default {
  data() {
    return {
      notes: [],
      newNote: {
        title: '',
        description: '',
        categories: '',
      },
      editNote: {
        idNote: null,
        title: '',
        description: '',
        categories: [],
        categoriesString: '',
      },
      isEditDialogVisible: false,
      isCreateDialogVisible: false,
      filterCategory: '',
      originalNotes: [],
      selectedStatus: 'active',
    };
  },
  mounted() {
    this.getNotes();
  },
  methods: {
    async openCreateDialog() {
      try {
        this.isCreateDialogVisible = true;
      } catch (error) {
        console.error('Error fetching all notes:', error);
      }
    },

    cancelCreate() {
      this.isCreateDialogVisible = false;
      this.newNote = {
          title: '',
          description: '',
          categories: '',
        };
    },

    async getNotes() {
      try {
        let apiUrl = `${backendUrl}/api/notes-spa`;

        if (this.selectedStatus === 'active' || this.selectedStatus === 'archived') {
          apiUrl += `?status=${this.selectedStatus}`;
        }

        const response = await axios.get(apiUrl);
        this.notes = response.data;
        this.originalNotes = [...this.notes];

      } catch (error) {
        console.error('Error fetching all notes:', error);
      }
    },

    async createNote() {
      try {
        const categoriesArray = this.newNote.categories.split(',').map(category => ({ nameCategory: category.trim() }))
        const noteData = JSON.stringify({
          title: this.newNote.title,
          description: this.newNote.description,
          categories: categoriesArray,
        });
        
        const response = await axios.post(`${backendUrl}/api/notes-spa`,
                                          noteData,
                                          {headers: {'Content-Type': 'application/json', 'Accept': 'application/json'}}
                                          );

        this.notes.push(response.data);

        this.newNote = {
          title: '',
          description: '',
          categories: '',
        };
        this.isCreateDialogVisible = false;
      } catch (error) {
        console.error('Error creating note:', error);
      }
    },

    async openEditDialog(note) {
      const id = note.idNote;
      try {
        const response = await axios.get(`${backendUrl}/api/notes-spa/${id}/`);
        const noteReponse = response.data;

        this.editNote = {
          idNote: noteReponse.idNote,
          title: noteReponse.title,
          description: noteReponse.description,
          categories: noteReponse.categories,
          categoriesString: noteReponse.categories.map(category => category.nameCategory).join(','),
        };

        this.isEditDialogVisible = true;

      } catch (error) {
        console.error('Error fetching all notes:', error);
      }
    },

    async saveEdit() {
      try {
        this.editNote.categories = this.editNote.categoriesString.split(',').map(category => ({ nameCategory: category.trim() }));

        const noteData = JSON.stringify({
          idNote: this.editNote.idNote,
          title: this.editNote.title,
          description: this.editNote.description,
          categories: this.editNote.categories,
        });

        const response = await axios.put(`${backendUrl}/api/notes-spa`,
                                         noteData,
                                         {headers:{'Content-Type':'application/json','Accept':'application/json'}
                                        });

        const updatedNoteIndex = this.notes.findIndex(note => note.idNote === this.editNote.idNote);
        if (updatedNoteIndex !== -1) {
          this.notes[updatedNoteIndex] = response.data;
        }

        this.isEditDialogVisible = false;
      } catch (error) {
        console.error('Error updating note:', error);
      }
    },

    cancelEdit() {
      this.isEditDialogVisible = false;
    },

    async filterByCategory() {
      try {
        const response = await axios.get(`${backendUrl}/api/notes-spa/${this.filterCategory}`);
        this.notes = response.data;
      } catch (error) {
        console.error('Error fetching notes by category:', error);
      }
    },

    clearFilter() {
      this.notes = [...this.originalNotes];
      this.filterCategory = '';
    },
    
    async deleteNote(noteId) {
      try {
        await axios.delete(`${backendUrl}/api/notes-spa/${noteId}`);
        this.notes = this.notes.filter(note => note.idNote !== noteId);
      } catch (error) {
        console.error('Error deleting note:', error);
      }
    },

    async archiveNote(noteId) {
      try {
        const response = await axios.put(`${backendUrl}/api/notes-spa/archive/${noteId}`);

        const updatedNote = response.data;
        const noteIndex = this.notes.findIndex(note => note.idNote === noteId);

        if (noteIndex !== -1) {
          this.notes[noteIndex] = ref(updatedNote);
        }
        this.getNotes();
      } catch (error) {
        console.error('Error archiving/unarchiving note:', error);
      }
    }
  },
  watch: {
    selectedCategory() {
      this.getNotes();
    },
    selectedStatus() {
      if (this.selectedStatus === 'active') {
        this.notes = this.notes.filter(note => !note.isArchived);
      } else if (this.selectedStatus === 'archived') {
        this.notes = this.notes.filter(note => note.isArchived);
      } else {
        this.notes = this.originalNotes;
      }
    },
  },
};
</script>


<style scoped>

@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');

* {
  font-family: 'Roboto', sans-serif !important;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

.container-init {
  background: hsla(32, 100%, 57%, 1);
  background: linear-gradient(180deg, hsla(32, 100%, 57%, 1) 0%, hsla(15, 31%, 79%, 1) 100%);
  background: -moz-linear-gradient(180deg, hsla(32, 100%, 57%, 1) 0%, hsla(15, 31%, 79%, 1) 100%);
  background: -webkit-linear-gradient(180deg, hsla(32, 100%, 57%, 1) 0%, hsla(15, 31%, 79%, 1) 100%);
  filter: progid: DXImageTransform.Microsoft.gradient( startColorstr="#FF9A26", endColorstr="#DBC2BA", GradientType=1 );
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-content: center;
  justify-content: flex-start;
  align-items: center;
  padding: 30px 0px 30px 70px;
  column-gap: 150px;
}

.div-filter-categ {
  display: flex;
  flex-direction: column;
}

.filter-text {
  display: flex;
}

.filter-categ {
  display: flex;
}

.margin-categ {
  margin-right: 15px;
}

.cont-form-create {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: rgba(169, 177, 182, 0.975);
  padding: 20px;
  display: flex;
  flex-direction: column;
  width: 30em;
  height: 25em;
  border-radius: 10px;
  box-shadow: -3px 4px 13px 2px rgb(75, 76, 77);
}

.form-create h2  {
  font-size: 20px;
  margin: 0px;
  padding: 0px;
}

.form-create {
  display: flex;
  flex-direction: column;
  row-gap: 15px;
  padding: 20px;
  text-align: left;
}

.cont-form-edit h2 {
  font-size: 20px;
  margin: 0px;
  padding: 0px;
}

.cont-form-edit {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: rgba(169, 177, 182, 0.975);
  display: flex;
  flex-direction: column;
  width: 30em;
  height: 25em;
  border-radius: 10px;
  box-shadow: -3px 4px 13px 2px rgb(75, 76, 77);
  row-gap: 20px;
  padding: 20px;
  text-align: left;
  justify-content: center;
}

.cont-form-edit h2,
.cont-form-edit label,
.cont-form-edit input,
.cont-form-edit button,
.cont-form-edit textarea {
  margin-left: 15px;
  margin-right: 15px;
}

.container-sec {
  display: flex;
  margin: 35px 100px;
  flex-direction: row;
  align-content: center;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
}

.card-note {
  border-radius: 5px;
  border: 1px solid black;
  width: 18em;
  padding: 25px 0px;
  margin: 5px;
}

.badge-categ {
  background-color: rgb(240, 152, 80);
  border-radius: 8px;
  padding: 4px 8px;
}

.content-card-note {
  display: flex;
  flex-direction: column;
}

.buttons-card-note {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  align-content: center;
  column-gap: 20px;
}

button {
  padding: 6px 11px;
  border-radius: 5px;
  border: 1px solid #0000008c;
}

.button-new {
  display: flex;
  column-gap: 7px;
  margin-top: 12px;
  background-color: #ff8029;
  padding: 14px 18px;
}

.input-filter-categ {
  margin-right: 15px;
  border: 1px solid #0000008f;
  border-radius: 4px;
  width: 13em;
  height: 1.8em;
  padding: 1px 9px;
}

.form-filter-1 {
  display: flex;
}

#buton-filter-categ {
  display: flex;
  column-gap: 7px;
  background-color: #56c0efdb;
}

#buton-clear-categ {
  display: flex;
  column-gap: 7px;
  background-color: #ff9494;
}

select {
  border: 1px solid #0000008f;
  border-radius: 4px;
  width: 13em;
  height: 2.1em;
  padding: 1px 9px;
}

button, select, textarea, input {
  font-family: 'Roboto', sans-serif !important;
  font-size: 15px;
}

.button-edit {
  background-color: #19abed;
}

.button-save {
  display: flex;
  column-gap: 7px;
  background-color: #058ccae8;
  justify-content: center;
}

.button-cancel-2 {
  background-color: #e54b4b;
}

.button-cancel-1 {
  background-color: #ff00009c;
  margin: 0px 70px;
}

.button-delete {
  background-color: #ff00009c;
}

.button-archived {
  background-color: rgba(74, 74, 74, 0.32);
}

button:hover, select:hover {
  cursor: pointer;
  background-color: rgba(255, 255, 255, 0.25);
}

</style>
