package com.wottui.edw.example.dao;


import com.wottui.edw.example.dao.model.User;

/**
 * @author chendonglin
 */
public interface UserMapper {
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert
     * @param record
     * @return
     */
    int insert(User record);

    /**
     * insert selective
     * @param record
     * @return
     */
    int insertSelective(User record);

    /**
     *
     * @param id
     * @return
     */
    User selectByPrimaryKey(Long id);

    /**
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(User record);

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return user
     */
    User selectByUsername(String username);
}