package me.antinux.fakeserver.repository;

import me.antinux.fakeserver.entity.UserSolvedWord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSolvedWordRepository extends CrudRepository<UserSolvedWord, Long> {

    List<UserSolvedWord> findAllByUserId(Long userId);

    List<UserSolvedWord> findAllByWordId(Long wordId);

}
