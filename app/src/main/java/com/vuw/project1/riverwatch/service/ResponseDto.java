package com.vuw.project1.riverwatch.service;


import com.google.gson.annotations.SerializedName;

/**
 * The response to be sent back from the server on submission of a report
 * Created by Liam De Grey(computernerddegrey@gmail.com) on 23-Sep-15.
 */
public class ResponseDto {
    @SerializedName("status")
    private final String status;

    @SerializedName("url")
    private final String url;

    @SerializedName("error_message")
    private final String error;

    public ResponseDto(final String status, final String url, final String error) {
        this.status = status;
        this.url = url;
        this.error = error;
    }

    public boolean hasSentSuccessfully() {
        return status.equals("OK");
    }
}
