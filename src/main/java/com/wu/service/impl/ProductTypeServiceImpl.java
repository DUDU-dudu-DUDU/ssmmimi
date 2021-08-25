package com.wu.service.impl;

import com.wu.mapper.ProductTypeMapper;
import com.wu.pojo.ProductType;
import com.wu.pojo.ProductTypeExample;
import com.wu.service.ProductTypeService;
import com.wu.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    ProductTypeMapper productTypeMapper;


    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
