package com.biyesheji.modules.project.dao;

import com.biyesheji.modules.project.entity.CategoryEntity;
import com.biyesheji.modules.sys.dao.BaseDao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 分类
 * 
 * *
 * *
 *
 */
@Mapper
public interface CategoryDao extends BaseDao<CategoryEntity> {

	List<CategoryEntity> queryAll();
	
}
