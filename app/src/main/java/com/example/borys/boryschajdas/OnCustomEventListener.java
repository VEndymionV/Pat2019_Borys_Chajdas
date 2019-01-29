package com.example.borys.boryschajdas;

import java.util.List;

public interface OnCustomEventListener {
    void onEvent(List<Photo> photos);
    void onFailure();
}
