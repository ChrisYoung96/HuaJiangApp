package com.chrisyoung.huajiangapp.view.vinterface;

import com.chrisyoung.huajiangapp.domain.CRecord;

public interface IAddOrModifyRecordView extends BaseView {


    void cleareText();

    void showRecord(CRecord cRecord);

    void jump2MainActivity();

}
