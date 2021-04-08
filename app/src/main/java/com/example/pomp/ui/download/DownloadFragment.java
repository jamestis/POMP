package com.example.pomp.ui.download;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.DownloadManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pomp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadFragment extends Fragment implements View.OnClickListener {

    Button b;
    EditText URLView;
    EditText FileLocationView;
    ProgressBar p;
    TextView t;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_download, container, false);
        b = (Button) myview.findViewById(R.id.download_button);
        URLView = (EditText) myview.findViewById(R.id.editTextURL);
        FileLocationView = (EditText) myview.findViewById(R.id.editTextURLOutputFile);
        b.setOnClickListener(this);
        return myview;
    }


    //add error handling: check if url is correct, update progress bar?
    @Override
    public void onClick(View v) {
        String URL = URLView.getText().toString().trim();
        String filePath = FileLocationView.getText().toString().trim();
        if(!filePath.endsWith(".fasta")){
            Toast t = Toast.makeText(getContext(),"File destination must be a fasta file.",Toast.LENGTH_LONG);
            t.show();
        }
        else {
            DownloadFile(URL, filePath);
        }
    }

    private void DownloadFile(String URL, String filePath) {
        try {
            System.out.println("Starting download...");
            System.out.println("URL LINK : " + URL);
            DownloadManager.Request req = new DownloadManager.Request(Uri.parse(URL));
            req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            req.setTitle("Download");
            req.setDescription("Downloading file....");
            req.allowScanningByMediaScanner();
            req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filePath);
            System.out.println(Environment.DIRECTORY_DOWNLOADS);
            DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
            System.out.println("Finished queing download");
            manager.enqueue(req);
            Toast toast = Toast.makeText(getContext(), "Check your download manager for file infromation", Toast.LENGTH_LONG);
            toast.show();
        }
        catch(IllegalArgumentException arg){
            Toast toast = Toast.makeText(getContext(), "Invalid URL!", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}

