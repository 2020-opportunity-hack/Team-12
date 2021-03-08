package com.learnandearn.sundayfriends.network.model;

public class OnBoardDto {
    private OnBoard onBoard;
    private ResponseCode responseCode;

    public OnBoardDto(OnBoard onBoard, ResponseCode responseCode) {
        this.onBoard = onBoard;
        this.responseCode = responseCode;
    }

    public OnBoard getOnBoard() {
        return onBoard;
    }
    public ResponseCode getResponseCode() {
        return responseCode;
    }


}
