package jpasecurity.jpasecurity.repository;

import jpasecurity.jpasecurity.service.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUser_Id(Long id);
}
