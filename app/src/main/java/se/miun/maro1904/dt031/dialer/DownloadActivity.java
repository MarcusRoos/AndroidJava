package se.miun.maro1904.dt031.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@SuppressWarnings("deprecation")
public class DownloadActivity extends AppCompatActivity {
    private static final String TAG = "DownloadActivity";
    private String webPage = "";
    private String destination = "";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_activity);
        try {
            webPage = getIntent().getExtras().getString("url");
            destination = getIntent().getExtras().getString("destination");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            webPage = "www.google.com";
        }
        WebView webview = (WebView) findViewById(R.id.web_view);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains(webPage.substring(0, webPage.lastIndexOf("/")))) {
                    view.loadUrl(url);
                } else {
                    Toast.makeText(DownloadActivity.this,
                            getString(R.string.downloadingProgress),
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
        webview.setDownloadListener((downloadUrl, userAgent, contentDisposition, mimetype, contentLength) -> new DownloadSoundTask().execute(downloadUrl));
        webview.loadUrl(webPage);
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadSoundTask extends AsyncTask<String, Integer, Integer> {
        private final ProgressDialog progressDialog = new ProgressDialog(DownloadActivity.this);
        private String filePath = "";

        // Inspiration taken from https://abhiandroid.com/programming/asynctask
        @Override
        protected void onPreExecute() {
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage(getString(R.string.downloadingProgress));
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... urls) {
            int percentage;
            int length = 0;
            String downloader = urls[0];

            String fileName = downloader.substring(downloader
                    .lastIndexOf("/") + 1);
            filePath = destination + fileName;
            URL url = null;
            URLConnection urlConnection = null;
            InputStream inStream = null;
            FileOutputStream outStream = null;
            try {
                url = new URL(downloader);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                urlConnection = url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inStream = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int content = urlConnection.getContentLength();
                byte[] byteReader = new byte[content];
                length = 0;
                int bytesRead = 0;
                while (length < content) {
                    try {
                        bytesRead = inStream.read(byteReader, length, byteReader.length - length);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    length += bytesRead;
                    percentage = length * 100 / content;
                    publishProgress(percentage);
                }
            try {
                outStream = new FileOutputStream(filePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                outStream.write(byteReader);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return length;
                }


        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            progressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (this.progressDialog != null && this.progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (!filePath.equals("") && !destination.equals("")) {
                new unZipping().execute(filePath, destination);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class unZipping extends AsyncTask<String, Void, Void> {
        private ProgressDialog progressDialog = new ProgressDialog(DownloadActivity.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage(getString(R.string.decompressing));
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            String filePath = params[0];
            String directoryPathToUnzip = params[1];
            unZip(filePath, directoryPathToUnzip);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            if (this.progressDialog != null && this.progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            new CleanTask().execute(destination);
        }
    }

    private static class CleanTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String directoryToClean = params[0];
            cleanDirectory(directoryToClean, "dps");
            return null;
        }
    }

    //unZip file from Util.java
    public static boolean unZip(String sourceFile, String destinationDir) {
        try {

            ZipFile zipFile = new ZipFile(sourceFile);
            Enumeration<? extends ZipEntry> e = zipFile.entries();

            while (e.hasMoreElements()) {
                ZipEntry entry = e.nextElement();

                File destinationFile  = new File(destinationDir, entry.getName());

                File destinationParent = destinationFile.getParentFile();

                if (destinationParent != null) {
                    destinationParent.mkdirs();
                }

                if (!entry.isDirectory()) {
                    BufferedInputStream in = new BufferedInputStream(zipFile
                            .getInputStream(entry));

                    BufferedOutputStream out = new BufferedOutputStream(
                            new FileOutputStream(destinationFile ));

                    byte[] buffer = new byte[4096];
                    int bytesWrite;

                    while ((bytesWrite = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesWrite);
                    }

                    out.flush();
                    out.close();
                    in.close();
                }
            }

            zipFile.close();
        } catch (IOException e) {
            Log.e(TAG, "Error unzipping " + sourceFile + ": " + e.toString());
            return false;
        }
        return true;
    }

    public static void cleanDirectory(String cleanUp, String extension1) {
        File dir = new File(cleanUp);
        String[] files = dir.list();
            for (String file : files) {
                String extension2 = file.substring(
                        file.lastIndexOf(".") + 1);
                if (extension2.equals(extension1)) {
                    File delFile = new File(cleanUp + file);
                    if (delFile.isFile() && delFile.canWrite()) {
                        delFile.delete();
                    }
                }
            }
    }

}
