package cn.ruihusoft.mybatis.core;

import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

public interface BaseService<T> {

    BaseMapper getBaseMapper();

    T selectByPrimaryKey(Object key);

    List<T> select(T record);

    List<T> selectByRowBounds(T record, RowBounds rowBounds);

    int selectCount(T record);

    int selectCountByExample(Object example);

    List<T> selectByExample(Object example);

    List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds);

    T selectOneByExample(Object example);

    T selectOne(T record);

    boolean insert(T obj);

    boolean insertSelective(T obj);

    boolean updateByPrimaryKey(T obj);

    boolean updateByPrimaryKeySelective(T record);

    int updateByExample(T record, Object example);

    int updateByExampleSelective(T record, Object example);

    int deleteByPrimaryKey(Object key);

    int delete(T record);

    int deleteByExample(Object example);

}
