package com.wu.controller;

import com.github.pagehelper.PageInfo;
import com.wu.pojo.ProductInfo;
import com.wu.pojo.vo.ProductVo;
import com.wu.service.ProductInfoService;
import com.wu.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProducInfoAction {

    // 每页记录数
    public static final int PAGE_SIZE = 5;
    // 异步上传图片的名称
    String saveFileName = "";

    @Autowired
    ProductInfoService productInfoService;

    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list = productInfoService.getAll();
        System.out.println(list);
        request.setAttribute("list", list);
        return "product";
    }

    // 显示第一页五条记录
    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("prodVo");
        if (vo != null){
            info = productInfoService.splitPageVo((ProductVo) vo, 5);
            // 清楚session防止影响
            request.getSession().removeAttribute("prodVo");
        }else {
            // 得到第一页数据
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }
        request.setAttribute("info", info);
        return "product";
    }

    // ajax分页翻页处理
    @ResponseBody
    @RequestMapping("/ajaxsplit")
    public void ajaxSplit(ProductVo vo, HttpSession session){
        // 去的当前页数据
        PageInfo info = productInfoService.splitPageVo(vo, PAGE_SIZE);
        session.setAttribute("info", info);
    }

    // ajax进行文件上传
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage, HttpServletRequest request){
        // 提取文件后缀名
        saveFileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());
        // 得到项目存储路径
        String path = request.getSession().getServletContext().getRealPath("/image_big");
        System.out.println(path+ "/" +saveFileName);
        // 转存  File.separator=/
        try {
            pimage.transferTo(new File(path+File.separator+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 返回客户端json对象   封装图片路径  进行图片回写
        JSONObject object = new JSONObject();
        object.put("imgurl",saveFileName);
        return object.toString();
    }

    @RequestMapping("/save")
    public String save(ProductInfo info, HttpServletRequest request){
        info.setpImage(saveFileName);
        info.setpDate(new Date());
        System.out.println(info);
        //
        int num = -1;
        try {
            num = productInfoService.save(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0){
            request.setAttribute("msg", "添加成功了亲~~");
        }else {
            request.setAttribute("msg", "添加失败了亲~~");
        }
        // 清空saveFileName变量中的内容  为了下次增加修改异步ajax
        saveFileName = "";
        return "forward:/prod/split.action";
    }


    @RequestMapping("/one")
    public String one(int pid, ProductVo vo, Model model,HttpSession session){
        ProductInfo info = productInfoService.getById(pid);
        model.addAttribute("prod", info);
        // 将多条件以及页码放入session中 更新处理结束后分页读取
        session.setAttribute("prodVo",vo);
        return "update";
    }

    @RequestMapping("/update")
    public String update(ProductInfo info, HttpServletRequest request){
        // 因为ajax的异步图片上传 有上传则用saveFileName
        // 如果没有上传  则为“ ”  就用隐藏域上传
        if (!saveFileName.equals("")){
            info.setpImage(saveFileName);
        }
        int num = -1;
        try {
            num = productInfoService.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (num > 0){
            request.setAttribute("msg", "更新成功了哦~");
        }else {
            request.setAttribute("msg", "更新失败");
        }

        // 再次清空saveFileNmae
        saveFileName = "";
        return "forward:/prod/split.action";
    }


    @RequestMapping("/delete")
    public String delete(int pid,ProductVo vo, HttpServletRequest request){
        int num = -1;
        try {
            num = productInfoService.delete(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0){
            request.setAttribute("msg", "这条记录没了..");
            request.getSession().setAttribute("deleteProdVo", vo);
        }else {
            request.setAttribute("msg", "删除失败");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit", produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request){
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("deleteProdVo");
        if (vo != null){
            info = productInfoService.splitPageVo((ProductVo) vo, PAGE_SIZE);
        }else {
            // 取得第一页数据
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }
        request.getSession().setAttribute("info", info);
        return request.getAttribute("msg");
    }

    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids, ProductVo vo, HttpServletRequest request){
        // 将字符串装换成数组
        String[] ps = pids.split(",");
        try {
            int num = productInfoService.deleteBatch(ps);
            if (num > 0 ){
                request.setAttribute("msg", "批量删除完了~");
                request.getSession().setAttribute("deleteProdVo", vo);
            }else {
                request.setAttribute("msg", "批量删除失败？");
            }
        } catch (Exception e) {
            request.setAttribute("msg", "商品不可删除！");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }

    // 多条件查询功能
    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductVo vo, HttpSession session){
        List<ProductInfo> list = productInfoService.selectCondition(vo);
        list.forEach(System.out::println);
        session.setAttribute("list", list);
    }
}
