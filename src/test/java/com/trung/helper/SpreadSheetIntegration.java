package com.trung.helper;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class SpreadSheetIntegration {

    private static Sheets sheetsService;
    private static String SPREADSHEET_ID = "your_sheetID";

    public static String getSpreadsheetId() {
        return SPREADSHEET_ID;
    }

    private static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("src/main/resources/keys.json"))
                .createScoped(Lists.newArrayList(SheetsScopes.SPREADSHEETS));
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), new HttpCredentialsAdapter(credentials))
                .setApplicationName("Google Sheets Example")
                .build();
    }

    public static void writeData(Map<String, TestResult> testResults) throws IOException, GeneralSecurityException {
        List<List<Object>> values = new ArrayList<>();
        values.add(Arrays.asList("Testcase Name", "Result", "Duration (ms)"));
        for (Map.Entry<String, TestResult> entry : testResults.entrySet()) {
            List<Object> row = new ArrayList<>();
            row.add(entry.getKey());
            row.add(entry.getValue().getResult());
            row.add(entry.getValue().getDuration());
            values.add(row);
        }
        sheetsService = getSheetsService();
        ValueRange body = new ValueRange().setValues(values);
        UpdateValuesResponse result = sheetsService.spreadsheets().values()
                .update(SPREADSHEET_ID, "DemoReport", body)
                .setValueInputOption("RAW")
                .execute();
        System.out.println("Data written to sheet successfully.");
    }

    public static void readData() throws IOException {
        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, "DemoReport")
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            System.out.println("Data from Google Sheet:");
            for (List row : values) {
                System.out.println(row);
            }
        }
    }
}
