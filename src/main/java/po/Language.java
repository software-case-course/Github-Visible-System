package po;

public enum Language{
    Javascript("Javascript"),Java("Java"),Python("Python"),CSS("CSS"),PHP("PHP"),Ruby("Ruby"),
        CPlus("C++"),C("C"),Shell("Shell"),ObjectC("Objective-C");
    String value;

    private Language(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}