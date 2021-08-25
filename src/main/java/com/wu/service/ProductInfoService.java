package com.wu.service;

import com.github.pagehelper.PageInfo;
import com.wu.pojo.ProductInfo;
import com.wu.pojo.vo.ProductVo;

import java.util.List;

public interface ProductInfoService {
    List<ProductInfo> getAll();

    // 分页功能
    PageInfo splitPage(int pageNum, int pageSize);

    // 增加商品
    int save(ProductInfo info);

    // 按照id查询商品
    ProductInfo getById(int pid);

    // 更新商品
    int update(ProductInfo info);

    // 单个商品的删除
    int delete(int pid);

    // 批量删除商品
    int deleteBatch(String[] pids);

    // 多条件查询
    List<ProductInfo> selectCondition(ProductVo vo);

    // 多条件查询分页
    public PageInfo splitPageVo(ProductVo vo, int pageSize);
}
