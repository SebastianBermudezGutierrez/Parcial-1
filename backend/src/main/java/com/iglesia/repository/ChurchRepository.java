package com.iglesia.repository;

import com.iglesia.Church;
import java.util.List;
import java.util.Optional;

public interface ChurchRepository {
    Church save(Church church);
    Optional<Church> findById(Long id);
    List<Church> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
