package com.hhkj.dyedu.utils.dimens;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zangyi_shuai_ge on 2018/9/28
 *
 * @author zangyi 767450430
 * <p>
 * 绿豆芽项目尺寸
 * 设计稿尺寸 1920 1200
 * 需要适配机型 1920*1200 2560*1600
 * 屏幕比例 1.6
 */
public class GenerateValueFiles {
    private static List<ScreenSize> screenSizes;
    private static String dirStr = "./res";
    
    public static void main(String[] args) {
        screenSizes = new ArrayList<>();
        screenSizes.add(new ScreenSize(1200, 1920));
        screenSizes.add(new ScreenSize(1600, 2560));
        //创建 res文件夹
        File dir = new File(dirStr);
        if (!dir.exists()) {
            dir.mkdir();
        }
        //在res 文件夹下面生成各自的资源文件
        for (int i = 0; i < screenSizes.size(); i++) {
            ScreenSize screenSize = screenSizes.get(i);
            //计算比例
            double proportion = screenSize.getHeight() / 1920.00;
            StringBuilder sbForWidth = new StringBuilder();
            sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
            sbForWidth.append("<resources>");
            //中间的值
            for (int j = 0; j < 1920 + 1; j++) {
                double d = j * proportion;
                d = (double) Math.round(d * 10) / 10;
                String one = "<dimen name=\"px_" + String.valueOf(j) + "\">" + String.valueOf(d) + "px</dimen>\n";
                sbForWidth.append(one);
                if (j == 125) {
                    String t = "<dimen name=\"start_class_top_bar_height\">" + String.valueOf(d) + "px</dimen>\n";
                    sbForWidth.append(t);
                }
                if (j == 120) {
                    String t = "<dimen name=\"start_class_bottom_bar_height\">" + String.valueOf(d) + "px</dimen>\n";
                    sbForWidth.append(t);
                }
                if (j == 300) {
                    String t = "<dimen name=\"start_class_left_ppt_width\">" + String.valueOf(d) + "px</dimen>\n";
                    sbForWidth.append(t);
                }
                if (j == 185) {
                    String t = "<dimen name=\"start_class_right_menu_small_width\">" + String.valueOf(d) + "px</dimen>\n";
                    sbForWidth.append(t);
                }
                if (j == 86) {
                    String t = "<dimen name=\"corner_86_px\">" + String.valueOf(d) + "px</dimen>\n";
                    sbForWidth.append(t);
                }
            }
            sbForWidth.append("</resources>");
            File fileDir = new File(dirStr + "/values-" + screenSize.getHeight() + "x" + screenSize.getWidth());
            fileDir.mkdir();
            File layxFile = new File(fileDir.getAbsolutePath(), "dimens.xml");
            try {
                PrintWriter pw = new PrintWriter(new FileOutputStream(layxFile));
                pw.print(sbForWidth.toString());
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            
        }
    }
    
}
