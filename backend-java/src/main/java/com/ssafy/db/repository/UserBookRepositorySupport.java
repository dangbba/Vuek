package com.ssafy.db.repository;

import com.ssafy.db.entity.UserBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserBookRepositorySupport {
    private final EntityManager em;

    public List<UserBook> getUserBooksByUserId(String userId){
        Query query = em.createNativeQuery(
                "select * from UserBook");
        List<UserBook> list = query.getResultList();
        em.close();
        return list;
    }
}
