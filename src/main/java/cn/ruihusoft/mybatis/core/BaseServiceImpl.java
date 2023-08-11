package cn.ruihusoft.mybatis.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Slf4j
public class BaseServiceImpl<M extends Mapper<T>, T> implements BaseService<T> {

    @Autowired
    private M baseMapper;

    @Override
    public M getBaseMapper() {
        return baseMapper;
    }

    @Override
    public T selectByPrimaryKey(Object key) {
        return baseMapper.selectByPrimaryKey(key);
    }

    @Override
    public List<T> select(T record) {
        return baseMapper.select(record);
    }

    @Override
    public List<T> selectByRowBounds(T record, RowBounds rowBounds) {
        return baseMapper.selectByRowBounds(record, rowBounds);
    }

    @Override
    public int selectCount(T record) {
        return baseMapper.selectCount(record);
    }

    @Override
    public int selectCountByExample(Object example) {
        return baseMapper.selectCountByExample(example);
    }

    @Override
    public List<T> selectByExample(Object example) {
        return baseMapper.selectByExample(example);
    }

    @Override
    public List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
        return baseMapper.selectByExampleAndRowBounds(example, rowBounds);
    }

    @Override
    public T selectOneByExample(Object example) {
        return baseMapper.selectOneByExample(example);
    }

    @Override
    public T selectOne(T record) {
        return baseMapper.selectOne(record);
    }

    @Override
    public boolean insert(T obj) {
        return baseMapper.insert(obj) == 1;
    }

    @Override
    public boolean insertSelective(T obj) {
        return baseMapper.insertSelective(obj) == 1;
    }

    @Override
    public boolean updateByPrimaryKey(T obj) {
        return baseMapper.updateByPrimaryKey(obj) > 0;
    }

    @Override
    public boolean updateByPrimaryKeySelective(T record) {
        return baseMapper.updateByPrimaryKeySelective(record) > 0;
    }

    @Override
    public int updateByExample(T record, Object example) {
        return baseMapper.updateByExample(record, example);
    }

    @Override
    public int updateByExampleSelective(T record, Object example) {
        return baseMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int deleteByPrimaryKey(Object key) {
        return baseMapper.deleteByPrimaryKey(key);
    }

    @Override
    public int delete(T record) {
        return baseMapper.delete(record);
    }

    @Override
    public int deleteByExample(Object example) {
        return baseMapper.deleteByExample(example);
    }
}
