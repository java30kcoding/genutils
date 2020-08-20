package cn.itlou.genutils.tabledoc;

import lombok.Data;

import java.util.List;

@Data
public class OKRData {

    private List<OKRItem> objectives;
    private List<OKRItem> manageObjectives;
    private String date;
}
@Data
class OKRItem {
    int index;
    Objective object;
    KeyResult kr1;
    KeyResult kr2;
    KeyResult kr3;
}
@Data
class Objective {
    private String desc;
    private String progress;

    public Objective(String desc, String progress) {
        this.desc = desc;
        this.progress = progress;
    }

}
@Data
class KeyResult {
    private String desc;
    private String progress;

    public KeyResult(String desc, String progress) {
        this.desc = desc;
        this.progress = progress;
    }

}