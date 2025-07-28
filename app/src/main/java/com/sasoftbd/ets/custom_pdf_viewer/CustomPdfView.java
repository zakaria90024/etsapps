package com.sasoftbd.ets.custom_pdf_viewer;


import android.content.Context;
import android.graphics.*;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.util.AttributeSet;
import android.view.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CustomPdfView extends View {

    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;
    private ParcelFileDescriptor fileDescriptor;
    private Bitmap pageBitmap;

    private float scaleFactor = 1f;
    private float offsetX = 0f, offsetY = 0f;

    private ScaleGestureDetector scaleDetector;
    private GestureDetector gestureDetector;

    private RectF highlightRect = null;

    public CustomPdfView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGestures();
    }

    private void initGestures() {
        scaleDetector = new ScaleGestureDetector(getContext(),
                new ScaleGestureDetector.SimpleOnScaleGestureListener() {
                    @Override
                    public boolean onScale(ScaleGestureDetector detector) {
                        scaleFactor *= detector.getScaleFactor();
                        scaleFactor = Math.max(1f, Math.min(scaleFactor, 5f));
                        invalidate();
                        return true;
                    }
                });

        gestureDetector = new GestureDetector(getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
                        offsetX -= dx;
                        offsetY -= dy;
                        invalidate();
                        return true;
                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        highlightRect = new RectF(e.getX()-20, e.getY()-20,
                                e.getX()+20, e.getY()+20);
                        invalidate();
                        return true;
                    }
                });
    }

    public void openPdf(String assetFileName) {
        try {
            File file = new File(getContext().getCacheDir(), assetFileName);
            if (!file.exists()) {
                InputStream asset = getContext().getAssets().open(assetFileName);
                FileOutputStream output = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int size;
                while ((size = asset.read(buffer)) != -1) {
                    output.write(buffer, 0, size);
                }
                output.close();
                asset.close();
            }
            fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            pdfRenderer = new PdfRenderer(fileDescriptor);
            showPage(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPageCount() {
        return pdfRenderer != null ? pdfRenderer.getPageCount() : 0;
    }

    public void showPage(int index) {
//        if (pdfRenderer == null || index < 0 || index >= pdfRenderer.getPageCount()) return;
//        if (currentPage != null) currentPage.close();
//
//        currentPage = pdfRenderer.openPage(index);
//        pageBitmap = Bitmap.createBitmap(currentPage.getWidth(),
//                currentPage.getHeight(), Bitmap.Config.ARGB_8888);
//        currentPage.render(pageBitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
//        invalidate();


        if (pdfRenderer == null || index < 0 || index >= pdfRenderer.getPageCount()) return;
        if (currentPage != null) currentPage.close();

        currentPage = pdfRenderer.openPage(index);

        int renderWidth = currentPage.getWidth() * 2;   // 3x HD
        int renderHeight = currentPage.getHeight() * 2;

        pageBitmap = Bitmap.createBitmap(renderWidth, renderHeight, Bitmap.Config.ARGB_8888);

        Matrix matrix = new Matrix();
        float scale = 2.0f;  // High DPI scale factor
        matrix.postScale(scale, scale);

        currentPage.render(pageBitmap, null, matrix, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        invalidate();
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (pageBitmap != null) {
//            canvas.save();
//            canvas.translate(offsetX, offsetY);
//            canvas.scale(scaleFactor, scaleFactor);
//            canvas.drawBitmap(pageBitmap, 0, 0, null);
//
//            if (highlightRect != null) {
//                Paint paint = new Paint();
//                paint.setColor(Color.YELLOW);
//                paint.setAlpha(120);
//                canvas.drawRect(highlightRect, paint);
//            }
//
//            canvas.restore();
//        }
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return true;
    }


//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (pageBitmap != null) {
//            canvas.save();
//            canvas.translate(offsetX, offsetY);
//            canvas.scale(scaleFactor, scaleFactor);
//
//            // Draw PDF Page
//            canvas.drawBitmap(pageBitmap, 0, 0, null);
//
//            // Draw 1dp border
//            Paint borderPaint = new Paint();
//            borderPaint.setColor(Color.GRAY); // Border color
//            borderPaint.setStyle(Paint.Style.STROKE);
//            borderPaint.setStrokeWidth(getResources().getDisplayMetrics().density); // 1dp
//
//            canvas.drawRect(
//                    0,
//                    0,
//                    pageBitmap.getWidth(),
//                    pageBitmap.getHeight(),
//                    borderPaint
//            );
//
//            // Highlight area (if any)
//            if (highlightRect != null) {
//                Paint paint = new Paint();
//                paint.setColor(Color.YELLOW);
//                paint.setAlpha(120);
//                canvas.drawRect(highlightRect, paint);
//            }
//
//            canvas.restore();
//        }
//    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (pageBitmap != null) {
            canvas.save();
            canvas.translate(offsetX, offsetY);
            canvas.scale(scaleFactor, scaleFactor);

            // Define reduced width and height
            float marginDp = 25; // reduce width by 16dp total (8dp left + 8dp right)
            float marginPx = marginDp * getResources().getDisplayMetrics().density;

            float pageLeft = marginPx;
            float pageTop = marginPx;
            float pageRight = pageBitmap.getWidth() - marginPx;
            float pageBottom = pageBitmap.getHeight() - marginPx;

            // Draw reduced page in the rectangle
            Rect srcRect = new Rect(0, 0, pageBitmap.getWidth(), pageBitmap.getHeight());
            RectF dstRect = new RectF(pageLeft, pageTop, pageRight, pageBottom);

            canvas.drawBitmap(pageBitmap, srcRect, dstRect, null);

            // Draw border around reduced page
            Paint borderPaint = new Paint();
            borderPaint.setColor(Color.GRAY);
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(getResources().getDisplayMetrics().density);

            canvas.drawRect(dstRect, borderPaint);

            canvas.restore();
        }
    }


}
