package com.integration.db;

import com.domain.NoteRepository;
import com.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaNoteRepository extends JpaRepository<Note, Long>, NoteRepository { }
