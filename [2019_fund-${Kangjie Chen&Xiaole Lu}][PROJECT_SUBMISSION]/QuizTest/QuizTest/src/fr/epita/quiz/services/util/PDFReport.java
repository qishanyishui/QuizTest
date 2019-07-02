package fr.epita.quiz.services.util;
import java.awt.Color;
import java.io.FileOutputStream;

//import org.apache.tools.ant.Main;

import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Section;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class PDFReport{
    private static Font headfont ;// 设置字体大小
    private static Font keyfont;// 设置字体大小
    private static Font textfont;// 设置字体大小

    static{
        BaseFont bfChinese;
        try {
            //bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            headfont = new Font(bfChinese, 10, Font.BOLD);// 设置字体大小
            keyfont = new Font(bfChinese, 8, Font.BOLD);// 设置字体大小
            textfont = new Font(bfChinese, 8, Font.NORMAL);// 设置字体大小
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeSimplePdf(String data) throws Exception{
        //1.新建document对象
        //第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        //2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        //创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\dell\\Desktop\\QuizTest\\QuizTest\\data/exam.pdf"));
        //3.打开文档
        document.open();
        //4.向文档中添加内容
        //通过 com.lowagie.text.Paragraph 来添加文本。可以用文本及其默认的字体、颜色、大小等等设置来创建一个默认段落
        document.add(new Paragraph(data));
        //document.add(new Paragraph("Some more text on the first page with different color and font type.",
               // FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new Color(255, 150, 200))));
        //5.关闭文档
        document.close();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("begin");
        PDFReport ppt=new PDFReport();
        String data = "First page ofthe document.\nSome more text \non the first page with ";
        ppt.writeSimplePdf(data);
        System.out.println("end");
    }

}