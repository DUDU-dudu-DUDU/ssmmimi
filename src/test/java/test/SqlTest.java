package test;

import com.wu.mapper.ProductInfoMapper;
import com.wu.pojo.ProductInfo;
import com.wu.pojo.vo.ProductVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml", "classpath:applicationContext_service.xml"})
public class SqlTest {
    @Autowired
    ProductInfoMapper mapper;

    @Test
    public void testSelectCondition(){
        ProductVo vo = new ProductVo();
        vo.setPname("米");
        vo.setTypeid(1);
        vo.setHprice(5000);
        vo.setLprice(4000);
        List<ProductInfo> list = mapper.selectCondition(vo);
        list.forEach(ProductInfo -> System.out.println(ProductInfo));
    }


}
