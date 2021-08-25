package test;

import com.wu.utils.MD5Util;
import org.junit.Test;

public class test {
    @Test
    public void password(){

        System.out.println(MD5Util.getMD5("000000"));
    }
}
