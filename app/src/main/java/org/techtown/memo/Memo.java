package org.techtown.memo;

public class Memo {
    int _id;
    String MEMO_SUBJECT;
    String MEMO_CONTENTS;
    int MEMO_COLOR;
    String MEMO_FAVORITE;
    String CREATE_DATE;
    String MODIFY_DATE;

    public Memo(int _id, String MEMO_SUBJECT, String MEMO_CONTENTS,int MEMO_COLOR, String MEMO_FAVORITE, String CREATE_DATE,String MODIFY_DATE) {
        this._id = _id;
        this.MEMO_SUBJECT =  MEMO_SUBJECT;
        this.MEMO_CONTENTS = MEMO_CONTENTS;
        this.MEMO_COLOR = MEMO_COLOR;
        this.MEMO_FAVORITE =  MEMO_FAVORITE;
        this.CREATE_DATE = CREATE_DATE;
        this.MODIFY_DATE = MODIFY_DATE;
    }

    public Memo(int _id, String MEMO_SUBJECT, String MEMO_CONTENTS,int MEMO_COLOR, String MEMO_FAVORITE, String CREATE_DATE) {
        this._id = _id;
        this.MEMO_SUBJECT =  MEMO_SUBJECT;
        this.MEMO_CONTENTS = MEMO_CONTENTS;
        this.MEMO_COLOR = MEMO_COLOR;
        this.MEMO_FAVORITE =  MEMO_FAVORITE;
        this.CREATE_DATE = CREATE_DATE;

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getMEMO_SUBJECT() {
        return MEMO_SUBJECT;
    }

    public void setMEMO_SUBJECT(String MEMO_SUBJECT) {
        this.MEMO_SUBJECT = MEMO_SUBJECT;
    }

    public String getMEMO_CONTENTS() {
        return MEMO_CONTENTS;
    }

    public void setMEMO_CONTENTS(String MEMO_CONTENTS) {
        this.MEMO_CONTENTS = MEMO_CONTENTS;
    }

    public int getMEMO_COLOR() {
        return MEMO_COLOR;
    }

    public void setMEMO_COLOR(int MEMO_COLOR) {
        this.MEMO_COLOR = MEMO_COLOR;
    }

    public String getMEMO_FAVORITE() {
        return MEMO_FAVORITE;
    }

    public void setMEMO_FAVORITE(String MEMO_FAVORITE) {
        this.MEMO_FAVORITE = MEMO_FAVORITE;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getMODIFY_DATE() {
        return MODIFY_DATE;
    }

    public void setMODIFY_DATE(String MODIFY_DATE) {
        this.MODIFY_DATE = MODIFY_DATE;
    }
}
