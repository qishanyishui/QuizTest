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
    private static Font headfont ;// ���������С
    private static Font keyfont;// ���������С
    private static Font textfont;// ���������С

    static{
        BaseFont bfChinese;
        try {
            //bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            headfont = new Font(bfChinese, 10, Font.BOLD);// ���������С
            keyfont = new Font(bfChinese, 8, Font.BOLD);// ���������С
            textfont = new Font(bfChinese, 8, Font.NORMAL);// ���������С
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeSimplePdf(String data) throws Exception{
        //1.�½�document����
        //��һ��������ҳ���С���������Ĳ����ֱ������ҡ��Ϻ���ҳ�߾ࡣ
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        //2.����һ����д��(Writer)��document���������ͨ����д��(Writer)���Խ��ĵ�д�뵽�����С�
        //���� PdfWriter ���� ��һ�������Ƕ��ĵ���������ã��ڶ����������ļ���ʵ�����ƣ��ڸ������л�����������·����
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\dell\\Desktop\\QuizTest\\QuizTest\\data/exam.pdf"));
        //3.���ĵ�
        document.open();
        //4.���ĵ����������
        //ͨ�� com.lowagie.text.Paragraph ������ı����������ı�����Ĭ�ϵ����塢��ɫ����С�ȵ�����������һ��Ĭ�϶���
        document.add(new Paragraph(data));
        //document.add(new Paragraph("Some more text on the first page with different color and font type.",
               // FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new Color(255, 150, 200))));
        //5.�ر��ĵ�
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