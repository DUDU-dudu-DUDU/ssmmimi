package com.wu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wu.mapper.ProductInfoMapper;
import com.wu.pojo.ProductInfo;
import com.wu.pojo.ProductInfoExample;
import com.wu.pojo.vo.ProductVo;
import com.wu.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        // 使用PageHelpr进行分页设置
        PageHelper.startPage(pageNum, pageSize);

        // 进行pageInfo封装
        // 进行有条件的查询操作，创建ProductInfoExample对象
        ProductInfoExample example = new ProductInfoExample();
        // 设置进行降序排序  DESC降序sql
        example.setOrderByClause("p_id desc");
        // 排序完成后进行去集合
        List<ProductInfo> list = productInfoMapper.selectByExample(example);
        // 将集合封装进pageInfo
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo getById(int pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(int pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] pids) {
        return productInfoMapper.deleteBatch(pids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductVo vo) {
        return productInfoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductVo vo, int pageSize) {
        // 取出集合之前必须设置PageHelper。startpage
        PageHelper.startPage(vo.getPage(), pageSize);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);

        return new PageInfo<>(list);
    }
}
