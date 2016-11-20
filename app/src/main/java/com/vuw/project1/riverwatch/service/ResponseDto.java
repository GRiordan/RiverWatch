//*H****************************************************************************
// FILENAME:	ResponseDto.java
//
// DESCRIPTION:
//  Used in constructing a object to send to the website
//
//  A list of names of copyright information is provided in the README
//
//    This file is part of RiverWatch.
//
//    RiverWatch is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    RiverWatch is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with RiverWatch.  If not, see <http://www.gnu.org/licenses/>.
//
// CHANGES:
// DATE			WHO	    DETAILS
// 20/11/1995	George	Added header.
//
//*H*

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
