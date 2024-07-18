package com.example.db;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SampleFragment extends Fragment {

    private EditText editTextInput;
    private EditText editNumberInput;
    private EditText editUsernameInput;
    private Button buttonSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample, container, false);

        editTextInput = view.findViewById(R.id.editTextInput);
        editNumberInput = view.findViewById(R.id.editNumberInput);
        editUsernameInput = view.findViewById(R.id.editUsernameInput);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });

        return view;
    }

    public void submitData() {
        String data = editTextInput.getText().toString().trim();
        String data1 = editNumberInput.getText().toString().trim();
        String data2 = editUsernameInput.getText().toString().trim();
        if (!data.isEmpty() && !data1.isEmpty() && !data2.isEmpty()) {
            sendDataToServer(data, data1, data2);
        } else {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendDataToServer(String data, String data1, String data2) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Replace with your server URL
                    URL url = new URL("http://lesterintheclouds.com/crud-android/create.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);

                    // Create the data to send
                    String postData = "data=" + data + "&number=" + data1 + "&username=" + data2;

                    // Write data to the connection
                    OutputStream os = conn.getOutputStream();
                    os.write(postData.getBytes());
                    os.flush();
                    os.close();

                    // Check the response code
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Request successful
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(requireContext(), "Data submitted successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // Request failed
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(requireContext(), "Failed to submit data", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    conn.disconnect();

                } catch (IOException e) {
                    e.printStackTrace();
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(requireContext(), "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}
