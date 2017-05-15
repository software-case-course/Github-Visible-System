package po;

public enum ChineseArea implements Area{
    Beijing("Beijing"),Tianjin("Tianjin"),Shanghai("Shanghai"),Chongqing("Chongqing"),Hebei("Hebei"),Henan("Henan"),
        Yunnan("Yunnan"),Liaoling("Liaoling"),Heilongjiang("Heilongjiang"),Hunan("Hunan"),Anhui("Anhui"),Shandong("Shandong"),
        Xinjiang("Xinjiang"),Jiangsu("Jiangsu"),Zhejiang("Zhejiang"),Jiangxi("Jiangxi"),Hubei("Hubei"),Guangxi("Guangxi"),
        Gansu("Gansu"),Shanxi("Shanxi"),Neimenggu("Neimenggu"),Jilin("Jilin"),Fujian("Fujian"),Guizhou("Guizhou"),
        Guangdong("Guangdong"),Guangzhou("Guangzhou"),Qinghai("Qinghai"),Tibet("Tibet"),Sichuan("Sichuan"),Ningxia("Ningxia"),
        Hainan("Hainan"),Taiwan("Taiwan"),HongKong("Hong+Kong"),Macau("Macau");
    String value;

    private ChineseArea(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public String getName(){
        return value.replace('+', ' ');
    }
}