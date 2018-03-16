package com.josh.gomez.projectmessi.generic.utils.pdf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.josh.gomez.projectmessi.R;
import com.josh.gomez.projectmessi.generic.database.mess.Mess;
import com.josh.gomez.projectmessi.generic.repository.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Joshua Gomez on 11/10/17.
 */

public class BuildPDF {

    private static Font titleFont = new Font(Font.FontFamily.HELVETICA, 13,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.HELVETICA, 10,
            Font.BOLD);
    private static Font smallColoredBold = new Font(Font.FontFamily.HELVETICA, 9,
            Font.BOLD);

    Activity activity;
    BaseColor WHITE = BaseColor.WHITE;
    BaseColor colorPrimary = BaseColor.WHITE;
    BaseColor colorLightGray = BaseColor.WHITE;
    BaseColor darkGray = BaseColor.BLACK;
    BaseColor alternativeColor;
    String title = "Mess Report";
    int rate = 0;
    List<Mess> messList;
    float[] columnWidths;
    float HEADING_PADDING = 8;
    float CELL_PADDING = 8;
    Boolean LANDSCAPE_MODE = false;
    String FOLDER_NAME = "Reports";

    public BuildPDF(Activity activity) {
        this.activity = activity;
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private static void addLine(Paragraph paragraph, String text) {
        paragraph.add(new Paragraph(text));
    }

    public BuildPDF title(String title) {
        this.title = title;
        return this;
    }

    public BuildPDF totalRate(int rate) {
        this.rate = rate;
        return this;
    }

    public BuildPDF load(List<Mess> messList) {
        this.messList = messList;
        return this;
    }

    public void Build() {
        String file = createFile();
        initColors();
        Document document;
        if (LANDSCAPE_MODE)
            document = new Document(PageSize.A4.rotate());
        else document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        try {
            addTitle(document);
            document.add(buildTable());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
        showToast("PDF saved as " + file);
        File mFile = new File(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(mFile);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    private String createFile() {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Mess mate");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        boolean success2 = true;
        if (success) {
            File folder2 = new File(Environment.getExternalStorageDirectory() + "/Mess mate/" + FOLDER_NAME);
            if (!folder2.exists()) {
                success2 = folder2.mkdir();
            }
        }
        String file;
        if (success2) {
            file = Environment.getExternalStorageDirectory().getPath() + "/Mess mate/" + FOLDER_NAME + "/" + title + ".pdf";
        } else

        {
            return null;
        }
        return file;
    }

    @SuppressWarnings("ResourceType")
    private void initColors() {
        try {
            @SuppressLint("ResourceType") String colorPrimaryString = activity.getResources().getString(R.color.colorPrimary).substring(3);
            colorPrimary = WebColors.getRGBColor(colorPrimaryString);
            smallBold.setColor(WHITE);
            smallColoredBold.setColor(colorPrimary);
            @SuppressLint("ResourceType") String colorLightGrayString = activity.getResources().getString(R.color.Offwhite).substring(3);
            @SuppressLint("ResourceType") String colorDarkGrayString = activity.getResources().getString(R.color.lightwhite).substring(3);
            colorLightGray = WebColors.getRGBColor(colorLightGrayString);
            darkGray = WebColors.getRGBColor(colorDarkGrayString);
            titleFont.setColor(colorPrimary);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addTitle(Document document) {
        Paragraph preface = new Paragraph();
        preface.setAlignment(Element.ALIGN_LEFT);
        preface.add(new Paragraph(title, titleFont));
        addLine(preface, "Total Rate : " + Constants.INDIAN_RUPEE + rate);
        addEmptyLine(preface, 2);
        try {
            document.add(preface);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void addRate(Document document) {
        Paragraph preface = new Paragraph();
        preface.setAlignment(Element.ALIGN_LEFT);
        preface.add(new Paragraph("Total Rate : " + rate, smallBold));
        addEmptyLine(preface, 2);
        try {
            document.add(preface);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private PdfPTable buildTable() {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        try {
            if (columnWidths != null)
                table.setWidths(columnWidths);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        table = setTableHeadings(table);
        PdfPCell c1;
        for (int i = 0; i < messList.size(); i++) {
            Mess mess = messList.get(i);

            if (i % 2 == 0)
                alternativeColor = WHITE;
            else alternativeColor = colorLightGray;

            c1 = new PdfPCell(new Phrase(mess.date, smallColoredBold));
            c1.setPadding(CELL_PADDING);
            c1.setBackgroundColor(alternativeColor);
            c1.setBorderColor(alternativeColor);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(mess.breakfast ? "Yes" : "-", smallColoredBold));
            c1.setPadding(CELL_PADDING);
            c1.setBackgroundColor(alternativeColor);
            c1.setBorderColor(alternativeColor);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(mess.lunch ? "Yes" : "-", smallColoredBold));
            c1.setPadding(CELL_PADDING);
            c1.setBackgroundColor(alternativeColor);
            c1.setBorderColor(alternativeColor);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(mess.dinner ? "Yes" : "-", smallColoredBold));
            c1.setPadding(CELL_PADDING);
            c1.setBackgroundColor(alternativeColor);
            c1.setBorderColor(alternativeColor);
            table.addCell(c1);


        }
        return table;
    }

    private PdfPTable setTableHeadings(PdfPTable table) {
        PdfPCell c1 = new PdfPCell(new Phrase("Date", smallBold));
        c1.setPadding(HEADING_PADDING);
        c1.setBackgroundColor(colorPrimary);
        c1.setBorderColor(colorPrimary);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Breakfast", smallBold));
        c1.setPadding(HEADING_PADDING);
        c1.setBackgroundColor(colorPrimary);
        c1.setBorderColor(colorPrimary);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Lunch", smallBold));
        c1.setPadding(HEADING_PADDING);
        c1.setBackgroundColor(colorPrimary);
        c1.setBorderColor(colorPrimary);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Dinner", smallBold));
        c1.setPadding(HEADING_PADDING);
        c1.setBackgroundColor(colorPrimary);
        c1.setBorderColor(colorPrimary);
        table.addCell(c1);

        table.setHeaderRows(1);
        return table;
    }
//    private  PdfPTable setTableBottom(PdfPTable table, JSONObject object) {
//
//        try {
//            if (alternativeColor == WHITE) alternativeColor = colorLightGray;
//            else alternativeColor = WHITE;
//            PdfPCell c1;
//            for (int j = 0; j < numCols; j++) {
//                c1 = new PdfPCell(new Phrase(object.getString(tableHeadings[j]), smallColoredBold));
//                c1.setPadding(HEADING_PADDING);
//                c1.setBackgroundColor(alternativeColor);
//                c1.setBorderColor(alternativeColor);
//                table.addCell(c1);
//            }
//            return table;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
