package com.lottery.app.repository.custome;

import com.lottery.app.service.dto.ArticlesDTO;
import com.lottery.app.service.dto.SearchDTO;
import com.lottery.app.service.dto.body.search.ArticlesSearchDTO;
import org.springframework.data.domain.Page;

public interface ArticlesCustomeRepository {
    Page<ArticlesDTO> searchArticles(SearchDTO<ArticlesSearchDTO> searchDTO);
}
