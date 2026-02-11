package com.michael.test.repository;

import com.michael.test.domains.UserFavorite;
import java.util.List;
import org.springframework.data.repository.Repository;

/**
 * @author michaelwang on 2021-03-14
 */
public interface UserFavoriteRepository extends Repository<UserFavorite, Integer> {
  public List<UserFavorite> findUserFavoriteByUserId(Integer userId);
}
