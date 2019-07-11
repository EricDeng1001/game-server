package me.antinux.fakeserver.repository;

import me.antinux.fakeserver.entity.MaxWordSize;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaxWordSizeRepository extends CrudRepository<MaxWordSize, Long> {
    MaxWordSize findTopByOrderByLengthDesc();
}
