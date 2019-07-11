package me.antinux.fakeserver.repository;

import me.antinux.fakeserver.entity.Word;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends CrudRepository<Word, Long> {
    List<Word> findByOwnerId(Long ownerId);
    List<Word> findAllByLength(int length);
    boolean existsByContent(String content);
}
