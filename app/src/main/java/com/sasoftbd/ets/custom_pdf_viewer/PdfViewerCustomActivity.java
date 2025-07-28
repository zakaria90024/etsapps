package com.sasoftbd.ets.custom_pdf_viewer;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;

import com.sasoftbd.ets.R;

import java.io.File;
import java.io.IOException;


import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class PdfViewerCustomActivity extends AppCompatActivity {

    private CustomPdfView pdfView;
    private TextView pageNumber;
    private int currentPage = 0;
    private int totalPages;
    private Uri pdfUri;
    private File tempPdfFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer_custom);

        pdfView = findViewById(R.id.customPdfView);
        pageNumber = findViewById(R.id.pageNumber);
        Button btnPrev = findViewById(R.id.btnPrev);
        Button btnNext = findViewById(R.id.btnNext);

        // Check Intent
        Intent intent = getIntent();
        if (intent != null && Intent.ACTION_VIEW.equals(intent.getAction())) {
            pdfUri = intent.getData();
            openPdfFromUri(pdfUri);
        } else {
            // Default (for testing) - load from assets
            pdfView.openPdf("sample.pdf");
        }

        totalPages = pdfView.getPageCount();
        updatePageNumber();

        btnPrev.setOnClickListener(v -> {
            if (currentPage > 0) {
                currentPage--;
                pdfView.showPage(currentPage);
                updatePageNumber();
            }
        });

        btnNext.setOnClickListener(v -> {
            if (currentPage < totalPages - 1) {
                currentPage++;
                pdfView.showPage(currentPage);
                updatePageNumber();
            }
        });
    }

    private void openPdfFromUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            tempPdfFile = new File(getCacheDir(), "opened_file.pdf");
            FileOutputStream outputStream = new FileOutputStream(tempPdfFile);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }

            outputStream.close();
            inputStream.close();

            // Load file into PdfView
            pdfView.openPdf(tempPdfFile.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePageNumber() {
        pageNumber.setText((currentPage + 1) + "/" + totalPages);
    }
}


