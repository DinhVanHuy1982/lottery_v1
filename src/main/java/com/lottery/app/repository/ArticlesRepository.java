package com.lottery.app.repository;

import com.lottery.app.domain.Articles;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Articles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Long> {
    @Query(
        value = "select a.name from articles a where \n" + "case \n" + "\twhen :id is null then true \n" + "\telse a.id != :id\n" + "end",
        nativeQuery = true
    )
    List<String> getLstArticleName(@Param("id") Long id);
}
