package po;

public enum Language{
    Javascript("Javascript"),Java("Java"),Python("Python"),CSS("CSS"),PHP("PHP"),Ruby("Ruby"),
        CPlus("C++"),C("C"),Shell("Shell"),ObjectC("Objective-C");
        // ,R("R"),VimL("VimL"),Go("Go"),
        // Perl("Perl"),CoffeeScript("CoffeeScript"),TeX("TeX"),Swift("Swift"),Scala("Scala");
    String value;

    private Language(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}