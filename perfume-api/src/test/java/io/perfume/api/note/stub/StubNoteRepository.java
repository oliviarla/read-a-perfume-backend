package io.perfume.api.note.stub;

import io.perfume.api.note.application.port.out.NoteRepository;
import io.perfume.api.note.domain.Note;
import io.perfume.api.note.domain.CategoryUser;

public class StubNoteRepository implements NoteRepository {

  @Override
  public Note save(Note note) {
    return note;
  }
}