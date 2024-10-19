package com.lottery.app.repository.custome.impl;

import com.lottery.app.commons.DataUtil;
import com.lottery.app.commons.ValidateUtils;
import com.lottery.app.repository.custome.ArticlesCustomeRepository;
import com.lottery.app.service.dto.ArticlesDTO;
import com.lottery.app.service.dto.SearchDTO;
import com.lottery.app.service.dto.body.search.ArticlesSearchDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticlesCustomeRepositoryImpl implements ArticlesCustomeRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public Page<ArticlesDTO> searchArticles(SearchDTO<ArticlesSearchDTO> searchDTO) {
        ArticlesSearchDTO dataSearch = searchDTO.getData();
        Pageable pageable = PageRequest.of(searchDTO.getPage() - 1, searchDTO.getPageSize());

        StringBuilder sql = new StringBuilder();
        String sqlSelect =
            "SELECT \n" +
            "    a.code, \n" +
            "    a.name, \n" +
            "    ag.name as articleGroupName, \n" +
            "    a.title, \n" +
            "    a.number_choice, \n" +
            "    a.number_of_digits, \n" +
            "    a.number_prize, \n" +
            "    a.time_start, \n" +
            "    a.time_end, \n" +
            "    a.update_name,\n" +
            "    a.content, \n" +
            "    COUNT(ld.id) AS totalLevelDeposits,\n" +
            "    COUNT(ia.id) AS totalIntroduceArticle \n";

        String sqlTable =
            "FROM \n" +
            "    articles a\n" +
            "LEFT JOIN \n" +
            "    article_group ag on a.article_group_code = ag.code \n" +
            "LEFT JOIN \n" +
            "    level_deposits ld ON ld.article_code = a.code\n" +
            "LEFT JOIN \n" +
            "    introduce_article ia ON ia.article_code = a.code\n";

        Map<String, Object> mapParametter = new HashMap<>();
        StringBuilder sqlWhere = new StringBuilder("WHERE true \n");
        if (!ValidateUtils.stringEmpty(dataSearch.getTextSearch())) {
            sqlWhere.append(
                "and (upper(a.name) like concat('%',upper(:textSearch),'%') or upper(a.title) like concat('%', upper(:textSearch),'%') )\n"
            );
            mapParametter.put("textSearch", DataUtil.validateKeySearch(dataSearch.getTextSearch()));
        }
        sqlWhere.append(" group by a.id , ag.name \n");

        sql.append(sqlSelect);
        sql.append(sqlTable);
        sql.append(sqlWhere);

        StringBuilder sqlCount = new StringBuilder(sql);
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        Query queryCount = entityManager.createNativeQuery("SELECT COUNT(1) FROM (" + sqlCount.toString() + ") a");
        DataUtil.setQueryParameters(mapParametter, query);
        DataUtil.setQueryParameters(mapParametter, queryCount);

        List<Object[]> lstObj = query.getResultList();
        List<ArticlesDTO> lstArticle = new ArrayList<>();
        for (Object obj[] : lstObj) {
            ArticlesDTO articlesDTO = new ArticlesDTO();
            articlesDTO.setCode(DataUtil.safeToString(obj[0]));
            articlesDTO.setName(DataUtil.safeToString(obj[1]));
            articlesDTO.setArticleGroupName(DataUtil.safeToString(obj[2]));
            articlesDTO.setNumberChoice(DataUtil.safeToLong(obj[3]));
            articlesDTO.setNumberOfDigits(DataUtil.safeToLong(obj[4]));
            articlesDTO.setNumberPrize(DataUtil.safeToLong(obj[5]));
            articlesDTO.setTimeStart(DataUtil.safeToString(obj[6]));
            articlesDTO.setTimeEnd(DataUtil.safeToString(obj[7]));
            articlesDTO.setUpdateName(DataUtil.safeToString(obj[8]));
            articlesDTO.setContent(DataUtil.safeToString(obj[9]));
            articlesDTO.setTotalLevelDeposits(DataUtil.safeToLong(obj[10]));
            articlesDTO.setTotalIntroduceArticle(DataUtil.safeToLong(obj[11]));
            lstArticle.add(articlesDTO);
        }

        Long countResult = 0L;
        countResult = DataUtil.safeToLong(queryCount.getSingleResult());

        return new PageImpl<>(lstArticle, pageable, countResult);
    }
}
